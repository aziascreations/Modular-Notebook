package com.azias.notebook.entries;

import com.azias.notebook.Notebook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.UUID;

public class NotebookEntryManager {
	private final static Logger logger = LoggerFactory.getLogger(NotebookEntryManager.class);

	private HashMap<UUID, NotebookEntry> loadedEntries;
	
	private Notebook parentNotebook;
	
	public NotebookEntryManager(Notebook parentNotebook) throws NotebookEntryException {
		if(parentNotebook == null) {
			throw new NotebookEntryException("parentNotebook is null !");
		}
		
		this.parentNotebook = parentNotebook;
		loadedEntries = new HashMap<>();
	}
	
	// TODO: These 2 shouldn't silently fail !
	public boolean isEntryLoaded(UUID entryUUID) {
		if(entryUUID != null) {
			return loadedEntries.containsKey(entryUUID);
		} else {
			logger.warn("A null UUID was given to isEntryLoaded() in {} !", this.getClass().getSimpleName());
			return false;
		}
	}
	
	public boolean doesEntryExists(UUID entryUUID) {
		if(entryUUID != null) {
			return parentNotebook.getStorageManager().hasfile(entryUUID);
		} else {
			logger.warn("A null UUID was given to doesEntryExists() in {} !", this.getClass().getSimpleName());
			return false;
		}
	}
	
}
