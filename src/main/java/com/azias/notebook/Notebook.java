package com.azias.notebook;

import com.azias.commons.addons.Addon;
import com.azias.commons.addons.AddonLoader;
import com.azias.commons.addons.AddonLoaderException;
import com.azias.notebook.addons.events.AddonClassGenericEvent;
import com.azias.notebook.addons.tasks.AddonClassDiscoveryTask;
import com.azias.storage.IDataStorage;
import com.azias.notebook.modules.localstorage.storages.LocalStorageAdapter;
import com.azias.storage.StorageManager;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class Notebook {
	private final static Logger logger = LoggerFactory.getLogger(Notebook.class);
	
	private String stuff;
	private long lastUpdateMillis;
	
	private CommandLine launchArgs;
	private AddonLoader addonLoader;
	
	private StorageManager storageManager;
	
	public Notebook(CommandLine launchArgs) {
		this.launchArgs = launchArgs;
		
		logger.debug("Instantiating the AddonLoader...");
		
		if(!launchArgs.hasOption("addons")) {
			logger.error("The addons argument isn't set.");
			logger.info("Please use \"--help\" to see the help.");
			System.exit(-1);
			
			// TODO: Remove this !!!!!
		} else {
			addonLoader = new AddonLoader(launchArgs.getOptionValue("addons").split(";"));
		}
	}
	
	public void initialize(boolean executeAddonLoader) {
		logger.info("Initializing the Notebook...");
		// TODO: IDK ???
		
		logger.debug("Initializing the AddonLoader in Notebook...");
		try {
			addonLoader.initialize(true);
		} catch(AddonLoaderException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		} catch(IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
		
		// TODO: Do shit.
		
		logger.debug("Preparing tasks for the AddonLoader...");
		
		// This event is used to share classes and data between Tasks that uses Classes and Reflection.
		AddonClassGenericEvent genericClassEvent = new AddonClassGenericEvent();
		AddonClassDiscoveryTask classDiscoveryTask = new AddonClassDiscoveryTask(genericClassEvent, Addon.class);
		
		addonLoader.addTask(this, classDiscoveryTask);
		
		if(executeAddonLoader) {
			try {
				addonLoader.finishLoading(this);
			} catch(AddonLoaderException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				System.exit(-1);
			}
		}
		
		//logger.info("--> {}", genericClassEvent.getAddonClassesList().get(0).getAnnotation(Addon.class).id());
		
		addonLoader.clearTasks(this);
		
		logger.debug("Setting up testing data storage medium.");
		
		storageManager = new StorageManager();
		storageManager.addStorage(new LocalStorageAdapter("./data/test01/"));
		
		if(storageManager.initialize()) {
			logger.debug("storageManager seems to have initialized successfully !");
		} else {
			logger.debug("storageManager seems to have failed to initialize !");
		}
		
		/*if(storageManager.hasfile(UUID.fromString("f88ed4ed-b470-4c7b-b5d1-29549e2fb82d"))) {
			logger.debug("UUID found !");
		} else {
			logger.debug("UUID not found !");
		}
		
		if(storageManager.hasfile(UUID.fromString("f88ed4ed-b470-4c7b-b5d1-29549e2fb83d"))) {
			logger.debug("UUID found !");
		} else {
			logger.debug("UUID not found !");
		}/**/
		
		logger.debug("Notebook successfully initialized !");
		logger.debug("Giving back control to parent class.");
	}
	
	public boolean update() {
		lastUpdateMillis = System.currentTimeMillis();
		
		return true;
	}
	
	public void finish() {
		logger.debug("Notebook's finish() function was called !");
	}
	
	
	
}
