package com.azias.notebook.entries;

import java.util.ArrayList;
import java.util.UUID;

public class NotebookEntry {
	private UUID entryUUID;
	
	private ArrayList<NotebookAsset> entryAssets;
	
	public NotebookEntry() {
		entryUUID = UUID.randomUUID();
		entryAssets = new ArrayList<>();
	}
	
	public NotebookEntry(UUID entryUUID) {
		this.entryUUID = entryUUID;
		entryAssets = new ArrayList<>();
	}
	
	/* Getters & Setters */
	
	public UUID getEntryUUID() {
		return entryUUID;
	}
	
	public void setEntryUUID(UUID entryUUID) {
		this.entryUUID = entryUUID;
	}
}
