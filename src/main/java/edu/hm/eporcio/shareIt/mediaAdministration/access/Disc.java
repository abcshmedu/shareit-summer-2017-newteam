package edu.hm.eporcio.shareIt.mediaAdministration.access;

public class Disc extends Medium {
	private final String barcode;
	private final String director;
	private final int fsk;
	
	public Disc(String title, String barcode, String director, int fsk) {
		super(title);
		this.barcode = barcode;
		this.director = director;
		this.fsk = fsk;
	}

	public String getBarcode() {
		return barcode;
	}

	public String getDirector() {
		return director;
	}

	public int getFsk() {
		return fsk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
		result = prime * result + ((director == null) ? 0 : director.hashCode());
		result = prime * result + fsk;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disc other = (Disc) obj;
		if (barcode == null) {
			if (other.barcode != null)
				return false;
		} else if (!barcode.equals(other.barcode))
			return false;
		if (director == null) {
			if (other.director != null)
				return false;
		} else if (!director.equals(other.director))
			return false;
		if (fsk != other.fsk)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Disc [title=" + super.getTitle() + ", barcode=" + barcode + ", director=" + director + ", fsk=" + fsk + "]";
	}
	
	
}
