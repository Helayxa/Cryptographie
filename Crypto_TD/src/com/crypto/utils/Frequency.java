package com.crypto.utils;

public class Frequency {

	char letter;
	double appearenceFrequency;
	
	public Frequency(char letter, double frequency) {
		this.letter = letter;
		this.appearenceFrequency = frequency;
	}

	public char getLetter() {
		return letter;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	public double getAppearenceFrequency() {
		return appearenceFrequency;
	}

	public void setAppearenceFrequency(double appearenceFrequency) {
		this.appearenceFrequency = appearenceFrequency;
	}
	
	public void increment() {
		this.appearenceFrequency++;
	}
	
	@Override
	public String toString() {
		return letter + ": " + appearenceFrequency;
	}
	
}
