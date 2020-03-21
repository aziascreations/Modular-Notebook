package com.azias.notebook.modules.localstorage;

import com.azias.commons.addons.Addon;
import com.azias.commons.addons.AddonLoader;
import com.azias.notebook.Notebook;
import com.azias.notebook.addons.events.AddonClassGenericEvent;

import com.azias.notebook.modules.localstorage.storages.LocalStorageAdapter;
import jdk.vm.ci.meta.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Addon(id="local-storage")
public class LocalStorageModule {
	private final static Logger logger = LoggerFactory.getLogger(LocalStorageModule.class);
	
	public static void registerStorageMediums(Notebook app, AddonLoader al, AddonClassGenericEvent classEvent) {
		logger.info("Registering storage medium in {}...", LocalStorageModule.class.getSimpleName());
		
		app.getStorageManager().addStorage(new LocalStorageAdapter("./data/storage/local-storage-test/"));
	}
	
}
