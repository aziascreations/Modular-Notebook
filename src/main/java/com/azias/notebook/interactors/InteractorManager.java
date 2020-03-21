package com.azias.notebook.interactors;

import com.azias.notebook.Notebook;
import com.azias.storage.IDataStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class InteractorManager {
	private final static Logger logger = LoggerFactory.getLogger(InteractorManager.class);
	
	private ArrayList<INotebookInteractor> interactors;
	
	public InteractorManager() {
		logger.trace("Instantiating {}...", this.getClass().getSimpleName());
		interactors = new ArrayList<>();
	}
	
	public boolean initialize() {
		for(INotebookInteractor interactor : interactors) {
			if(!interactor.initialize()) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean update(Notebook app, long lastUpdateMillis) {
		for(INotebookInteractor interactor : interactors) {
			if(!interactor.update(app, lastUpdateMillis)) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean finish() {
		logger.debug("InteractorManager's finish() function was called !");
		
		boolean hasEncounteredErrors = false;
		
		for(INotebookInteractor interactor : interactors) {
			if(!hasEncounteredErrors && !interactor.close()) {
				hasEncounteredErrors = true;
			}
		}
		
		return !hasEncounteredErrors;
	}
	
	public boolean addInteractor(INotebookInteractor interactor) {
		if(interactor != null) {
			interactors.add(interactor);
			return true;
		}
		
		return false;
	}
	
	
	/* Getters & Setters */
	
	public ArrayList<INotebookInteractor> getInteractors() {
		return interactors;
	}
}
