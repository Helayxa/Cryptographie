package com.crypto.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.crypto.algo.Cesar;
import com.crypto.algo.MerkleHellman;
import com.crypto.algo.MerkleHellmanResult;
import com.crypto.algo.Permutation;
import com.crypto.algo.Vigenere;
import com.crypto.utils.Frequency;
import com.crypto.utils.Utils;

public class Main {

	public static final int STOP_OPTION = 4;
	
	public static void main(String[] args) {
		if(args.length >= 2) {
			switch(args[0]) {
				case "Cesar" :
					cesarAlgorithm(args);
					break;
				case "Permutation" :
					permutationAlgorithm(args);
					break;
				case "Vigenere" :
					vigenereAlgorithm(args);
					break;
				case "MerkleHellman":
					merkleHellmanAlgorithm(args);
					break;
				default:
					System.out.println("Le nom de l'algorithme saisi n'est pas pris en compte. Nom des algorithmes disponibles :\n- Cesar\n- Permutation\n- Vigenere\n- MerkleHellman");
					break;
			}
		} else {
			System.out.println("Le nombre d'arguments saisi est incorrect.\n- Argument 1 : nom de l'algorithme (obligatoire)\n- Argument 2 : nom de l'action (obligatoire)\n- Argument 3 : nom du fichier contenant le texte à traiter (facultatif)\n- Argument 4 : document contenant la clé pour la permutation (facultatif)");
		}
	}
	
	/**
	 * Permet de gérer les différentes actions possibles avec le Cryptogramme de César.
	 * @param args : arguments passés en paramètres du programme
	 */
	public static void cesarAlgorithm(String[] args) {
		Cesar cesar = new Cesar();
		char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		
		switch(args[1]) {
			case "encode":
				String[] encodeParameters = getEncodingParameters(args, true);
				String encodedText = cesar.encode(encodeParameters[0], alphabet, encodeParameters[1].charAt(0));
				if(!encodedText.isEmpty()) {
					showAndSaveResult(encodedText, "cesar_encoded.txt");
				}
				break;
			case "decode" :
				String[] decodeParameters = getDecodingParameters(args, true);
				String decodedText = cesar.decode(decodeParameters[0], alphabet, decodeParameters[1].charAt(0));
				if(!decodedText.isEmpty()) {
					showAndSaveResult(decodedText, "cesar_decoded.txt");
				}
				break;
			case "decrypt" :
				String textToDecrypt = getDecryptParameters(args);
				cesar.decrypt(textToDecrypt, alphabet);
				break;
			default:
				System.out.println("L'action demandee n'est pas prise en compte. Actions supportees : encode, decode, decrypt");
		}
	}
	
	/**
	 * Permet de gérer les différentes actions possibles avec la Permutation de lettres.
	 * @param args : arguments passés en paramètres du programme
	 */
	public static void permutationAlgorithm(String[] args) {
		Permutation permutation = new Permutation();
		char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		char[] permutationAlphabet = getPermutationRandomAlphabet(args, alphabet);
		switch(args[1]) {
			case "encode":
				System.out.println("L'alphabet utilisé comme clé pour la permutation est le suivant : ");
				System.out.println(permutationAlphabet);
				System.out.println();
				String[] encodeParameters = getEncodingParameters(args, false);
				String encodedText = permutation.encode(encodeParameters[0], alphabet, permutationAlphabet);
				if(!encodedText.isEmpty()) {
					showAndSaveResult(encodedText, "permutation_encoded.txt");
				}
				break;
			case "decode" :
				System.out.println("L'alphabet utilisé comme clé pour la permutation est le suivant : ");
				System.out.println(permutationAlphabet);
				System.out.println();
				String[] decodeParameters = getDecodingParameters(args, false);
				String decodedText = permutation.decode(decodeParameters[0], alphabet, permutationAlphabet);
				if(!decodedText.isEmpty()) {
					showAndSaveResult(decodedText, "permutation_decoded.txt");
				}
				break;
			case "decrypt" :
				System.out.println("L'alphabet utilisé comme clé pour la permutation est le suivant : ");
				System.out.println(permutationAlphabet);
				System.out.println();
				String textToDecrypt = getDecryptParameters(args);
				String decryptedText = permutation.decryptWithFrequencies(alphabet, textToDecrypt);
				if(!decryptedText.isEmpty()) {
					showAndSaveResult(decryptedText, "permutation_decrypted.txt");
				}
				break;
			case "frequency" :
				String text = getDecryptParameters(args);
				List<Frequency> frequencies = Utils.getLettersAppearanceFrequency(alphabet, text);
				StringBuilder result = new StringBuilder();
				result.append("Pour le message : " + text + "\n");
				for(Frequency f : frequencies) {
					DecimalFormat formatter = new DecimalFormat("#0.00");
					result.append("Fréquence d'apparition de la lettre " + f.getLetter() + " : " + formatter.format(f.getAppearenceFrequency()) + " %\n");
				}
				
				if(!result.toString().isEmpty()) {
					showAndSaveResult(result.toString(), "permutation_frequency.txt");
				}
				break;
			default:
				System.out.println("L'action demandee n'est pas prise en compte. Actions supportees : encode, decode, decrypt, frequency");
		}
	}
	
	/**
	 * Permet de gérer les différentes actions possibles avec le protocole de Vigenère.
	 * @param args : arguments passés en paramètres du programme
	 */
	public static void vigenereAlgorithm(String[] args) {
		Vigenere vigenere = new Vigenere();
		char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		switch(args[1]) {
			case "encode":
				String[] encodeParameters = getEncodingParameters(args, true);
				String encodedText = vigenere.encode(alphabet, encodeParameters[0], encodeParameters[1]);
				if(!encodedText.isEmpty()) {
					showAndSaveResult(encodedText, "vigenere_encoded.txt");
				}
				break;
			case "decode" :
				String[] decodeParameters = getDecodingParameters(args, true);
				String decodedText = vigenere.decode(alphabet, decodeParameters[0], decodeParameters[1]);
				if(!decodedText.isEmpty()) {
					showAndSaveResult(decodedText, "vigenere_decoded.txt");
				}
				break;
			case "decrypt" :
				String textToDecrypt = getDecryptParameters(args);
				String decryptedText = vigenere.decrypt(textToDecrypt, alphabet);
				if(!decryptedText.isEmpty()) {
					showAndSaveResult(decryptedText, "vigenere_decrypted.txt");
				}
				break;
			default:
				System.out.println("L'action demandee n'est pas prise en compte. Actions supportees : encode, decode, decrypt");
		}
	}
	
	public static void merkleHellmanAlgorithm(String[] args){
		MerkleHellman merkleHellman = new MerkleHellman();
		MerkleHellmanResult mh = new MerkleHellmanResult();
		List<Long> decryptList = new ArrayList<Long>();
		List<Long> privateList = new ArrayList<Long>();
		String decodedText, decryptedText;
		char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		switch(args[1]) {
			case "encode":
				String[] encodeParameters = getEncodingParameters(args, false);
				MerkleHellmanResult mhResult = merkleHellman.encode(encodeParameters[0], alphabet);
				showAndSaveResult(mhResult.toString(), "merkleHellman_encode.txt");
				break;
			case "decode" :
				mh = getDecodingParametersMH(args, false);
				decryptList = merkleHellman.getDecryptKeyList(mh.getP(), mh.getM(), mh.getEncodeList());
				decodedText = merkleHellman.decode(decryptList, mh.getPrivateKey(), alphabet);
				if(!decodedText.isEmpty()) {
					showAndSaveResult(decodedText, "merkleHellman_decoded.txt");
				}
				break;
			case "decrypt" :
				mh = getDecodingParametersMH(args, true);
				privateList = merkleHellman.getPrivateKey(mh.getP(), mh.getM(), mh.getPublicKey());
				decryptList = merkleHellman.getDecryptKeyList(mh.getP(), mh.getM(), mh.getEncodeList());
				decryptedText = merkleHellman.decode(decryptList, privateList, alphabet);
				if(!decryptedText.isEmpty()) {
					showAndSaveResult(decryptedText, "merkleHellman_decrypted.txt");
				}
				break;
			default:
				System.out.println("L'action demandee n'est pas prise en compte. Actions supportees : encode, decode, decrypt");
		}
	}
	
	/**
	 * Permet de récupérer les paramètres nécessaires à l'encodage d'un texte
	 * @param args : arguments passés en paramètres du programme
	 * @param askForKey : booléen précisant s'il faut demander une clef pour l'encodage
	 * @return : tableau des paramètres pour l'encodage
	 */
	public static String[] getEncodingParameters(String[] args, boolean askForKey) {
		String[] parameters = new String[2];
		Scanner sc = new Scanner(System.in);
		if(args.length >= 3) {
			parameters[0] = getFileText(args[2]);
		} else {
			System.out.println("Veuillez entrer la chaine de caracteres a chiffrer : ");
			parameters[0] = sc.nextLine();
		}
		if(askForKey) {
			System.out.println("Veuillez saisir la clef de chiffrement : ");
			parameters[1] = sc.nextLine();
		}
		sc.close();
		return parameters;
	}
	
	/**
	 * Permet de récupérer les paramètres nécessaires au décodage d'un texte
	 * @param args : arguments passés en paramètres du programme
	 * @param askForKey : booléen précisant s'il faut demander une clef pour le décodage
	 * @return : tableau des paramètres pour le décodage
	 */
	public static String[] getDecodingParameters(String[] args, boolean askForKey) {
		String[] parameters = new String[2];
		Scanner sc = new Scanner(System.in);
		if(args.length >= 3) {
			parameters[0] = getFileText(args[2]);
		} else {
			System.out.println("Veuillez entrer la chaine de caracteres a déchiffrer : ");
			parameters[0] = sc.nextLine();
		}
		if(askForKey) {
			System.out.println("Veuillez saisir la clef de déchiffrement : ");
			parameters[1] = sc.nextLine();
		}
		sc.close();
		return parameters;
	}
	
	public static MerkleHellmanResult getDecodingParametersMH(String[] args, boolean askForPublicKey){
		MerkleHellmanResult mhResult = new MerkleHellmanResult();
		Scanner sc = new Scanner(System.in);
		String parameter;
		String[] tab_parameter;
		List<Long> privateList = new ArrayList<Long>();
		List<Long> encodeList = new ArrayList<Long>();
		List<Long> publicList = new ArrayList<Long>();
		if(args.length >= 3){
			mhResult = getFileTextMH(args[2], askForPublicKey);
		} else{
			
			if(askForPublicKey){
				System.out.println("Veuillez entrer la liste public : ");
				parameter = sc.nextLine();
				tab_parameter = parameter.split(" ");
				for(int i=0; i<tab_parameter.length; i++){
					publicList.add(i, Long.valueOf(tab_parameter[i]));
				}
				mhResult.setPublicKey(publicList);
			} else{
				System.out.println("Veuillez entrer la liste privée : ");
				parameter = sc.nextLine();
				tab_parameter = parameter.split(" ");
				for(int i=0; i<tab_parameter.length; i++){
					privateList.add(i, Long.valueOf(tab_parameter[i]));
				}
				mhResult.setPrivateKey(privateList);
			}
			System.out.println("Veuillez entrer la liste encodée : ");
			parameter = sc.nextLine();
			tab_parameter = parameter.split(" ");
			for(int i=0; i<tab_parameter.length; i++){
				encodeList.add(i, Long.valueOf(tab_parameter[i]));
			}
			mhResult.setEncodeList(encodeList);
			System.out.println("Veuillez entrer p : ");
			mhResult.setP(Long.valueOf(sc.nextLine()));
			System.out.println("Veuillez entrer m : ");
			mhResult.setM(Long.valueOf(sc.nextLine()));
		}
		sc.close();
		return mhResult;
	}
	
	public static MerkleHellmanResult getFileTextMH(String filename, boolean askForPublicKey) {
		String[] parameters = new String[5];
		List<Long> privateList = new ArrayList<Long>();
		List<Long> encodeList = new ArrayList<Long>();
		List<Long> publicList = new ArrayList<Long>();
		MerkleHellmanResult mhResult = new MerkleHellmanResult();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			for(int i=0; i<5; i++){
				parameters[i] = br.readLine();
			}
			br.close();
			String[] str_privateList = parameters[0].split(" ");
			for(int i=0; i<str_privateList.length; i++){
				privateList.add(i, Long.valueOf(str_privateList[i]));
			}
			String[] str_encodeList = parameters[1].split(" ");
			for(int i=0; i<str_encodeList.length; i++){
				encodeList.add(i, Long.valueOf(str_encodeList[i]));
			}
			mhResult.setPrivateKey(privateList);
			mhResult.setEncodeList(encodeList);
			mhResult.setP(Long.valueOf(parameters[2]));
			mhResult.setM(Long.valueOf(parameters[3]));
			if(askForPublicKey){
				String[] str_publicList = parameters[4].split(" ");
				for(int i=0; i<str_publicList.length; i++){
					publicList.add(i, Long.valueOf(str_publicList[i]));
				}
				mhResult.setPublicKey(publicList);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		return mhResult;
	}
	
	/**
	 * Permet de récupérer le texte à décrypter
	 * @param args : arguments passés en paramètres du programme
	 * @return : le texte à décrypter
	 */
	public static String getDecryptParameters(String[] args) {
		String text = "";
		Scanner sc = new Scanner(System.in);
		if(args.length >= 3) {
			text = getFileText(args[2]);
		} else {
			System.out.println("Veuillez entrer la chaine de caracteres : ");
			text = sc.nextLine();
		}
		sc.close();
		return text;
	}
	
	/**
	 * Retourne le texte contenu sur la première ligne d'un fichier texte
	 * @param filename : nom du fichier
	 * @return : le texte contenu sur la première ligne du fichier
	 */
	public static String getFileText(String filename) {
		String text = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			text = br.readLine();
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return text;
	}
	
	/**
	 * Affiche le résultat d'une opération en console et sauvegarde ce résultat dans un fichier
	 * @param result : résultat à afficher et sauvegarder
	 * @param filename : nom du fichier de sauvegarde
	 */
	public static void showAndSaveResult(String result, String filename) {
		System.out.println("Voici le resultat obtenu : ");
		System.out.println(result);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
			bw.write(result);
			System.out.println("Ce resultat a egalement ete sauvegarde dans le fichier : " + filename);
			bw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveKey() {
		
	}
	
	/**
	 * Retourne l'alphabet nécessaire pour la Permutation de lettres. On essaie de le récupérer à partir du fichier passé en paramètre d'entrée,
	 * sinon dans un fichier généré automatiquement à chaque nouvelle génération d'alphabet, ou alors on le génère aléatoirement si aucun fichier n'existe
	 * @param args : arguments passés en paramètres du programme
	 * @param alphabet : alphabet de base 
	 * @return : alphabet aléatoire ou l'alphabet saisi dans un fichier par l'utilisateur
	 */
	public static char[] getPermutationRandomAlphabet(String[] args, char[] alphabet) {
		Permutation permutation = new Permutation();
		char[] permutationAlphabet = new char[26];
		if(args.length >= 4) {
			permutationAlphabet = getFileText(args[3]).toCharArray();
			savePermutationAlphabetInFile(permutationAlphabet);
		} else if(new File("permutation_random_alphabet.txt").exists()) {
			permutationAlphabet = getFileText("permutation_random_alphabet.txt").toCharArray();
		} else {
			permutationAlphabet = permutation.generateRandomAlphabetKey(alphabet);
			savePermutationAlphabetInFile(permutationAlphabet);
		}
		return permutationAlphabet;
	}
	
	public static void savePermutationAlphabetInFile(char[] alphabet) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("permutation_random_alphabet.txt"));
			bw.write(alphabet);
			bw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}


