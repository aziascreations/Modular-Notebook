package com.azias.notebook.entries;

import com.azias.notebook.NotebookException;

public class NotebookEntryException extends NotebookException {
	private static final long serialVersionUID = 1L;
	
	public NotebookEntryException() {
		super("");
	}
	
	public NotebookEntryException(String message) {
		super(message);
	}
}
