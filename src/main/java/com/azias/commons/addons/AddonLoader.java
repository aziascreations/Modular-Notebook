package com.azias.commons.addons;

import com.azias.commons.addons.tasks.ClearableTask;
import com.azias.commons.addons.tasks.LoopingTask;
import com.azias.commons.addons.tasks.Task;
import com.azias.commons.utils.FileUtils;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class AddonLoader {
	private final static Logger logger = LoggerFactory.getLogger(AddonLoader.class);
	private final static String defaultAddonDirectory = "./data/addons/";
	
	
	private String[] addonsIds;
	private String[] addonsFolders;
	
	// TODO: Rename those.
	private int currentTaskIndex = 0, currentTaskStep = 0;
	
	private Gson gson;
	
	private HashMap<String, AddonInfo> addonsInfos;
	
	private List<Task> loadingTasks;
	
	
	public AddonLoader(String addonsIds) {
		this(addonsIds.split(";"), new String[]{defaultAddonDirectory});
	}
	
	public AddonLoader(String[] addonsIds) {
		this(addonsIds, new String[]{defaultAddonDirectory});
	}
	
	public AddonLoader(String[] addonsIds, String addonsFolder) {
		this(addonsIds, new String[]{addonsFolder});
	}
	
	public AddonLoader(String[] addonsIds, String[] addonsFolders) {
		logger.trace("Calling AddonLoader constructor with {} and {} as parameters", Arrays.toString(addonsIds),  Arrays.toString(addonsFolders));
		
		this.addonsIds = addonsIds;
		this.addonsFolders = addonsFolders;
	}
	
	
	/**
	 * Initialize the AddonLoader and load the addons info files and checks if
	 * everything is working correctly and ready to be loaded.
	 *
	 * @throws AddonLoaderException
	 *             Thrown when the addons list is empty, might get removed later
	 * @throws IOException
	 *             Thrown if an error occurs when reading a "addon.json" file.
	 * @throws Exception
	 *             Thrown if an error occurs when a
	 *             {@link com.google.gson.JsonSyntaxException}
	 *             is thrown when parsing a "addon.json" file.
	 */
	public void initialize(boolean requireAddons) throws AddonLoaderException, IOException {
		logger.debug("Initializing the AddonLoader with {} addon(s)", addonsIds.length);
		
		gson = new Gson();
		addonsInfos = new HashMap<>();
		loadingTasks = new ArrayList<>();
		currentTaskIndex = 0;
		currentTaskStep = 0;
		
		if(addonsIds.length <= 0 && requireAddons)
			throw new AddonLoaderException("Addons are required to continue !");
		
		for(String addonId : addonsIds) {
			logger.debug("Attempting to find {}...", addonId);
			
			String addonLocation = null;
			String addonInfoFilePath = null;
			File addonInfoFile = null;
			
			// Looping over all directories to find the "addon.json" file.
			for(String addonFolder : addonsFolders) {
				/*//addonInfoFilePath = this.getClass().getResource(;
				//addonInfoFilePath = Paths.get(addonFolder, addonId, "addon.json").toString();
				addonInfoFilePath = Paths.get("/addon.json").toString();
				logger.debug("-> {}", addonInfoFilePath);
				
				// 03:06:15 DEBUG - going to scan these urls: [file:/D:/Developement/Java/idea-ws-01/notebook/build/classes/java/main/, file:/D:/Developement/Java/idea-ws-01/notebook/build/resources/main/]

				logger.debug("-> {}", getClass().getResource(addonInfoFilePath));
				logger.debug("-> {}", getClass().getResource("classpath:/addon.json"));
				
				/*public File readFileFromClasspath()
				{
					URL fileUrl = getClass().getResource(CONFIG_FILE);
					return new File(fileUrl.getFile());
				}/**/
				
				//logger.debug("-> {}", this.getClass().getResource(addonInfoFilePath).getFile());
				//logger.debug("-> {}", this.getClass().getResource(addonInfoFilePath).getPath());
				
				addonInfoFile = new File(Paths.get(addonFolder, addonId, "addon.json").toString());
				logger.debug("Looking for {} at {}...", addonId, addonInfoFile.getPath());
				
				if(addonInfoFile.exists()) {
					addonLocation = addonFolder;
					break;
				} else {
					addonInfoFile = null;
				}
			}
			
			if(addonInfoFile == null) {
				throw new AddonLoaderException("Unable to find addon.json for " + addonId + " in any of the folders.");
			}
			
			try {
				AddonInfo addonInfo = gson.fromJson(FileUtils.fileToString(addonInfoFile.getPath()), AddonInfo.class);
				addonInfo.setDataPath(addonLocation);
				addonsInfos.put(addonInfo.getId(), addonInfo);
				
				// TODO: Make it parse its version fields.
			} catch(Exception e) {
				throw e;
			}
		}
		
		
		// TODO: Don't !
		//logger.debug("Indexing addons classes...");
		
		// This is not required for the moment and should be handled by the launcher.
		logger.debug("Parsing versions... (Not Implemented yet)");
		// Should be done in the AddonInfo Object !
		
		logger.debug("Checking dependencies... (Not Implemented yet)");
		
		
		logger.info("AddonLoader successfully initialized !");
	}
	
	public boolean update(Object app) throws AddonLoaderException {
		if(loadingTasks == null) {
			logger.error("The AddonLoader wasn't initialized when update() was called !");
			logger.error("Returning true to avoid a NullPointerException.");
			return true;
		}
		
		if(loadingTasks.isEmpty()) {
			logger.debug("The AddonLoader tasks list is empty, skipping update().");
			return true;
		}
		
		Task currentTask = loadingTasks.get(currentTaskIndex);
		
		if(currentTask instanceof LoopingTask) {
			if(currentTaskStep != -1) {
				// TODO: Change to trace once the AddonLoader is correctly implemented.
				logger.debug("Executing LoopingTask... ({})", currentTask.getClass().getSimpleName());
				if(((LoopingTask) currentTask).update(app, this, currentTaskStep)) {
					currentTaskStep = -1;
				} else {
					currentTaskStep++;
				}
			} else {
				logger.debug("Finalizing LoopingTask... ({})", currentTask.getClass().getSimpleName());
				currentTask.finalize(app, this);
				currentTaskStep = 0;
				currentTaskIndex++;
			}
		} else if(currentTask instanceof Task) {
			logger.trace("Executing Task... ({})", currentTask.getClass().getSimpleName());
			currentTask.execute(app, this);
			
			logger.debug("Finalizing Task execution...");
			currentTask.finalize(app, this);
			
			this.currentTaskStep = 0;
			this.currentTaskIndex++;
		} else {
			logger.warn("The current AddonLoader Task isn't an instance of a supported Interface/Object. -> {}", currentTask.toString());
			logger.warn("It will be skipped !");
			this.currentTaskStep = 0;
			this.currentTaskIndex++;
		}
		
		return currentTaskIndex >= loadingTasks.size();
	}
	
	/**
	 * Blocks the Thread until all events and callbacks are executed/loaded.
	 *
	 * @return true if no error occurred. - NOT IMPLEMENTED YET.
	 */
	public boolean finishLoading(Object app) throws AddonLoaderException {
		while(!update(app)) {}
		return false;
	}
	
	public boolean addTask(Object app, Task task) {
		return addTask(app, task, true);
	}
	
	public boolean addTask(Object app, Task task, boolean executeInit) {
		logger.debug("Attempting to register {} in the AddonLoader...", task.getClass().getSimpleName());
		if(loadingTasks == null) {
			logger.error("The AddonLoader isn't initialized !");
			return false;
		}
		
		try {
			if(executeInit)
				task.init(app, this);
		} catch(Exception e) {
			logger.error("An error occured while excuting the init function of a Task. ({})", task.getClass().getSimpleName());
			return false;
		}
		
		loadingTasks.add(task);
		
		return true;
	}
	
	/* Could be used to let the garbage collector remove them ? */
	public boolean clearTasks(Object app) {
		if(loadingTasks != null) {
			logger.debug("Clearing {} Task(s) from {}...", loadingTasks.size(), this.getClass().getSimpleName());
			
			for(Task task : loadingTasks) {
				if(task instanceof ClearableTask) {
					logger.debug("Clearing {}...", task.getClass().getSimpleName());
					((ClearableTask)task).clear(app, this);
				}
			}
			
			loadingTasks.clear();
			return true;
		} else {
			logger.error("Failed to clear tasks from {}, the Task list is equal to null !", this.getClass().getSimpleName());
			return false;
		}
	}
	
	public String[] getAddonIds() {
		return addonsIds;
	}
}
