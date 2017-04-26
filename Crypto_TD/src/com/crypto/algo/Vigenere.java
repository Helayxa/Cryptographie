package com.crypto.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Vigenere {
	
	public static final double COINCIDENCE_INDICATOR = 0.078;
	
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
	
	public List<List<Character>> searchForKeyLength(String sentence, char[] alphabet) {
		double coincidenceIndicator = 0;
		int keyLength = 0;
		char[] sentenceAsArray = sentence.replace(" ", "").toUpperCase().toCharArray();
		List<List<Character>> sequences = new ArrayList<>();
		while(coincidenceIndicator <= COINCIDENCE_INDICATOR && keyLength < sentenceAsArray.length) {
			keyLength++;
			coincidenceIndicator = 0;
			sequences.clear();
			for(int i = 0; i < keyLength; i++) {
				List<Character> subSequence = new ArrayList<>();
				for(int j = 0; j < sentenceAsArray.length; j++) {
					if((j + i) % keyLength == 0) {
						subSequence.add(sentenceAsArray[j]);
					}
				}
				coincidenceIndicator += calculateCoincidenceIndicator(subSequence, alphabet);
				sequences.add(subSequence);
			}
			coincidenceIndicator /= keyLength;
		}
		
		if(keyLength == sentenceAsArray.length) {
			System.out.println("Impossible de trouver un indicende de coïncidence correct.");
			keyLength = -1;
		} else {
			convertCharactersListToString(sequences);
		}
		
		System.out.println("La clé de décryptage est de longueur " + keyLength);
		return sequences;
	}
	
	public void convertCharactersListToString(List<List<Character>> sequences) {
		StringBuilder result = new StringBuilder(sequences.get(0).size());
		for (Character c : sequences.get(0)) {
		  result.append(c);
		}
		String output = result.toString();
	}
	
	public void getLettersAppearanceFrequency(List<List<Character>> sequences, String sentence) {
		
	}
	
	public void decrypt(String sentence, char[] alphabet) {
		List<List<Character>> sequences = searchForKeyLength(sentence, alphabet);
		getLettersAppearanceFrequency(sequences, sentence);
	}
	
	public double calculateCoincidenceIndicator(List<Character> sequence, char[] alphabet) {
		double coincidenceIndicator = 0;
		Map<Character, Double> frequencies = new HashMap<>();
		
		for(int i = 0; i < alphabet.length; i++) {
			frequencies.put(alphabet[i], 0.0);
		}
		
		for(int i = 0; i < sequence.size(); i++) {
			frequencies.put(sequence.get(i), frequencies.get(sequence.get(i)) + 1);
		}
		System.out.println(frequencies);
		for(Entry<Character, Double> e: frequencies.entrySet()) {
			coincidenceIndicator += ((e.getValue() * (e.getValue() - 1.0)) / (sequence.size() * (sequence.size() - 1.0)));
		}
		return coincidenceIndicator;
	}

}
