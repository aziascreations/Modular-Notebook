package com.azias.commons.addons.tasks;

import com.azias.commons.addons.AddonLoader;

public interface ClearableTask {
	boolean clear(Object app, AddonLoader al);
}
