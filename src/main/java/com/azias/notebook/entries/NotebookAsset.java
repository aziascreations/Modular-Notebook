package com.azias.notebook.entries;

import java.util.UUID;

public class NotebookAsset {
	private String name;
	private UUID assetUUID;
	private AssetTypeEnum assetType;
	
	public NotebookAsset(String name, UUID assetUUID, AssetTypeEnum assetType) {
		this.name = name;
		this.assetUUID = assetUUID;
		this.assetType = assetType;
	}
	
	public UUID getUUID() {
		return assetUUID;
	}
	
	public AssetTypeEnum getAssetType() {
		return assetType;
	}
}
