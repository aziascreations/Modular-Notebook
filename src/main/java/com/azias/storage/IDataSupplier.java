package com.azias.storage;

import java.util.UUID;

@Deprecated
public interface IDataSupplier {
	boolean hasFile(String filePath);
	boolean hasFile(UUID fileUUID);
}
