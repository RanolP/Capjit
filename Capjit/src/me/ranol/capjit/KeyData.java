package me.ranol.capjit;

public class KeyData {
	private boolean[] asc = new boolean[3];
	private char c;

	public void setAltRequired(boolean b) {
		asc[0] = b;
	}

	public void setShiftRequired(boolean b) {
		asc[1] = b;
	}

	public void setControlRequired(boolean b) {
		asc[2] = b;
	}

	public boolean getAltRequired() {
		return asc[0];
	}

	public boolean getShiftRequired() {
		return asc[1];
	}

	public boolean getControlRequired() {
		return asc[2];
	}

	public void setKeyChar(char c) {
		this.c = c;
	}

	public char getKeyChar() {
		return c;
	}
}
