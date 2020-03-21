package com.azias.commons.addons.tasks;

import com.azias.commons.addons.AddonLoader;

public interface ClearableTask {
	/**
	 * Prepares the Task everything it handles to be garbage cleaned.
	 * @param app The Object that contains the parent {@Link AddonLoader} with which the Task may interact.
	 * @param al The {@Link AddonLoader} that handles this task.
	 * @return true if no errors occurred while clearing.
	 */
	boolean clear(Object app, AddonLoader al);
}
