package com.crypto.algo;

import java.util.ArrayList;
import java.util.List;

import com.crypto.utils.Frequency;
import com.crypto.utils.Utils;

public class Permutation {

	public Permutation() {

	}

	/**
	 * Permet de générer un alphabet aléatoire qui servira de clé pour les différentes méthodes
	 * de l'algorithme de Permutation
	 * @param alphabet : alphabet de base avec toutes les lettres majuscules
	 * @return : un alphabet aléatoire contenant toutes les lettres majuscules
	 */
	public char[] generateRandomAlphabetKey(char[] alphabet) {
		char[] randomAlphabet = new char[alphabet.length];
		List<Character> remainingLetters = new ArrayList<Character>();
		for (int i = 0; i < alphabet.length; i++) {
			remainingLetters.add(alphabet[i]);
		}
		for (int i = 0; i < alphabet.length; i++) {
			int randomIndex = (int) Math.round(Math.random() * (remainingLetters.size() - 1));
			randomAlphabet[i] = remainingLetters.get(randomIndex);
			remainingLetters.remove(randomIndex);
		}
		return randomAlphabet;
	}

	/**
	 * Permet d'encoder un texte à partir d'une clé donnée en utilisant la Permutation de lettres.
	 * @param text : texte à encoder
	 * @param alphabet : contient l'alphabet en majuscule
	 * @param keyAlphabet : contient un alphabet aléatoire avec toutes les lettres en majuscule
	 * @return : texte encodé ou le message d'erreur s'il y en a
	 */
	public String encode(String text, char[] alphabet, char[] keyAlphabet) {
		char textChar[] = text.toCharArray();
		boolean wrongText = false;
		StringBuilder result = new StringBuilder();
		if (keyAlphabet != null) {
			for (int i = 0; i < textChar.length; i++) {
				int index = Utils.findIndexByLetter(alphabet, textChar[i]);

				if(textChar[i] == ' ') {
					result.append(' ');
				} else if(index != -1) {
					result.append(keyAlphabet[index]);
				} else {
					System.out.println("Le caractère " + textChar[i] + " ne fait pas partie des caractères autorisés.");
					wrongText = true;
				}
			}
		} else {
			return "La clé est vide.";
		}
		return wrongText ? "Le texte n'a pas pu être encodé correctement." : result.toString();
	}

	/**
	 * Permet de décoder un texte à partir d'une clé donnée en utilisant la Permutation de lettres.
	 * @param text : texte à décoder
	 * @param alphabet : contient l'alphabet en majuscule
	 * @param keyAlphabet : contient un alphabet aléatoire avec toutes les lettres en majuscule
	 * @return : texte décodé ou le message d'erreur s'il y en a
	 */
	public String decode(String text, char[] alphabet, char[] keyAlphabet) {
		char textChar[] = text.toCharArray();
		boolean wrongText = false;
		StringBuilder result = new StringBuilder();
		if (keyAlphabet != null) {
			for (int i = 0; i < textChar.length; i++) {
				int index = Utils.findIndexByLetter(keyAlphabet, textChar[i]);

				if(textChar[i] == ' ') {
					result.append(' ');
				} else if(index != -1) {
					result.append(alphabet[index]);
				} else {
					System.out.println("Le caractère " + textChar[i] + " ne fait pas partie des caractères autorisés.");
					wrongText = true;
				}
			}
		} else {
			return "La clé est vide.";
		}
		return wrongText ? "Le texte n'a pas pu être décodé correctement." : result.toString();
	}
	
	/**
	 * Permet de décrypter un texte encodé en utilisant la Permutation de lettres et la fréquence d'apparition des lettres
	 * @param alphabet : contient l'alphabet en majuscule
	 * @param text : texte à décrypter
	 * @return : Le résultat du texte décrypté
	 */
	public String decryptWithFrequencies(char[] alphabet, String text) {
		char[] textChar = text.toCharArray();
		boolean validText = Utils.validateTextFormat(textChar, alphabet);
		StringBuilder result = new StringBuilder();
		if(validText) {
			List<Frequency> frequencies = Utils.getLettersAppearanceFrequency(alphabet, text);
			if(!frequencies.isEmpty()) {
				for(int i = 0; i < textChar.length; i++) {
					int index = Utils.searchLetterInFrequencyList(frequencies, textChar[i]);
					if(textChar[i] == ' ') {
						result.append(' ');
					}
					result.append(Utils.APPEARANCE_FREQUENCIES_FR.get(index).getLetter());
				}
			}
		} else {
			System.out.println("Le texte n'a pas pu être décrypté correctement.");
		}
		return result.toString();
	}
}
