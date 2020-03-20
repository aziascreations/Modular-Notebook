package com.azias.notebook.modules.localstorage.storages;

import com.azias.storage.IDataStorage;
import com.github.zafarkhaja.semver.Version;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class LocalStorageAdapter implements IDataStorage {
	private final static Logger logger = LoggerFactory.getLogger(LocalStorageAdapter.class);
	private final static Version storageSchemeVersion = Version.forIntegers(1, 0, 0);
	
	private final static String storageMetaFilename = "./storage-meta.json";
	private final static String storageIndexFilename = "./storage-index.json";
	
	private UUID storageUUID;
	
	private String rootPath;
	private File rootDirectoryFile;
	
	private ArrayList<String> rawFileIndex;
	private ArrayList<UUID> fileIndex;
	
	public LocalStorageAdapter(String rootPath) {
		this.rootPath = rootPath;
		storageUUID = UUID.randomUUID();
	}
	
	@Override
	public boolean initialize() {
		rootDirectoryFile = new File(rootPath);
		
		// TODO: What if the folder is empty ?
		if(!rootDirectoryFile.exists()) {
			if(rootDirectoryFile.mkdirs()) {
				logger.debug("Successfully created the directory tree \"{}\" for storage for {}", rootPath, this.getClass().getSimpleName());
				try {
					if(!createDefaultFiles(true)) {
						logger.error("Failed to create the required files in \"{}\" for storage {}", rootPath, this.getClass().getSimpleName());
						logger.error("This error is likely caused by the fact that the required files already exists in a newly created directory, somehow...");
						return false;
					}
				} catch(IOException e) {
					e.printStackTrace();
					return false;
				}
			} else {
				logger.error("Failed to create the directory tree \"{}\" for storage for {}", rootPath, this.getClass().getSimpleName());
				return false;
			}
		}
		
		try {
			loadIndex(storageIndexFilename, false);
		} catch(IOException e) {
			logger.error("An error occurred while attempting to load the index file !");
			e.printStackTrace();
			return false;
		}
		
		logger.info("IDataStorage {} now has an index composed of {} entrie(s) !", this.getClass().getSimpleName(), fileIndex.size());
		
		return true;
	}
	
	private boolean createDefaultFiles(boolean failIfExist) throws IOException {
		File metaFile = Paths.get(rootPath, storageMetaFilename).toFile();
		logger.debug("Creating {}...", metaFile.getPath());
		
		if(!metaFile.exists()) {
			HashMap<String, Object> metaFields = new HashMap<>();
			metaFields.put("schemeVersion", "1.0.0");
			
			// Can't be changed right now.
			//metaFields.put("indexFile", "./storage-index.json");
			
			Writer writer = new FileWriter(metaFile);
			
			new Gson().toJson(metaFields, writer);
			
			writer.close();
		} else {
			if(failIfExist) {
				logger.warn("The file already exists !");
				return false;
			} else {
				// Load shit off of it if needed.
			}
		}
		
		File indexFile = Paths.get(rootPath, storageIndexFilename).toFile();
		logger.debug("Creating {}...", indexFile.getPath());
		
		if(!indexFile.exists()) {
			Writer writer = new FileWriter(indexFile);
			
			new Gson().toJson(new ArrayList<>(), writer);
			
			writer.close();
		} else {
			if(failIfExist) {
				logger.warn("The file already exists !");
				return false;
			}
		}
		
		return true;
	}
	
	// The path should be removed from the parameters and grabbed from an instance variable that is set accordingly.
	private boolean loadIndex(String indexFilePath, boolean preserveExistingIndex) throws IOException {
		File indexFile = Paths.get(rootPath, indexFilePath).toFile();
		
		if(!indexFile.exists()) {
			logger.error("Unable to find \"{}\" !", indexFile.getPath());
			throw new FileNotFoundException("Unable to find \""+indexFile.getPath()+"\" !");
		}
		
		if(rawFileIndex == null) {
			rawFileIndex = new ArrayList<>();
			fileIndex = new ArrayList<>();
		} else if(!preserveExistingIndex) {
			rawFileIndex.clear();
			fileIndex.clear();
		}
		
		logger.debug("Loading index from \"{}\"...", indexFile.getPath());
		
		if(preserveExistingIndex) {
			return false;
		} else {
			//gson.fromJson(br, new TypeToken<List<JsonLog>>(){}.getType());
			BufferedReader indexFileReader = new BufferedReader(new FileReader(indexFile));
			rawFileIndex = new Gson().fromJson(indexFileReader, new TypeToken<ArrayList<String>>(){}.getType());
			indexFileReader.close();
			
			for(String uuidString : rawFileIndex) {
				fileIndex.add(UUID.fromString(uuidString));
			}
		}
		
		return true;
	}
	
	@Override
	public String getId() {
		return "local-"+storageUUID.toString();
	}
	
	@Override
	public boolean hasFile(UUID fileUUID) {
		if(fileIndex != null) {
			return fileIndex.contains(fileUUID);
		}
		
		/*if(rawFileIndex != null) {
			String desiredUUID = fileUUID.toString();
			
			for(String indexedUUID : rawFileIndex) {
				if(indexedUUID.equalsIgnoreCase(desiredUUID)) {
					return true;
				}
			}
		}/**/
		
		return false;
	}
	
	@Override
	public boolean canSupplyFile() {
		return true;
	}
	
	@Override
	public boolean canStoreFile() {
		return false;
	}
	
	@Override
	public boolean supportsSynchronisation() {
		return false;
	}
	
	@Override
	public ArrayList<UUID> getEntries() {
		return null;
	}
	
	@Override
	public boolean isInitialized() {
		return false;
	}
}
