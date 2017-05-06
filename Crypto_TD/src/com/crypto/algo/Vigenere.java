package com.crypto.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.crypto.utils.Frequency;
import com.crypto.utils.Utils;

public class Vigenere {
	
	public static final double COINCIDENCE_INDICATOR = 0.078;
	
	public Vigenere() {

	}

	/**
	 * Permet d'encoder un texte à partir d'une clé donnée en utilisant le chiffrement de Vigenère.
	 * @param alphabet : contient l'alphabet en majuscule
	 * @param text : texte à encoder
	 * @param key : clé pour encoder le texte
	 * @return : texte encodé ou le message d'erreur s'il y en a
	 */
	public String encode(char[] alphabet, String text, String key) {
		char[] keyAsArray = key.toCharArray();
		boolean validKey = Utils.validateTextFormat(keyAsArray, alphabet);
		if(validKey) {
			char[] textChar = text.replace(" ", "").toCharArray();
			boolean validText = Utils.validateTextFormat(textChar, alphabet);
			if(validText) {
				StringBuilder result = new StringBuilder();
				for(int i = 0; i < textChar.length; i++) {
					int indexToShift = Utils.findIndexByLetter(alphabet, keyAsArray[i % keyAsArray.length]);
					int currentLetterIndexInAlphabet = Utils.findIndexByLetter(alphabet, textChar[i]);
					int letterEncodedIndex = (currentLetterIndexInAlphabet + indexToShift) % alphabet.length;
					result.append(alphabet[letterEncodedIndex]);
				}
				return result.toString();
			} else {
				return "Le texte n'a pas pu être encodé correctement.";
			}
		} else {
			return "La clé est invalide.";
		}
	}
	
	/**
	 * Permet de décoder un texte à partir d'une clé donnée en utilisant le chiffrement de Vigenère.
	 * @param alphabet : contient l'alphabet en majuscule
	 * @param text : texte à décoder
	 * @param key : clé pour décoder le texte
	 * @return : texte décodé ou le message d'erreur s'il y en a
	 */
	public String decode(char[] alphabet, String text, String key) {
		char[] keyAsArray = key.toCharArray();
		boolean validKey = Utils.validateTextFormat(keyAsArray, alphabet);
		if(validKey) {
			char[] textChar = text.replace(" ", "").toCharArray();
			boolean validText = Utils.validateTextFormat(textChar, alphabet);
			if(validText) {
				StringBuilder result = new StringBuilder();
				for(int i = 0; i < textChar.length; i++) {
					int indexToShift = Utils.findIndexByLetter(alphabet, keyAsArray[i % keyAsArray.length]);
					int currentLetterIndexInAlphabet = Utils.findIndexByLetter(alphabet, textChar[i]);
					int letterEncodedIndex = (currentLetterIndexInAlphabet - indexToShift + 26) % alphabet.length;
					result.append(alphabet[letterEncodedIndex]);
				}
				return result.toString();
			} else {
				return "Le texte n'a pas pu être encodé correctement.";
			}
		} else {
			return "La clé est invalide.";
		}
	}
	
	/**
	 * Permet de décoder un texte sans clé en utilisant la cryptanalyse du chiffrement de Vigenère.
	 * @param text : texte à décrypter
	 * @param alphabet : contient l'alphabet en majuscule
	 * @return : le texte décrypté
	 */
	public String decrypt(String text, char[] alphabet) {
		List<String> sequences = searchForKeyLength(text, alphabet);
		if(!sequences.isEmpty() && sequences != null) {
			String key = searchForKey(sequences, text, alphabet);
			if(key != null && !key.isEmpty()) {
				System.out.println("La clé de décryptage est : " + key);
				return decode(alphabet, text, key);
			} else {
				return "Impossible de retrouver la clef pour décrypter";
			}
		} else {
			return "Impossible de trouver la longueur de la clef";
		}
	}
	
	/**
	 * Retourne la longueur de la clé en fonction du texte à décrypter.
	 * @param text : texte à décrypter
	 * @param alphabet : contient l'alphabet en majuscule
	 * @return : longueur de la clé utilisée pour encoder le texte
	 */
	public List<String> searchForKeyLength(String text, char[] alphabet) {
		double coincidenceIndicator = 0;
		int keyLength = 0;
		char[] textChar = text.replace(" ", "").toUpperCase().toCharArray();
		List<String> sequences = new ArrayList<>();
		while(coincidenceIndicator <= COINCIDENCE_INDICATOR && keyLength < textChar.length) {
			keyLength++;
			coincidenceIndicator = 0;
			sequences.clear();
			for(int i = 0; i < keyLength; i++) {
				StringBuilder subSequence = new StringBuilder();
				for(int j = 0; j < textChar.length; j++) {
					if((j + i) % keyLength == 0) {
						subSequence.append(textChar[j]);
					}
				}
				coincidenceIndicator += calculateCoincidenceIndicator(subSequence.toString(), alphabet);
				sequences.add(subSequence.toString());
			}
			coincidenceIndicator /= keyLength;
		}

		if(keyLength == textChar.length) {
			System.out.println("Impossible de trouver un indice de de coïncidence correct.");
		}
		
		return sequences;
	}
	
	/**
	 * Retourne la clé utilisée pour encoder le texte
	 * @param sequences : Liste des sous-séquences possibles du texte en fonction de la longueur de la clef
	 * @param text : texte à décrypter
	 * @param alphabet : contient l'alphabet en majuscule
	 * @return : la clé ayant permis l'encodage du texte
	 */
	public String searchForKey(List<String> sequences, String text, char[] alphabet) {
		StringBuilder key = new StringBuilder();
		for(String sequence : sequences) {
			List<Frequency> frequencies = Utils.getLettersAppearanceFrequency(alphabet, sequence);
			if(!frequencies.isEmpty()) {
				char firstChar = frequencies.get(0).getLetter();
				int keyLetterIndex = ((Utils.findIndexByLetter(alphabet, firstChar) - Utils.findIndexByLetter(alphabet, 'E')) + 26) % 26;
				key.append(alphabet[keyLetterIndex]);
			}
		}
		return key.toString();
	}
	
	/**
	 * Permet de calculer l'indice de coïncidence d'une phrase
	 * @param subSequence : sous-séquence provenant du découpage du texte en fonction de la longueur de la clef, pour laquelle on veut calculer l'IC
	 * @param alphabet : contient l'alphabet en majuscule
	 * @return : l'indice de coïncidence associé à la sous-séquence passée en paramètre
	 */
	public double calculateCoincidenceIndicator(String subSequence, char[] alphabet) {
		double coincidenceIndicator = 0;
		char[] subSequenceArray = subSequence.toCharArray();
		Map<Character, Double> frequencies = new HashMap<>();
		
		for(int i = 0; i < alphabet.length; i++) {
			frequencies.put(alphabet[i], 0.0);
		}
		
		for(int i = 0; i < subSequenceArray.length; i++) {
			frequencies.put(subSequenceArray[i], frequencies.get(subSequenceArray[i]) + 1);
		}

		for(Entry<Character, Double> e: frequencies.entrySet()) {
			coincidenceIndicator += ((e.getValue() * (e.getValue() - 1.0)) / (subSequenceArray.length * (subSequenceArray.length - 1.0)));
		}
		return coincidenceIndicator;
	}

}
