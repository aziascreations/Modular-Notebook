package com.azias.notebook.entries;

public enum AssetTypeEnum {
	// similar to the HTML MIME types
	TEXT(1, null, "text", new String[]{}),
	IMAGE(2, null, "image", new String[]{}),
	ARCHIVE(3, null, "archive", new String[]{}),
	AUDIO(4, null, "audio", new String[]{}),
	VIDEO(5, null, "video", new String[]{}),
	APPLICATION(6, null, "app", new String[]{}),
	
	PLAINTEXT(100, TEXT, "plain", new String[]{"txt"}),
	MARKDOWN(101, TEXT, "markdown", new String[]{"md"}),
	CODE(102, TEXT, "source-code", new String[]{}),
	
	// Use a sub-parent like SOURCE or CODE ???
	JAVA(CODE.id * 100 + 0, TEXT, "java", new String[]{"java"}),
	PUREBASIC(CODE.id * 100 + 1, TEXT, "purebasic", new String[]{"pb", "pbp", "pbi", "pbf"}),
	
	PNG(200, IMAGE, "png", new String[]{"png"}),
	JPG(201, IMAGE, "jpg", new String[]{"jpg", "jpeg"}),
	BMP(202, IMAGE, "bmp", new String[]{"bmp"}),
	
	ZIP(300, ARCHIVE, "zip", new String[]{"zip"}),
	RAR(301, ARCHIVE, "rar", new String[]{"rar"}),
	JAR(302, ARCHIVE, "jar", new String[]{"jar"});
	
	private final int id;
	private final AssetTypeEnum parentType;
	private final String label;
	private final String[] extensions;
	
	/*private AssetTypeEnum(int id, AssetTypeEnum parentType, String label) {
		super(id, parentType, label, new String[0]);
	}/**/
	
	// TODO: Make 2 constructors, 1 for roots which has only the id
	// And the other one without id since it can be calculated.
	
	private AssetTypeEnum(int id, AssetTypeEnum parentType, String label, String[] extensions) {
		this.id = id;
		this.parentType = parentType;
		this.label = label;
		this.extensions = extensions;
	}
	
	//public static AssetTypeEnum getParentType() {
	//	return
	//}
}
