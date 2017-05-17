package com.crypto.algo;

import java.util.ArrayList;
import java.util.List;

public class MerkleHellmanResult {

	List<Long> privateKey = new ArrayList<Long>();
	List<Long> publicKey = new ArrayList<Long>();
	List<Long> encodeList = new ArrayList<Long>();
	long p = 0;
	long m = 0;
	
	public MerkleHellmanResult(){
		
	}
	
	public MerkleHellmanResult(List<Long> privateKey, List<Long> publicKey, List<Long> encodeList, long p, long m) {
		this.privateKey = privateKey;
		this.publicKey = publicKey;
		this.encodeList = encodeList;
		this.p = p;
		this.m = m;
	}

	public List<Long> getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(List<Long> privateKey) {
		this.privateKey = privateKey;
	}

	public List<Long> getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(List<Long> publicKey) {
		this.publicKey = publicKey;
	}

	public List<Long> getEncodeList() {
		return encodeList;
	}

	public void setEncodeList(List<Long> encodeList) {
		this.encodeList = encodeList;
	}

	public long getP() {
		return p;
	}

	public void setP(long p) {
		this.p = p;
	}

	public long getM() {
		return m;
	}

	public void setM(long m) {
		this.m = m;
	}

	@Override
	public String toString() {
		String s_privateKey = privateKey.toString().replace("[", "").replace("]", "").replace(",", "");
		String s_publicKey = publicKey.toString().replace("[", "").replace("]", "").replace(",", "");
		String s_encodeList = encodeList.toString().replace("[", "").replace("]", "").replace(",", "");
		return (s_privateKey + "\n" + s_encodeList + "\n"  + p + "\n"  + m + "\n" + s_publicKey);
	}	
}
