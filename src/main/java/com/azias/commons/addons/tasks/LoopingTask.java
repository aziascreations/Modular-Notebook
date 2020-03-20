package com.azias.commons.addons.tasks;

import com.azias.commons.addons.AddonLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface LoopingTask extends Task {
	Logger logger = LoggerFactory.getLogger(LoopingTask.class);
	
	@Override
	default boolean execute(Object app, AddonLoader al) {
		logger.error("Something has gone horribly wrong and \"execute(game, al)\" was called in a looping task.");
		logger.error("This program will now be exiting, I wish you the best of luck for the rest.");
		//System.exit(ExitCodeEnum.AddonLoaderLoopingTaskExecuteThingy.getCode());
		return false;
	}
	
	/**
	 * Called over and over by the AddonLoader until it returns true.
	 */
	boolean update(Object app, AddonLoader al, int currentTaskStep);
	
	float getProgress();
}
