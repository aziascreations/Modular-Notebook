package com.azias.notebook.tests;

import com.azias.notebook.entries.AssetTypeEnum;
import com.azias.notebook.entries.NotebookAsset;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class AssetTypeGsonTest {
	
	
	public static void main(String[] args) {
		Gson gson = new Gson();
		
		HashMap<UUID, AssetTypeEnum> entryAssets = new HashMap<>();
		entryAssets.put(UUID.randomUUID(), AssetTypeEnum.BMP);
		entryAssets.put(UUID.randomUUID(), AssetTypeEnum.ZIP);
		
		System.out.println(gson.toJson(entryAssets));
		
		ArrayList<NotebookAsset> assets = new ArrayList<>();
		assets.add(new NotebookAsset("test", UUID.randomUUID(), AssetTypeEnum.BMP));
		assets.add(new NotebookAsset("test2", UUID.randomUUID(), AssetTypeEnum.ZIP));
		
		System.out.println(gson.toJson(assets));
		
	}
	
}
