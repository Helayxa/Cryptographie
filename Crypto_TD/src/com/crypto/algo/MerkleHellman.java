package com.crypto.algo;

import java.util.ArrayList;
import java.util.List;

import com.crypto.utils.Utils;

public class MerkleHellman {
	
	public MerkleHellman(){
		
	}
	
	/**
	 * Génération d'une liste super croissante de longueur 5
	 * @return
	 */
	public List<Long> generateSuperCroissantList(){
		List<Long> sc = new ArrayList<Long>();
		long nextNumber = 0;
		long sum = 1;
		for(int i = 0; i < 5; i++){
			nextNumber = sum + (long)(Math.random() * ((3) + 1));
			sum += nextNumber;
			sc.add(i, nextNumber);
		}
		return sc;
	}
	
	/**
	 * Génération d'une liste non super croissante avec p et m premier entre eux
	 * @param p long
	 * @param m long
	 * @param sc liste super croissante
	 * @return
	 */
	public List<Long> generateNoSuperCroissanteList(long p, long m, List<Long> sc){
		List<Long> nsc = new ArrayList<Long>();
		for(int i = 0; i < sc.size(); i++){
			nsc.add(i, (sc.get(i)*p)%m);
		}
		return nsc;
	}
	
	/**
	 * Permet de savoir si deux nombres sont premiers entre eux
	 * @param a long
	 * @param b long
	 * @return
	 */
	public static long pgcd(long a, long b) {
	    long r = a;
	    while (r!=0){
	    	r = a%b; 
	    	a=b; 
	    	b=r; 
	    }
	    return(Math.abs(a));
	 }
	
	/**
	 * Fonction permettant de retourner une liste de bits correspondant à l'index des caractères de la phrase a crytpé dans l'alphabet. Les caractères sont codés sur 5 bits
	 * @param charSentence contient le message à crypté sous forme de tableau de caractères
	 * @param alphabet contient l'alphabet sous forme de tableau de caractères
	 * @return
	 */
	public List<String> getBitsSentence(char[] charSentence , char[] alphabet){
		List<String> bitList = new ArrayList<String>();
		int index = 0;
		String bitString;
		int bitSize = 0;
		String bits = "";
		int i_random = 0;
		String s_random = "";
		//Codage de chaque caractère sur 5 bits
		for(int i = 0; i < charSentence.length; i++){
			if(charSentence[i] != ' '){
				index = Utils.findIndexByLetter(alphabet, charSentence[i]);
				bitString = Integer.toBinaryString(index);
				bitSize = bitString.length();
				if(bitSize < 5){
					for(int j = bitSize; j < 5 ; j++){
						bitString = "0" + bitString;
					}
				}
				bits += bitString;
			}
			else{
				bits += charSentence[i];
			}
		}
		StringBuffer bufBits = new StringBuffer(bits);
		//Regroupement en bloc de taille correspondant à la taille du text
		while(bufBits.length() != 0){
			if(bufBits.length() < 5){
				bitString = bufBits.substring(0, bufBits.length());
				for(int j = bufBits.length(); j < 5; j++){
					i_random = (int)(Math.random()*2);
					s_random = Integer.toString(i_random);
					bitString = bitString + s_random;
				}
				bitList.add(bitString);
				bufBits = bufBits.replace(0, bufBits.length(), "");
			}
			else{
				bitString = bufBits.substring(0, 5);
				bufBits = bufBits.replace(0, 5, "");
				bitList.add(bitString);
			}
		}
		return bitList;
	}
	
	/**
	 * Fonction permettant d'encoder un message par le protocole MekleHellman
	 * @param sentence message à crypté
	 * @param alphabet contient l'alphabet sous forme de tableau de caractères
	 * @return
	 */
	public MerkleHellmanResult encode(String sentence, char[] alphabet){
		//Suppression des caractères spéciaux
		sentence = sentence.replaceAll("\\s+","");
		char charSentence[] = sentence.toUpperCase().toCharArray();
		MerkleHellmanResult mhResult = new MerkleHellmanResult();
		long sum = 0;
		char charBit[];
		List<Long> publicKey = new ArrayList<Long>();
		List<Long> encodeList = new ArrayList<Long>();
		List<Long> privateKey = new ArrayList<Long>();
		long p = (long)(Math.random()*(10) + 1);
		long m = 0;
		// Récupération d'une liste super croissante servant de clé privée
		privateKey = generateSuperCroissantList();
		//Calcul de l'entier m (modulo) > somme des éléments de la privatekey
		for(int i = 0; i < privateKey.size() ; i++){
			m += privateKey.get(i);
		}
		m += (int)(Math.random()*(100) + 1);
		//Calcul de l'entier p premier avec m
		while(pgcd(p, m) != 1){
			p = (long)(Math.random()*(m) + 1);
		}
		//Calcul de la liste non super croissante
		publicKey = generateNoSuperCroissanteList(p, m, privateKey);
		mhResult.setM(m);
		mhResult.setP(p);
		mhResult.setPrivateKey(privateKey);
		mhResult.setPublicKey(publicKey);
		//Récupération de la liste des bits du message à encoder
		List<String> bitList = getBitsSentence(charSentence, alphabet);
		//Pour chaque bit on va calculer un entier qui représentara le message crypté
		for(int i = 0; i < bitList.size(); i++){
			 charBit = bitList.get(i).toCharArray();
			for(int j = 0; j < publicKey.size(); j++){
				sum += Character.getNumericValue(charBit[j]) * publicKey.get(j);
			}
			encodeList.add(sum);
			sum = 0;
		}
		mhResult.setEncodeList(encodeList);
		return mhResult;
	}
	
	/**
	 * Algorithme d'Euclide étendu afin de calculer l'inverse modulaire
	 * @param p long
	 * @param m long
	 * @return
	 */
	public long euclideEtendu (long p, long m){
		long pp = p;
		long mm = m;
		long t0 = 0;
		long t = 1;
		long q = (long) mm/pp;
		long r = mm - q * pp;
		long tmp = 0;
	 
		 while(r > 0){
			tmp = t0 - q * t;
			if (tmp >= 0){
				tmp = tmp % m;
			}
			else{
			     tmp = m - ((-tmp) % m);
			}
		 t0 = t;
		 t = tmp;
		 mm = pp;
		 pp = r;
		 q = (long) mm/pp;
		 r = mm - q * pp;
	    }
	 
	    if (pp!=1)
	    	return -1; //Pas d'inverse modulaire pour m et p
	    else 
	    	return t;
	}
	
	/**
	 * Permet de retrouver la liste super croissante
	 * @param p long
	 * @param m long
	 * @param publicKey contient la liste non super croissante
	 * @return
	 */
	public List<Long> getPrivateKey (long p, long m, List<Long> publicKey){
		List<Long> privateKey = new ArrayList<Long>();
		long inverse = euclideEtendu(p,m);
		if(inverse != -1){
			for(int i = 0; i < publicKey.size(); i++){
				privateKey.add((publicKey.get(i)*inverse)%m);
			}
		}
		return privateKey;
	}
	
	/**
	 * Déchiffrage du message codé à l'aide de l'inverse modulaire de m
	 * @param p long
	 * @param m long
	 * @param encodeList contient le message crypté sous forme de liste
	 * @return
	 */
	public List<Long> getDecryptKeyList(long  p, long m, List<Long> encodeList){
		List<Long> decodeList = new ArrayList<Long>();
		long inverse = euclideEtendu(p, m);
		if(inverse != -1){
			for(int i = 0; i < encodeList.size(); i++){
				decodeList.add(i, (encodeList.get(i)*inverse)%m);
			}
			System.out.println("Liste décrypté :" +decodeList);
		}
		return decodeList;
	}
	
	/**
	 * Algorithme glouton permettant de récupérer les bits des caratères codés
	 * @param numCrypt element de la liste obtenue par getDecryptKeyList
	 * @param privateList liste super croissante
	 * @return
	 */
	public String knapSack(long numCrypt, List<Long> privateList){
		long sum = numCrypt;
		String binary = "";
		int i = privateList.size() - 1;
		// Tant que l'entier généré par le decryptage n'est pas égale à 0
		// on recherche dans la clé privée les entiers qui peuvent le composer
		// chaque entier prit correspondra à un bit 1 sionon a un bit 0
		while(sum != 0){
			if((sum - privateList.get(i))>= 0){
				sum -= privateList.get(i);
				binary = "1" + binary;
			}
			else{
				binary = "0" + binary;
			}
			i--;
		}
		while(i >= 0){
			binary = "0" + binary;
			i--;
		}
		return binary;
	}
	
	/**
	 * Fonction permettant de déchiffrer un message codé à l'aide de la clé
	 * @param list liste obtenue par getDecryptKeyList
	 * @param privateList liste super croissante
	 * @param alphabet contient l'aphabet sous forme de tableau de caractères.
	 * @return
	 */
	public String decode(List<Long> list, List<Long> privateList, char[] alphabet){
		String binaryAll = "";
		String binary = "";
		int index = 0;
		String decrypt = "";
		//Pour chaque element de la liste on cherche sa correspondance en bit à partir de la clé 
		for(int i = 0; i < list.size(); i++){
			binaryAll = binaryAll + knapSack(list.get(i), privateList);
		}
		StringBuffer bufBinary = new StringBuffer(binaryAll);
		//On redécoupe la liste afin d'obtenir des chaines de 5 bits correspondants à un caractère
		while(bufBinary.length() >= 5){
			binary = bufBinary.substring(0, 5);
			bufBinary = bufBinary.replace(0, 5, "");
			//Traduction du bit en entier pour retrouver la caractère correspondant à cette entier dans l'alphabet
			index = Integer.parseInt(binary, 2);
			decrypt = decrypt + alphabet[index];
		}
		return decrypt;	
	}
}
