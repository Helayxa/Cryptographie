package com.crypto.main;

import java.util.Scanner;

import com.crypto.algo.Cesar;
import com.crypto.algo.Permutation;
import com.crypto.algo.Vigenere;

public class Main {

	public static final int STOP_OPTION = 4;
	
	public static void main(String[] args) {
		Cesar cesar = new Cesar();
		Permutation permutation = new Permutation();
		Vigenere vigenere = new Vigenere();
		char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		char[] permutationAlphabet = permutation.generateRandomAlphabetKey();
		//char[] keyAlphabet = { 'R', 'G', 'E', 'C', 'V', 'J', 'U', 'A', 'S', 'P', 'O', 'I', 'M', 'B', 'W', 'D', 'T', 'Z', 'X', 'F', 'H', 'Y', 'K', 'L', 'N', 'Q' };
		String sentence;
		String s_key;
		char key;
		int opt = 0;
		Scanner sc = new Scanner(System.in); 
		System.out.println("========================================");
	    System.out.println("|         CHOISIR UN ALGORITHME        |");
	    System.out.println("========================================");
	    System.out.println("| Options:                             |");
	    System.out.println("|        1. Algorithme de C�sar        |");
	    System.out.println("|        2. Algorithme de permutation  |");
	    System.out.println("|        3. Algorithme de Vigen�re     |");
	    System.out.println("|        4. Quitter                    |");
	    System.out.println("========================================");
		while(opt != STOP_OPTION) {
		    System.out.println("\nVeullez entrer votre choix :");
		    opt = sc.nextInt();
		    
		    switch(opt) {
			    case 1:
			    	int cesarOption = 0;
			    	System.out.println("========================================");
				    System.out.println("|         ALGORITHME DE CESAR          |");
				    System.out.println("========================================");
				    System.out.println("| Options:                             |");
				    System.out.println("|        1. Chiffrement        	       |");
				    System.out.println("|        2. D�chiffrement              |");
				    System.out.println("|        3. D�cryptage                 |");
				    System.out.println("|        4. Quitter                    |");
				    System.out.println("========================================");
				    System.out.println("\nVeullez entrer votre choix :");
				    cesarOption = sc.nextInt();
				    switch(cesarOption) {
				    	case 1 :
				    		System.out.println("Veuillez entrer la chaine de caract�res � chiffrer : ");
					    	sc.nextLine();
					    	sentence = sc.nextLine();
					    	System.out.println("Entrer la cl� de chiffrement");
					    	s_key = sc.nextLine();
					    	key = s_key.charAt(0);
					    	System.out.println("Voici votre message chiffr� : ");
					    	System.out.println(cesar.encrypt(sentence, alphabet, key));
				    		break;
				    	case 2 : 
				    		System.out.println("Veuillez entrer la chaine de caract�res � d�chiffrer");
					    	sc.nextLine();
					    	sentence = sc.nextLine();
					    	System.out.println("Entrer la cl� de d�chiffrement");
					    	s_key = sc.nextLine();
					    	key = s_key.charAt(0);
					    	System.out.println("Voici votre message d�chiffr� : ");
					    	System.out.println(cesar.decode(sentence, alphabet, key));
				    		break;
				    	case 3: 
				    		System.out.println("Veuillez entrer la chaine de caract�res � d�crypter sans cl� : ");
					    	sc.nextLine();
					    	sentence = sc.nextLine();
					    	cesar.decrypt(sentence, alphabet);
				    		break;
				    	case 4: 
				    		break;
				    	default:
							System.out.println("Erreur de saisie");
							break;
				    }
			        break;
			    case 2:
			    	int permutationOption = 0;
			    	System.out.println("===================================================================");
				    System.out.println("|                    ALGORITHME DE PERMUTATION                    |");
				    System.out.println("===================================================================");
				    System.out.println("| Options:                                                        |");
				    System.out.println("|        1. Chiffrement                                           |");
				    System.out.println("|        2. D�chiffrement                                         |");
				    System.out.println("|        3. Fr�quence d'apparition des lettres                    |");
				    System.out.println("|        4. D�crypter avec la fr�quence d'apparition des lettres  |");
				    System.out.println("|        5. Quitter                                               |");
				    System.out.println("===================================================================");
				    System.out.println("\nVeullez entrer votre choix :");
				    permutationOption = sc.nextInt();
				    switch(permutationOption) {
					    case 1 :
					    	System.out.println("Veuillez entrer la chaine de caract�res � chiffrer : ");
					    	sc.nextLine();
					    	sentence = sc.nextLine();
					    	System.out.println("Voici votre message chiffr� : " + permutation.encrypt(sentence, alphabet, permutationAlphabet));
					    	break;
					    case 2 :
					    	System.out.println("Veuillez entrer la chaine de caract�res � d�chiffrer : ");
					    	sc.nextLine();
					    	sentence = sc.nextLine();
					    	System.out.println("Voici votre message d�chiffr� : ");
					    	System.out.println(permutation.decode(sentence, alphabet, permutationAlphabet));
					    	break;
					    case 3 :
					    	System.out.println("Veuillez entrer la chaine de caract�res pour laquelle vous voulez conna�tre la fr�quence d'apparition des lettres : ");
							sc.nextLine();
					    	sentence = sc.nextLine();
							System.out.println("Fr�quence d'apparition des lettres dans votre chapine de caract�re");
							System.out.println(permutation.getLettersAppearanceFrequency(alphabet, sentence));
					    	break;
					    case 4: 
					    	System.out.println("Veuillez entrer la chaine de caract�res � d�crypter : ");
							sc.nextLine();
					    	sentence = sc.nextLine();
							System.out.println("Voici votre message d�crypt� : ");
							System.out.println(permutation.decryptWithFrequencies(alphabet, sentence));
					    	break;
					    case 5 :
					    	break;
					    default:
							System.out.println("Erreur de saisie");
							break;
				    }
			    	break;
			    case 3:
			    	int vigenereOption = 0;
			    	System.out.println("===============================================");
				    System.out.println("|             ALGORITHME DE VIGENERE          |");
				    System.out.println("===============================================");
				    System.out.println("| Options:                                    |");
				    System.out.println("|        1. Chiffrement                       |");
				    System.out.println("|        2. D�chiffrement                     |");
				    System.out.println("|        3. D�cryptage                        |");
				    System.out.println("|        9. Quitter                           |");
				    System.out.println("===============================================");
				    System.out.println("\nVeullez entrer votre choix :");
				    vigenereOption = sc.nextInt();
				    switch(vigenereOption) {
				    case 1 :
				    	System.out.println("Veuillez entrer la chaine de caract�res � chiffrer : ");
				    	sc.nextLine();
				    	sentence = sc.nextLine();
				    	System.out.println("Entrer la cl� de chiffrement");
				    	s_key = sc.nextLine();
				    	System.out.println("Voici votre message chiffr� : " + vigenere.encode(alphabet, sentence, s_key));
//				    	System.out.println(vigenere.encode(alphabet, "CHIFFREDEVIGENERE", "CRYPTOGRAPHIE"));
				    	break;
				    case 2 :
				    	System.out.println("Veuillez entrer la chaine de caract�res � d�chiffrer : ");
				    	sc.nextLine();
				    	sentence = sc.nextLine();
				    	System.out.println("Entrer la cl� de d�chiffrement");
				    	s_key = sc.nextLine();
				    	System.out.println("Voici votre message d�chiffr� : " + vigenere.decode(alphabet, sentence, s_key));
//				    	System.out.println(vigenere.decode(alphabet, "EYGUYFKUEKPOIPVPT", "CRYPTOGRAPHIE"));
				    	break;
				    case 3 :
				    	vigenere.decrypt("QODBSWWOFOLOFMWMSZFKHSEESFWCSKJOFSTSSBEESVSCPKGOGCCXHKQAISGOG", alphabet);
				    	break;
				    case 9 :
				    	break;
				    default:
						System.out.println("Erreur de saisie");
						break;
					}
			    	break;
			    case 4:
			        break;
			    case 5: 
			    	vigenere.decrypt("QODBSWWOFOLOFMWMSZFKHSEESFWCSKJOFSTSSBEESVSCPKGOGCCXHKQAISGOG", alphabet);
			    	break;
				default:
					System.out.println("Erreur de saisie");
					break;
			}
		}
		sc.close();
	}
}


