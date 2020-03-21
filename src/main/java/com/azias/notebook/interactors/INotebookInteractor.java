package com.azias.notebook.interactors;

import com.azias.notebook.Notebook;

public interface INotebookInteractor {
	boolean initialize();
	
	boolean update(Notebook app, long delta);
	
	boolean close();
	
	boolean hasToInteract();
}
