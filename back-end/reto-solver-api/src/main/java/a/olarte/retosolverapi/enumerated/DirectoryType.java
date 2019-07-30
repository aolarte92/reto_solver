package a.olarte.retosolverapi.enumerated;

public enum DirectoryType {

	INPUT_DIRECTORY("input/"),
	OUTPUT_DIRECTORY("output/");
	
	private String url;

	private DirectoryType(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
	
	
}
