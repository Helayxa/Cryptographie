package com.crypto.algo;

public class Vigenere {
	
	public Vigenere() {

	}
	
	public int findIndexByLetter(char[] alphabet, char letter) {
		int indexToStart = -1;
		/* Recherche de la première lettre de notre alphabet pour créer le nouvel alphabet avec décalage */
		for(int i = 0; i < alphabet.length; i++) {
			if(alphabet[i] == letter) {
				indexToStart = i;
				break;
			}
		}
		return indexToStart;
	}
	
	public String encode(char[] alphabet, String sentence, String key) {
		char[] keyAsArray = key.replace(" ", "").toUpperCase().toCharArray();
		char[] sentenceAsArray = sentence.replace(" ", "").toUpperCase().toCharArray();
		char[] result = new char[sentenceAsArray.length];
		for(int i = 0; i < sentenceAsArray.length; i++) {
			int indexToShift = findIndexByLetter(alphabet, keyAsArray[i % keyAsArray.length]);
			int currentIndex = findIndexByLetter(alphabet, sentenceAsArray[i]);
			result[i] = alphabet[(currentIndex + indexToShift) % alphabet.length];
		}
		return String.valueOf(result);
	}
	
	public String decode(char[] alphabet, String sentence, String key) {
		char[] keyAsArray = key.replace(" ", "").toUpperCase().toCharArray();
		char[] sentenceAsArray = sentence.replace(" ", "").toUpperCase().toCharArray();
		char[] result = new char[sentenceAsArray.length];
		for(int i = 0; i < sentenceAsArray.length; i++) {
			int indexToShift = findIndexByLetter(alphabet, keyAsArray[i % keyAsArray.length]);
			int currentIndex = findIndexByLetter(alphabet, sentenceAsArray[i]);
			result[i] = alphabet[(currentIndex - indexToShift + 26) % alphabet.length];
		}
		return String.valueOf(result);
	}
	
	public void searchForKeyLength(String sentence) {
		boolean keyLengthFound = false;
		int keyLength = 1;
		char[] sentenceAsArray = sentence.replace(" ", "").toUpperCase().toCharArray();
		while(keyLength <= 5) {
			for(int i = 0; i < keyLength; i++) {
				char[] subSequence = new char[sentenceAsArray.length];
				int index = 0;
				for(int j = 0; j < sentenceAsArray.length; j++) {
					if(j % keyLength == 0) {
						subSequence[index] = sentenceAsArray[j];
						index++;
					}
				}
				System.out.println(subSequence);
			}
			keyLength++;
		}
	}

}
