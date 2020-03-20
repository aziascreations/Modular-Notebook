package com.azias.commons.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
	/**
	 * Load a file and returns the text encoded in UTF-8.
	 *
	 * @param path
	 *            The desired file's path
	 * @return The file's content
	 * @throws IOException
	 */
	public static String fileToString(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
	}
}
