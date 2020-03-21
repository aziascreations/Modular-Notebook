package com.azias.commons.addons.tasks;

import com.azias.commons.addons.AddonLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface LoopingTask extends Task {
	Logger logger = LoggerFactory.getLogger(LoopingTask.class);
	
	/**
	 * ???
	 * @param app The Object that contains the parent {@Link AddonLoader} with which the Task may interact.
	 * @param al The {@Link AddonLoader} that handles this task.
	 * @return false to indicate that an error occurred since it should be called in a LoopingTask.
	 */
	@Override
	default boolean execute(Object app, AddonLoader al) {
		logger.error("Something has gone horribly wrong and \"execute(app, al)\" was called in a LoopingTask !");
		//logger.error("This program will now be exiting, I wish you the best of luck for the rest.");
		//System.exit(ExitCodeEnum.AddonLoaderLoopingTaskExecuteThingy.getCode());
		return false;
	}
	
	/**
	 * Function that is called over and over by the parent {@Link AddonLoader} until it returns true.
	 * @param app The Object that contains the parent {@Link AddonLoader} with which the Task may interact.
	 * @param al The {@Link AddonLoader} that handles this task.
	 * @param currentTaskStep ???
	 * @return true if the LoopingTask has finished what it had to do.
	 */
	boolean update(Object app, AddonLoader al, int currentTaskStep);
	
	
	@Deprecated
	float getProgress();
}
