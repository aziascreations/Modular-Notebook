package com.azias.notebook;

public class NotebookException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private ErrorCode errorCode;
	
	public enum ErrorCode {
		ERROR_GENERIC,
		ERROR_SUPER,
		ERROR_NO_ADDONS_IN_PARAMETERS;
	}
	
	public NotebookException() {
		this("", ErrorCode.ERROR_GENERIC);
	}
	
	public NotebookException(ErrorCode errorCode) {
		this("", ErrorCode.ERROR_GENERIC);
	}
	
	public NotebookException(String message) {
		this(message, ErrorCode.ERROR_GENERIC);
	}
	
	public NotebookException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
