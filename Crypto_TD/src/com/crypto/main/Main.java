package com.crypto.main;

import com.crypto.algo.Cesar;

public class Main {

	public static void main(String[] args) {
		Cesar cesar = new Cesar();
		char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		String messageToEncrypt = "Chiffrez moi";
		System.out.println("Message � chiffrer : " + messageToEncrypt);
		System.out.println("Message chiffr� : " + cesar.encrypt(messageToEncrypt, alphabet, 'g'));
		System.out.println();
		String messageToDecode = "NCJA EZTD PLDJ";
		System.out.println("Message � d�chiffrer : " + messageToDecode);
		System.out.println("Message d�chiffr� : " + cesar.decode(messageToDecode, alphabet, 'l'));
		cesar.decrypt("MH SHQVH TXH FHVW DFTXLV", alphabet);
	}
	
	
}


