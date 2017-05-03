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
	
	private Disc() {
		super("");
		director = "";
		barcode = "";
		fsk = -1;
	}
	
	/**
	 * Returns a new disc with merged information of this one and the given one.
	 * The given discs field values are preferred over this ones. Only if a field of the given discs is null or contain an escape value, this discs value is used instead.
	 * @param newer The dominant book to merge with.
	 * @return A new merged book. 
	 */
	public Disc merge(Disc newer) {
		String director = getDirector();
		String title = getTitle();
		String barcode = getBarcode();
		int fsk = getFsk();
		
		if(newer.getDirector() != null && !newer.getDirector().equals("")) {
			director = newer.getDirector();
		}
		if(newer.getTitle() != null && !newer.getTitle().equals("")) {
			title = newer.getTitle();
		}
		if(newer.getBarcode() != null && !newer.getBarcode().equals("")) {
			barcode = newer.getBarcode();
		}
		if(newer.getFsk() > 0) {
			fsk = newer.getFsk();
		}
		
		return new Disc(title, barcode, director, fsk);
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
