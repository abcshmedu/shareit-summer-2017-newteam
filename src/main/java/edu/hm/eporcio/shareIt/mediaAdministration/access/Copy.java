package edu.hm.eporcio.shareIt.mediaAdministration.access;

public class Copy {
	private final Medium medium;
	private final String owner;
	
	public Medium getMedium() {
		return medium;
	}

	public String getOwner() {
		return owner;
	}

	public Copy(Medium medium, String owner) {
		super();
		this.medium = medium;
		this.owner = owner;
	}
}
