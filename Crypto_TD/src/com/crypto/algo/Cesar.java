package com.crypto.algo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.crypto.utils.Utils;

public class Cesar {
		
	public Cesar() {

	}
	
	/**
	 * Permet d'encoder un texte à partir d'une clé donnée en utilisant le Cryptogramme de César.
	 * @param text : texte à encoder
	 * @param alphabet : contient l'alphabet en majuscule
	 * @param key : clé pour encoder le texte
	 * @return : texte encodé ou le message d'erreur s'il y en a
	 */
	public String encode(String text, char[] alphabet, char key) {
		int keyIndex = Utils.findIndexByLetter(alphabet, key);
		char textChar[] = text.toCharArray();
		StringBuilder result = new StringBuilder();
		boolean wrongText = false;
		if(keyIndex != -1) {
			for(int i = 0; i < textChar.length; i++) {
				int index = Utils.findIndexByLetter(alphabet, textChar[i]);
				if(textChar[i] == ' ') {
					result.append(' ');
				} else if(index != -1) {
					result.append(alphabet[(index + keyIndex) % 26]);
				} else {
					System.out.println("Le caractère " + textChar[i] + " ne fait pas partie des caractères autorisés.");
					wrongText = true;
				}
			}
		} else {
			System.out.println("La clef n'est pas contenue dans l'alphabet !");
		}
		return wrongText ? "Le texte n'a pas pu être encodé correctement." : result.toString();
	}
	
	/**
	 * Permet de décoder un texte à partir d'une clé donnée en utilisant le Cryptogramme de César.
	 * @param text : texte à décoder
	 * @param alphabet : contient l'alphabet en majuscule
	 * @param key : clé pour décoder le texte
	 * @return : texte encodé ou le message d'erreur s'il y en a
	 */
	public String  decode(String text, char[] alphabet, char key) {
		int keyIndex = Utils.findIndexByLetter(alphabet, key);
		char textChar[] = text.toCharArray();
		StringBuilder result = new StringBuilder();
		boolean wrongText = false;
		if(keyIndex != -1) {
			for(int i = 0; i < textChar.length; i++) {
				int index = Utils.findIndexByLetter(alphabet, textChar[i]);
				if(textChar[i] == ' ') {
					result.append(' ');
				} else if(index != -1) {
					result.append(alphabet[(index - keyIndex + 26) % 26]);
				} else {
					System.out.println("Le caractère " + textChar[i] + " ne fait pas partie des caractères autorisés.");
					wrongText = true;
				}
			}
		} else {
			System.out.println("La clef n'est pas contenue dans l'alphabet !");
		}
		return wrongText ? "Le texte n'a pas pu être décodé correctement." : result.toString();
	}
	
	/**
	 * Permet de décrypter un texte sans clé en utilisant le Cryptogramme de César et d'écrire le résultat dans un fichier texte.
	 * @param text : texte à décoder
	 * @param alphabet : contient l'alphabet en majuscule
	 */
	public void decrypt(String text, char[] alphabet) {
		String filename = "cesar_decrypted.txt";
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
			for(int i = 0; i < alphabet.length; i++) {
				String decodedMessage = decode(text, alphabet, alphabet[i]);
				System.out.println(decodedMessage + " avec la clé " + alphabet[i]);
				bw.write(decodedMessage + " avec la clé " + alphabet[i] + "\n");
			}
			System.out.println("Ce resultat a egalement ete sauvegarde dans le fichier : " + filename);
			bw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
