package com.crypto.algo;

public class Cesar {
	
	public Cesar() {

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
	
	public char[] createKeyAlphabet(char[] alphabet, char letter) {
		int indexToShift = findIndexByLetter(alphabet, letter);
		char[] keyAlphabet = null;
		if(indexToShift != -1) {
			keyAlphabet = new char[26];
			for(int i = 0; i < alphabet.length; i++) {
				keyAlphabet[i] = alphabet[(i + indexToShift) % 26];
			}
		}
		return keyAlphabet;
	}
	
	public String encrypt(String sentence, char[] alphabet, char letter) {
		letter = Character.toUpperCase(letter);
		char[] keyAlphabet = createKeyAlphabet(alphabet, letter);
		char sentenceChar[] = sentence.toUpperCase().toCharArray();
		char encryptSentence[] = new char[sentence.length()];
		if(keyAlphabet != null) {
			for(int i = 0; i < sentenceChar.length; i++) {
				int index = findIndexByLetter(alphabet, sentenceChar[i]);
				if(index != -1) {
					encryptSentence[i] = keyAlphabet[index];
				}
			}
		}
		else {
			encryptSentence = "La clé ne fait pas partie de l'alphabet".toCharArray();
		}
		return String.valueOf(encryptSentence);
	}
	
	public String  decode(String sentence, char[] alphabet, char letter) {
		letter = Character.toUpperCase(letter);
		char sentenceChar[] = sentence.toUpperCase().toCharArray();
		char encryptSentence[] = new char[sentence.length()];
		char[] keyAlphabet = createKeyAlphabet(alphabet, letter);
		if(keyAlphabet != null) {
			for(int i = 0; i < sentenceChar.length; i++) {
				int index = findIndexByLetter(keyAlphabet, sentenceChar[i]);
				if(index != -1) {
					encryptSentence[i] = alphabet[index];
				}
			}
		}
		return String.valueOf(encryptSentence);
	}
	
	public void decrypt(String sentence, char[] alphabet) {
		for(int i = 0; i < alphabet.length; i++) {
			String decodedMessage = decode(sentence, alphabet, alphabet[i]);
			System.out.println(decodedMessage + " avec la clé " + alphabet[i]);
		}
	}

}
