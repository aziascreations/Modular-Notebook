package com.azias.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.UUID;

public class StorageManager {
	private final static Logger logger = LoggerFactory.getLogger(StorageManager.class);
	
	private ArrayList<IDataStorage> dataStorages;
	
	public StorageManager() {
		dataStorages = new ArrayList<>();
	}
	
	public boolean initialize() {
		for(IDataStorage dataStorage : dataStorages) {
			if(!dataStorage.initialize()) {
				logger.error("Failed to initialize IDataStorage {} !", dataStorage.getClass().getSimpleName());
			}
		}
		
		return true;
	}
	
	public boolean hasfile(UUID fileUUID) {
		if(dataStorages != null && fileUUID != null) {
			for(IDataStorage dataStorage : dataStorages) {
				if(dataStorage.hasFile(fileUUID)) {
					return true;
				}
			}
		} else {
			logger.warn("The IDataStorage ArrayList in {} or fileUUID is null !", this.getClass().getSimpleName());
		}
		
		return false;
	}
	
	public boolean addStorage(IDataStorage storage) {
		if(dataStorages != null) {
			dataStorages.add(storage);
			logger.debug("Registered storage medium {} with \"{}\" as its Id !", storage.getClass().getSimpleName(), storage.getId());
			return true;
		} else {
			logger.error("Failed to add {} as a storage medium since \"dataStorages\" is equal to null !",
					this.getClass().getSimpleName());
			return false;
		}
	}
}
