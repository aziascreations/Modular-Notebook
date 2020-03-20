package com.azias.storage;

import java.util.ArrayList;
import java.util.UUID;

public interface IDataStorage {
	// Every method that will be used to interact with a storage medium should be put here.
	
	
	/**
	 * Called before the data storage medium can be used.
	 * It should prepare it to receive or supply data right after that
	 * @return true if it is ready
	 */
	boolean initialize();
	
	String getId();
	
	boolean hasFile(UUID fileUUID);
	
	// Could potentially be moved into other interfaces.
	boolean canSupplyFile();
	boolean canStoreFile();
	
	boolean supportsSynchronisation();
	
	ArrayList<UUID> getEntries();
	
	@Deprecated
	boolean isInitialized();
}
