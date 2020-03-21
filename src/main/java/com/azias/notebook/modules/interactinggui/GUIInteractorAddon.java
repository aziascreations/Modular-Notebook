package com.azias.notebook.modules.interactinggui;

import com.azias.commons.addons.Addon;
import com.azias.commons.addons.AddonLoader;
import com.azias.notebook.Notebook;
import com.azias.notebook.addons.events.AddonClassGenericEvent;

@Addon(id="gui-interactor")
public class GUIInteractorAddon {
	// implements Addon
	
	public static void registerInteractors(Notebook app, AddonLoader al, AddonClassGenericEvent classEvent) {
		app.getInteractorManager().addInteractor(new GuiInteractor());
	}
}
