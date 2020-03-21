package com.azias.notebook;

import com.azias.commons.addons.Addon;
import com.azias.commons.addons.AddonLoader;
import com.azias.commons.addons.AddonLoaderException;
import com.azias.notebook.addons.events.AddonClassGenericEvent;
import com.azias.notebook.addons.tasks.AddonClassDiscoveryTask;
import com.azias.notebook.addons.tasks.AddonClassMethodCallerTask;
import com.azias.notebook.interactors.InteractorManager;
import com.azias.storage.StorageManager;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Notebook {
	private final static Logger logger = LoggerFactory.getLogger(Notebook.class);
	
	private CommandLine launchArgs;
	
	private AddonLoader addonLoader;
	private StorageManager storageManager;
	// TODO: Make an event based system ???
	private InteractorManager interactorManager;
	
	private long lastUpdateMillis;
	
	public Notebook(CommandLine launchArgs) throws NotebookException {
		logger.debug("Instantiating Notebook...");
		this.launchArgs = launchArgs;
		
		logger.debug("Instantiating the AddonLoader...");
		if(!launchArgs.hasOption("addons")) {
			logger.trace("The addons argument isn't set, throwing exception !");
			throw new NotebookException("Addons argument not set !", NotebookException.ErrorCode.ERROR_NO_ADDONS_IN_PARAMETERS);
		} else {
			addonLoader = new AddonLoader(launchArgs.getOptionValue("addons").split(";"));
		}
		
		storageManager = new StorageManager();
		interactorManager = new InteractorManager();
	}
	
	public void initialize(boolean executeAddonLoader) {
		logger.info("Initializing the Notebook...");
		
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
		
		// TODO: Don't exit the program, just shit out an exception.
		
		logger.debug("Preparing tasks for the AddonLoader...");
		
		// This event is used to share classes and data between Tasks that uses Classes and Reflection.
		AddonClassGenericEvent genericClassEvent = new AddonClassGenericEvent();
		AddonClassDiscoveryTask classDiscoveryTask = new AddonClassDiscoveryTask(genericClassEvent, Addon.class);
		
		// Provide multiple interfaces for the addon classes so they can be differentiated based on that.
		AddonClassMethodCallerTask classCallerTask1 = new AddonClassMethodCallerTask("initialize", genericClassEvent);
		AddonClassMethodCallerTask classCallerTask2 = new AddonClassMethodCallerTask("registerStorageMediums", genericClassEvent);
		AddonClassMethodCallerTask classCallerTask3 = new AddonClassMethodCallerTask("registerInteractors", genericClassEvent);
		
		addonLoader.addTask(this, classDiscoveryTask);
		addonLoader.addTask(this, classCallerTask1);
		addonLoader.addTask(this, classCallerTask2);
		addonLoader.addTask(this, classCallerTask3);
		
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
		
		logger.debug("There are currently {} storage adapter(s) registered !", storageManager.getDataStorages().size());
		
		if(storageManager.initialize()) {
			logger.trace("storageManager seems to have initialized successfully !");
		} else {
			logger.trace("storageManager seems to have failed to initialize !");
		}
		
		logger.debug("There are currently {} interactor(s) registered !", interactorManager.getInteractors().size());
		
		if(interactorManager.initialize()) {
			logger.trace("interactorManager seems to have initialized successfully !");
		} else {
			logger.trace("interactorManager seems to have failed to initialize !");
		}
		
		logger.debug("Notebook successfully initialized !");
		logger.debug("Giving back control to parent class.");
	}
	
	public boolean update() {
		lastUpdateMillis = System.currentTimeMillis();
		
		boolean isRunning = interactorManager.update(this, lastUpdateMillis);
		
		if(isRunning) {
			// Do more shit...
		}
		
		return true;
		//return !isRunning;
	}
	
	public void finish() {
		logger.debug("Notebook's finish() function was called !");
		interactorManager.finish();
	}
	
	
	/* Getters & Setters */
	
	public StorageManager getStorageManager() {
		return storageManager;
	}
	
	public InteractorManager getInteractorManager() {
		return interactorManager;
	}
}
