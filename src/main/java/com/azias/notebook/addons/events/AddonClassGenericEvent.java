package com.azias.notebook.addons.events;

import java.util.ArrayList;

public class AddonClassGenericEvent {
	private ArrayList<Class<?>> addonClasses;
	
	public AddonClassGenericEvent() {
		addonClasses = new ArrayList<>();
	}
	
	public ArrayList<Class<?>> getAddonClassesList() {
		return addonClasses;
	}
}
