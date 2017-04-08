package com.crypto.main;

import java.util.Scanner;

import com.crypto.algo.Cesar;

public class Main {

	public static void main(String[] args) {
		Cesar cesar = new Cesar();
		char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		/*String messageToEncrypt = "Chiffrez moi";
		System.out.println("Message � chiffrer : " + messageToEncrypt);
		System.out.println("Message chiffr� : " + cesar.encrypt(messageToEncrypt, alphabet, 'g'));
		System.out.println();
		String messageToDecode = "NCJA EZTD PLDJ";
		System.out.println("Message � d�chiffrer : " + messageToDecode);
		System.out.println("Message d�chiffr� : " + cesar.decode(messageToDecode, alphabet, 'l'));
		cesar.decrypt("MH SHQVH TXH FHVW DFTXLV", alphabet);
		*/
		String sentence;
		String s_key;
		char key;
		Scanner sc = new Scanner(System.in); 
		System.out.println("========================================");
	    System.out.println("|          MENU SELECTION              |");
	    System.out.println("========================================");
	    System.out.println("| Options:                             |");
	    System.out.println("|        1. Codage C�sar               |");
	    System.out.println("|        2. Codage Permutation         |");
	    System.out.println("|        3. Codage Vegen�re            |");
	    System.out.println("|        4. D�codage C�sar             |");
	    System.out.println("|        5. D�codage C�sar sans cl�    |");
	    System.out.println("|        6. D�codage Permutation       |");
	    System.out.println("|        7. D�codage Vegen�re          |");
	    System.out.println("|        8. Exit                       |");
	    System.out.println("========================================");
	    System.out.println("Veullez entrer votre choix :");
	    int opt = sc.nextInt();
	    
	    //Switch
	    switch(opt){
	    case 1:
	    	System.out.println("Codage C�sar, veuillez entrer la chaine de caract�res � encoder");
	    	sc.nextLine();
	    	sentence = sc.nextLine();
	    	System.out.println("Entrer la cl� d'encodage");
	    	s_key = sc.nextLine();
	    	key = s_key.charAt(0);
	    	System.out.println("Voici votre message crypt� :" + cesar.encrypt(sentence, alphabet, key));
	        break;
	    case 2:
	    	System.out.println("Codage Permutation");
	    	break;
	    case 3:
	    	System.out.println("Codage Vegen�re");
	    	break;
	    case 4:
	    	System.out.println("D�codage C�sar, veuillez entrer la chaine de caract�res � d�coder");
	    	sc.nextLine();
	    	sentence = sc.nextLine();
	    	System.out.println("Entrer la cl� de d�codage");
	    	s_key = sc.nextLine();
	    	key = s_key.charAt(0);
	    	System.out.println("Voici votre message d�crypt� :" + cesar.decode(sentence, alphabet, key));
	        break;
	    case 5:
	    	System.out.println("D�codage C�sar sans cl�, veuillez entrer la chaine de caract�res � d�coder");
	    	sc.nextLine();
	    	sentence = sc.nextLine();
	    	cesar.decrypt(sentence, alphabet);
	        break;
		case 6:
	    	System.out.println("D�codage Permutation");
	    	break;
		case 7:
			System.out.println("D�codage Vegen�re");
			break;
		case 8:
			break;
		default:
			System.out.println("Erreur de saisie");
			break;
		}
	}
}


