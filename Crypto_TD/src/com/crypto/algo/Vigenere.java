package com.crypto.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.crypto.utils.Frequency;

public class Vigenere {
	
	public static final double COINCIDENCE_INDICATOR = 0.078;
	private static final char E = 'E';
	
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
	
	public List<String> searchForKeyLength(String sentence, char[] alphabet) {
		double coincidenceIndicator = 0;
		int keyLength = 0;
		char[] sentenceAsArray = sentence.replace(" ", "").toUpperCase().toCharArray();
		List<List<Character>> sequences = new ArrayList<>();
		List<String> keySentences = new ArrayList<>();
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
			for(List<Character> sequence : sequences) {
				keySentences.add(convertCharactersListToString(sequence));
			}
		}
		
		System.out.println("La clé de décryptage est de longueur " + keyLength);
		return keySentences;
	}
	
	public String convertCharactersListToString(List<Character> sequence) {
		StringBuilder result = new StringBuilder(sequence.size());
		for (Character c : sequence) {
		  result.append(c);
		}
		String output = result.toString();
		return output;
	}
	
	public List<Frequency> getLettersAppearanceFrequency(char[] alphabet, String sentence) {
		List<Frequency> frequencies = new ArrayList<>();
		for (int i = 0; i < alphabet.length; i++) {
			frequencies.add(new Frequency(alphabet[i], 0.0));
		}

		char[] sentenceArray = sentence.toUpperCase().toCharArray();
		for (int i = 0; i < sentenceArray.length; i++) {
			if (findIndexByLetter(alphabet, sentenceArray[i]) != -1) {
				int index = searchLetterInList(frequencies, sentenceArray[i]);
				if(index != -1) {
					frequencies.get(index).increment();
				}
			}
		}
		
		for(int i = 0; i < frequencies.size(); i++) {
			frequencies.get(i).setAppearenceFrequency((frequencies.get(i).getAppearenceFrequency() / sentenceArray.length) * 100);
		}
		frequencies.sort((o1, o2) -> Double.compare(o2.getAppearenceFrequency(), o1.getAppearenceFrequency()));
		return frequencies;
	}
	
	private int searchLetterInList(List<Frequency> frequencies, char letter) {
		for(int i = 0; i < frequencies.size(); i++) {
			if(frequencies.get(i).getLetter() == letter) {
				return i;
			}
		}
		return -1;
	}
	
	public String decrypt(String sentence, char[] alphabet) {
		List<String> sequences = searchForKeyLength(sentence, alphabet);
		String key = searchForKey(sequences, sentence, alphabet);
		System.out.println("La clé de décryptage est : " + key);
		return decode(alphabet, sentence, key);
	}
	
	public String searchForKey(List<String> sequences, String sentence, char[] alphabet) {
		List<Character> key = new ArrayList<>();
		for(String sequence : sequences) {
			List<Frequency> frequencies = getLettersAppearanceFrequency(alphabet, sequence);
			if(!frequencies.isEmpty()) {
				char firstChar = frequencies.get(0).getLetter();
				int keyLetterIndex = ((findIndexByLetter(alphabet, firstChar) - findIndexByLetter(alphabet, E)) + 26) % 26;
				key.add(alphabet[keyLetterIndex]);
			}
		}
		return convertCharactersListToString(key);
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

		for(Entry<Character, Double> e: frequencies.entrySet()) {
			coincidenceIndicator += ((e.getValue() * (e.getValue() - 1.0)) / (sequence.size() * (sequence.size() - 1.0)));
		}
		return coincidenceIndicator;
	}

}
