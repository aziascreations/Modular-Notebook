package com.azias.notebook.addons.tasks;

import com.azias.commons.addons.Addon;
import com.azias.commons.addons.AddonLoader;
import com.azias.commons.addons.tasks.ClearableTask;
import com.azias.commons.addons.tasks.Task;
import com.azias.notebook.addons.events.AddonClassGenericEvent;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddonClassDiscoveryTask implements Task, ClearableTask {
	private final static Logger logger = LoggerFactory.getLogger(AddonClassDiscoveryTask.class);
	
	private AddonClassGenericEvent classEvent;
	private Class<?> annotationClass;
	
	public AddonClassDiscoveryTask(AddonClassGenericEvent classEvent, Class<?> annotationClass) {
		this.classEvent = classEvent;
		this.annotationClass = annotationClass;
	}
	
	@Override
	public boolean init(Object app, AddonLoader al) {
		return (classEvent != null) && (classEvent.getAddonClassesList() != null);
	}
	
	@Override
	public boolean execute(Object app, AddonLoader al) {
		// This should be used when we want to associate the class to the addon for ordered loading !
		// This task only discovers them !
		//String currentAddonId = al.getAddonIds()[currentTaskStep];
		
		// TODO: Add task to sort them and eliminate duplicates.
		
		classEvent.getAddonClassesList().addAll(new Reflections("").getTypesAnnotatedWith(Addon.class));
		
		logger.debug("{} found {} class(es) with the {} annotation.",
				this.getClass().getSimpleName(),
				classEvent.getAddonClassesList().size(),
				annotationClass.getSimpleName());
		
		return true;
	}
	
	@Override
	public boolean finalize(Object app, AddonLoader al) {
		return true;
	}
	
	@Override
	public boolean hasEncounteredErrors() {
		return false;
	}
	
	@Override
	public Exception getException() {
		return null;
	}
	
	@Override
	public boolean giveArbitraryData(Object... data) {
		return false;
	}
	
	@Override
	public boolean clear(Object app, AddonLoader al) {
		logger.debug("Test clear done !");
		return true;
	}
}
