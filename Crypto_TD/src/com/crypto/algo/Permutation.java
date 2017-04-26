package com.crypto.algo;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.crypto.utils.Frequency;

public class Permutation {
	
	public static final List<Frequency> APPEARANCE_FREQUENCIES_FR = new ArrayList<>();
	static {
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('E', 17.26));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('A', 8.40));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('S', 8.08));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('I', 7.34));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('N', 7.13));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('T', 7.07));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('R', 6.55));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('L', 6.01));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('U', 5.74));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('O', 5.26));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('D', 4.18));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('C', 3.03));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('P', 3.01));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('M', 2.96));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('V', 1.32));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('G', 1.27));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('F', 1.12));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('B', 1.06));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('Q', 0.99));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('H', 0.92));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('X', 0.45));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('J', 0.31));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('Y', 0.30));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('Z', 0.12));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('K', 0.05));
		APPEARANCE_FREQUENCIES_FR.add(new Frequency('W', 0.04));
	}

	public Permutation() {

	}

	public char[] generateRandomAlphabetKey() {
		char[] alphabet = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		char[] keyAlphabet = new char[26];
		List<Character> remainingLetters = new ArrayList<Character>();
		// Copie de l'alphabet dans une liste
		for (int i = 0; i < alphabet.length; i++) {
			remainingLetters.add(alphabet[i]);
		}
		// Création de l'alphabet mélangé
		for (int i = 0; i < alphabet.length; i++) {
			int randomIndex = (int) Math.round(Math.random() * (remainingLetters.size() - 1));
			keyAlphabet[i] = remainingLetters.get(randomIndex);
			remainingLetters.remove(randomIndex);
		}
		return keyAlphabet;
	}

	public int findIndexByLetter(char[] alphabet, char letter) {
		int indexToStart = -1;
		/*
		 * Recherche de la première lettre de notre alphabet pour créer le
		 * nouvel alphabet avec décalage
		 */
		for (int i = 0; i < alphabet.length; i++) {
			if (alphabet[i] == letter) {
				indexToStart = i;
				break;
			}
		}
		return indexToStart;
	}

	public String encrypt(String sentence, char[] alphabet, char[] keyAlphabet) {
		char sentenceChar[] = sentence.replace(" ", "").toUpperCase().toCharArray();
		char encryptSentence[] = new char[sentence.length()];
		if (keyAlphabet != null) {
			for (int i = 0; i < sentenceChar.length; i++) {
				int index = findIndexByLetter(alphabet, sentenceChar[i]);
				if (index != -1) {
					encryptSentence[i] = keyAlphabet[index];
				}
			}
		} else {
			encryptSentence = "La clé ne fait pas partie de l'alphabet".toCharArray();
		}
		return String.valueOf(encryptSentence);
	}

	public String decode(String sentence, char[] alphabet, char[] keyAlphabet) {
		char sentenceChar[] = sentence.toUpperCase().toCharArray();
		char encryptSentence[] = new char[sentence.length()];
		if (keyAlphabet != null) {
			for (int i = 0; i < sentenceChar.length; i++) {
				int index = findIndexByLetter(keyAlphabet, sentenceChar[i]);
				if (index != -1) {
					encryptSentence[i] = alphabet[index];
				}
			}
		}
		return String.valueOf(encryptSentence);
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

	public String decryptWithFrequencies(char[] alphabet, String sentence) {
		List<Frequency> frequencies = getLettersAppearanceFrequency(alphabet, sentence);
		char[] sentenceArray = sentence.toUpperCase().toCharArray();
		for(int i = 0; i < sentenceArray.length; i++) {
			int index = searchLetterInList(frequencies, sentenceArray[i]);
			sentenceArray[i] = APPEARANCE_FREQUENCIES_FR.get(index).getLetter();
		}
		System.out.println(sentenceArray);
		try {
		    Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("result.txt"), "utf-8"));
		    writer.write(String.valueOf(sentenceArray));
		    writer.close();
		} catch (IOException ex) {
			
		}
		
		return String.valueOf(sentenceArray);
	}
}
