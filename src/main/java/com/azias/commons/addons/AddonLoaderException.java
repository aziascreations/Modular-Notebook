package com.azias.commons.addons;

public class AddonLoaderException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private int exceptionType;
	
	public AddonLoaderException(String message) {
		super(message);
		exceptionType = -1;
	}
	
	public AddonLoaderException(String message, int exceptionType) {
		super(message);
		this.exceptionType = exceptionType;
	}
	
	public int getExceptionType() {
		return exceptionType;
	}
}