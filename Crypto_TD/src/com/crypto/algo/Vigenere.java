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
	 * Permet d'encoder un texte � partir d'une cl� donn�e en utilisant le chiffrement de Vigen�re.
	 * @param alphabet : contient l'alphabet en majuscule
	 * @param text : texte � encoder
	 * @param key : cl� pour encoder le texte
	 * @return : texte encod� ou le message d'erreur s'il y en a
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
				return "Le texte n'a pas pu �tre encod� correctement.";
			}
		} else {
			return "La cl� est invalide.";
		}
	}
	
	/**
	 * Permet de d�coder un texte � partir d'une cl� donn�e en utilisant le chiffrement de Vigen�re.
	 * @param alphabet : contient l'alphabet en majuscule
	 * @param text : texte � d�coder
	 * @param key : cl� pour d�coder le texte
	 * @return : texte d�cod� ou le message d'erreur s'il y en a
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
				return "Le texte n'a pas pu �tre encod� correctement.";
			}
		} else {
			return "La cl� est invalide.";
		}
	}
	
	/**
	 * Permet de d�coder un texte sans cl� en utilisant la cryptanalyse du chiffrement de Vigen�re.
	 * @param text : texte � d�crypter
	 * @param alphabet : contient l'alphabet en majuscule
	 * @return : le texte d�crypt�
	 */
	public String decrypt(String text, char[] alphabet) {
		List<String> sequences = searchForKeyLength(text, alphabet);
		if(!sequences.isEmpty() && sequences != null) {
			String key = searchForKey(sequences, text, alphabet);
			if(key != null && !key.isEmpty()) {
				System.out.println("La cl� de d�cryptage est : " + key);
				return decode(alphabet, text, key);
			} else {
				return "Impossible de retrouver la clef pour d�crypter";
			}
		} else {
			return "Impossible de trouver la longueur de la clef";
		}
	}
	
	/**
	 * Retourne la longueur de la cl� en fonction du texte � d�crypter.
	 * @param text : texte � d�crypter
	 * @param alphabet : contient l'alphabet en majuscule
	 * @return : longueur de la cl� utilis�e pour encoder le texte
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
			System.out.println("Impossible de trouver un indice de de co�ncidence correct.");
		}
		
		return sequences;
	}
	
	/**
	 * Retourne la cl� utilis�e pour encoder le texte
	 * @param sequences : Liste des sous-s�quences possibles du texte en fonction de la longueur de la clef
	 * @param text : texte � d�crypter
	 * @param alphabet : contient l'alphabet en majuscule
	 * @return : la cl� ayant permis l'encodage du texte
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
	 * Permet de calculer l'indice de co�ncidence d'une phrase
	 * @param subSequence : sous-s�quence provenant du d�coupage du texte en fonction de la longueur de la clef, pour laquelle on veut calculer l'IC
	 * @param alphabet : contient l'alphabet en majuscule
	 * @return : l'indice de co�ncidence associ� � la sous-s�quence pass�e en param�tre
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
