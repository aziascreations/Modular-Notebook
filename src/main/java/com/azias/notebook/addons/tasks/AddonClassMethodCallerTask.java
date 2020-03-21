package com.azias.notebook.addons.tasks;

import com.azias.commons.addons.Addon;
import com.azias.commons.addons.AddonLoader;
import com.azias.commons.addons.tasks.LoopingTask;
import com.azias.notebook.Notebook;
import com.azias.notebook.addons.events.AddonClassGenericEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AddonClassMethodCallerTask implements LoopingTask {
	private final static Logger logger = LoggerFactory.getLogger(AddonClassMethodCallerTask.class);
	
	private String methodName;
	private AddonClassGenericEvent classEvent;
	
	public AddonClassMethodCallerTask(String methodName, AddonClassGenericEvent classEvent) {
		this.methodName = methodName;
		this.classEvent = classEvent;
	}
	
	@Override
	public boolean init(Object app, AddonLoader al) {
		return true;
	}
	
	@Override
	public boolean update(Object app, AddonLoader al, int currentTaskStep) {
		// Checking if the end of the list was reached previously.
		if(currentTaskStep >= al.getAddonIds().length) {
			return true;
		}
		
		logger.debug("Calling {} for {}...", methodName, al.getAddonIds()[currentTaskStep]);
		
		// Checks for addons without code and skips them
		// TODO: Check for potential NullPointerException when getting a null map entry.
		// This possibility should be accounted for when initializing the AddonLoader.
		/*if(!al.getAddonsInfos().get(al.getAddonsIds()[currentTaskStep]).hasCode()) {
			logger.debug("This addon doesn't have code, skipping !");
			return false;
		}/**/
		
		for(Class<?> addonClass : classEvent.getAddonClassesList()) {
			if(!addonClass.getAnnotation(Addon.class).id().equals(al.getAddonIds()[currentTaskStep])) {
				continue;
			}
			
			for(Method method : addonClass.getMethods()) {
				logger.trace("Seeking method {}, currently at: {}", methodName, method.getName());
				
				if(method.getName().equals(methodName)) {
					logger.trace("Attempting to execute method");
					
					try {
						//TODO: Check if the tyoe cast os correct !
						
						method.invoke(addonClass.getDeclaredConstructor().newInstance(), (Notebook) app, al, classEvent);
					} catch(NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
						logger.error("An error occured while executing the \"{}\" function for \"{}\"",
								"PreInitialization",
								al.getAddonIds()[currentTaskStep]);
						e.printStackTrace();
						//throw e;
					}
					break;
				}
			}
			
			break;
		}
		
		return false;
	}
	
	@Override
	public boolean finalize(Object app, AddonLoader al) {
		return false;
	}
	
	@Override
	public float getProgress() {
		return 0;
	}
	
	@Override
	public boolean hasEncounteredErrors() {
		//return exception != null;
		return false;
	}
	
	@Override
	public Exception getException() {
		//return exception;
		return null;
	}
	
	@Override
	public boolean giveArbitraryData(Object... data) {
		return false;
	}
}
