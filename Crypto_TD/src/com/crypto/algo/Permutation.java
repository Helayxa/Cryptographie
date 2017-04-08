package com.crypto.algo;

import java.util.ArrayList;
import java.util.List;

public class Permutation {
	
	public Permutation() {

	}
	
	public char[] generateRandomAlphabetKey() {
		char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		char[] keyAlphabet = new char[26];
		List<Character> remainingLetters = new ArrayList<Character>();
		// Copie de l'alphabet dans une liste
		for(int i = 0; i < alphabet.length; i++) {
			remainingLetters.add(alphabet[i]);
		}
		// Création de l'alphabet mélangé
		for(int i = 0; i < alphabet.length; i++) {
			int randomIndex = (int) Math.round(Math.random() * (remainingLetters.size() - 1));
			keyAlphabet[i] = remainingLetters.get(randomIndex);
			remainingLetters.remove(randomIndex);
		}
		return keyAlphabet;
	}

}
