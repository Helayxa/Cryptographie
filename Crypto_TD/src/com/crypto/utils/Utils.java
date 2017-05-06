package com.crypto.utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {
	
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

	public Utils() {

	}
	
	/**
	 * Retourne l'index d'une lettre dans un alphabet donné
	 * @param alphabet : tableau de char dans lequel rechercher la lettre
	 * @param letter : lettre pour laquelle on recherche l'index
	 * @return Index de la lettre dans l'alphabet
	 */
	public static int findIndexByLetter(char[] alphabet, char letter) {
		int indexToStart = -1;
		for(int i = 0; i < alphabet.length; i++) {
			if(alphabet[i] == letter) {
				indexToStart = i;
				break;
			}
		}
		return indexToStart;
	}
	
	/**
	 * Retourne un booléen déterminant si le texte en entrée contient uniquement les caractères autorisés
	 * @param textChar : texte à tester sous forme de tableau de char
	 * @param alphabet : tableau des caractères disponibles
	 * @return : booléen déterminant si le texte est valide
	 */
	public static boolean validateTextFormat(char[] textChar, char[] alphabet) {
		boolean valid = true;
		for (int i = 0; i < textChar.length; i++) {
			int index = Utils.findIndexByLetter(alphabet, textChar[i]);
			if(index == -1 && textChar[i] != ' ') {
				System.out.println("Le caractère " + textChar[i] + " ne fait pas partie des caractères autorisés.");
				valid = false;
			}
		}
		return valid;
	}
	
	/**
	 * Retourne une liste d'objets Frequency qui contient la fréquence d'apparition de chaque lettre
	 * @param alphabet : tableau des caractères susceptibles d'apparaitre dans le texte
	 * @param text : texte dans lequel on veut récupérer la fréquence d'apparition des lettres
	 * @return : Liste d'objets Frequency triée par fréquence décroissante
	 */
	public static List<Frequency> getLettersAppearanceFrequency(char[] alphabet, String text) {
		char[] textChar = text.toCharArray();
		List<Frequency> frequencies = new ArrayList<>();
		/* Initialisation de la liste */
		for (int i = 0; i < alphabet.length; i++) {
			frequencies.add(new Frequency(alphabet[i], 0.0));
		}
		
		/* Permet de compter le nombre d'apparition de chaque lettre */
		for (int i = 0; i < textChar.length; i++) {
			if (Utils.findIndexByLetter(alphabet, textChar[i]) != -1) {
				int index = searchLetterInFrequencyList(frequencies, textChar[i]);
				if(index != -1) {
					frequencies.get(index).increment();
				}
			}
		}
		
		/* Calcule le pourcentage d'apparition de chaque lettre */
		for(int i = 0; i < frequencies.size(); i++) {
			frequencies.get(i).setAppearenceFrequency((frequencies.get(i).getAppearenceFrequency() / textChar.length) * 100);
		}
		
		frequencies.sort((o1, o2) -> Double.compare(o2.getAppearenceFrequency(), o1.getAppearenceFrequency()));
		return frequencies;
	}
	
	/**
	 * Retourne l'index d'une lettre dans une liste de Frequuency donnée
	 * @param frequencies: Liste d'objets Frequency dans laquelle rechercher une lettre
	 * @param letter : lettre à rechercher dans la liste
	 * @return : Index de l'objet Frequency qui contient la lettre recherchée
	 */
	public static int searchLetterInFrequencyList(List<Frequency> frequencies, char letter) {
		for(int i = 0; i < frequencies.size(); i++) {
			if(frequencies.get(i).getLetter() == letter) {
				return i;
			}
		}
		return -1;
	}
	
}
