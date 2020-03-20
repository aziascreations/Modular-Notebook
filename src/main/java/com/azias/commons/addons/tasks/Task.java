package com.azias.commons.addons.tasks;

import com.azias.commons.addons.AddonLoader;

public interface Task {
	//String DEFAULT_TASK_ID = "task.id.default";
	
	/**
	 * Used to setup/prepare stuff for the Callback to use later.
	 */
	/*
	 * Le fait de passer le AddonLoader est probablement inutile comme "game" le contient déjà.
	 * Au pire ce ne sera pas une grosse modif pour plus tard.
	 */
	boolean init(Object app, AddonLoader al);
	
	boolean execute(Object app, AddonLoader al);
	
	boolean finalize(Object app, AddonLoader al);
	
	/**
	 * Used to check if the Task encountered exceptions that couldn't be thrown to the AddonLoader.
	 * In Task, you can just return a boolean, but in LoopingTask, you can't and i't easier to give the possibility to both.
	 */
	boolean hasEncounteredErrors();
	
	Exception getException();
	
	/**
	 * Should be avoided if possible !
	 * @param Object... data
	 * @return boolean
	 */
	@Deprecated
	boolean giveArbitraryData(Object... data);

	/*default String getId() {
		return DEFAULT_TASK_ID;
	}/**/
}
