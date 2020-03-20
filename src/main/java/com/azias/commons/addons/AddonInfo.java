package com.azias.commons.addons;

import com.github.zafarkhaja.semver.Version;

import java.util.HashMap;

public class AddonInfo {
	private String id, name, description;
	private String[] authors, credits;
	
	private String versionStr, versionUrl, projectUrl, updateUrl;
	
	private HashMap<String, String> dependencies;
	
	private transient Version version;
	private transient HashMap<String, Version> parsedDependencies;
	
	/**
	 * TEMP (Absolute or relative -> (given value + addon id))
	 * Defined from the addonFolder variable in one of the first loops in AddonLoader.
	 * The fact that it will be absolute or relative will depend on the original variable.
	 */
	private transient String dataPath;
	
	// isLoaded still has no use...
	// Might be used to indicate if code has been executed for the addons
	// TODO: figure that shit out
	//protected transient boolean isLoaded = false;
	
	/**
	 * Used to indicate if an addon has a class that use the {@link Addon}
	 * Annotation with the same id as the addon.
	 */
	// Isn't it kind of useless tho ?
	// Or was it used originally since the Reflector thingy can't handle the ones that don't exists ?
	private transient boolean hasCode = false;
	
	// Was useless...
	/*public void resetTransientFields() {
		this.hasCode = false;
	}/**/
	
	//TODO: make a Parse versions function.
	
	@Deprecated
	public void fillDummy() {
		id = "id";
		name = "name";
		description = "desc";
		
		authors = new String[]{"A1", "A2"};
		credits = new String[]{"C1", "C2"};
		
		versionStr = "1.0.0";
		versionUrl = "http://localhost/version.txt";
		projectUrl = "http://localhost/none.html";
		updateUrl = "http://localhost/void.zip";
		dependencies = new HashMap<>();
		dependencies.put("com.test", "1.*.*");
	}
	
	public Version getVersion() {
		return version;
	}
	
	public void setVersion(Version version) {
		this.version = version;
	}
	
	public String getDataPath() {
		return dataPath;
	}
	
	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public boolean getHasCode() {
		return hasCode;
	}
	
	public boolean hasCode() {
		return hasCode;
	}
	
	protected void setHasCode(boolean hasCode) {
		this.hasCode = hasCode;
	}
	
	public HashMap<String, Version> getParsedDependencies() {
		return parsedDependencies;
	}
	
	public void setParsedDependencies(HashMap<String, Version> parsedDependencies) {
		this.parsedDependencies = parsedDependencies;
	}
}
