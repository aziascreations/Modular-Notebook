package com.azias.commons.addons.tasks;

import com.azias.commons.addons.AddonLoader;

public interface Task {
	/**
	 * Initializes the Task by preparing variables and running checks.
	 * It is up to you to decide what it does, but it shouldn't do the heavy lifting here.
	 * @param app The Object that contains the parent {@Link AddonLoader} with which the task may interact.
	 * @param al The {@Link AddonLoader} that handles this task.
	 * @return true if no errors occurred while initializing.
	 */
	boolean init(Object app, AddonLoader al);
	
	/**
	 * Executes the code that the Task is made for.
	 * All the heavy lifting and time consuming stuff should be done in here !
	 * @param app The Object that contains the parent {@Link AddonLoader} with which the Task may interact.
	 * @param al The {@Link AddonLoader} that handles this task.
	 * @return true if no errors occurred while executing.
	 */
	boolean execute(Object app, AddonLoader al);
	
	/**
	 * Clears the variables if needed.
	 * This function should not prepare stuff to be garbage collected, implement {@Link ClearableTask} to do that !
	 * @param app The Object that contains the parent {@Link AddonLoader} with which the Task may interact.
	 * @param al The {@Link AddonLoader} that handles this task.
	 * @return true if no errors occurred while finishing.
	 */
	boolean finalize(Object app, AddonLoader al);
	
	
	@Deprecated
	boolean hasEncounteredErrors();
	
	@Deprecated
	Exception getException();
	
	/**
	 * Don't use it !
	 * @param Object... data
	 * @return boolean
	 */
	@Deprecated
	boolean giveArbitraryData(Object... data);
}
