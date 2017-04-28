package edu.hm.eporcio.shareIt.mediaAdministration.logic;

import java.util.HashMap;
import java.util.Map;
import edu.hm.eporcio.shareIt.mediaAdministration.access.Book;
import edu.hm.eporcio.shareIt.mediaAdministration.access.Disc;
import edu.hm.eporcio.shareIt.mediaAdministration.access.Medium;

/**
 * A service thats provides methods to access and change media information.
 * @author Elias Porcio
 * @version 27.04.2017
 */
public class MediaServiceImpl implements MediaService {
	
	/** All books known to the system, identified by their ISBN. */
	final Map<String, Book> knownBooks = new HashMap<>();
	/** All discs known to the system, identified by their Barcode. */
	final Map<String, Disc> knownDiscs = new HashMap<>();

	@Override
	public MediaServiceResult addBook(Book toAdd) {
		MediaServiceResult response = validateBook(toAdd);
		
		if(knownBooks.containsKey(toAdd.getIsbn())) {
			response = MediaServiceResult.IsbnAlreadyKnown;
		}
		
		if(response == MediaServiceResult.Ok) {
			knownBooks.put(toAdd.getIsbn(), toAdd);
		}
		
		return response;
	}

	@Override
	public MediaServiceResult addDisc(Disc toAdd) {
		MediaServiceResult response = validateDisc(toAdd);
		
		if(knownDiscs.containsKey(toAdd.getBarcode())) {
			response = MediaServiceResult.BarcodeAlreadyKnown;
		}
		
		if(response == MediaServiceResult.Ok) {
			knownDiscs.put(toAdd.getBarcode(), toAdd);
		}
		
		return response;
	}

	@Override
	public Medium[] getBooks() {
		Medium[] books = new Medium[knownBooks.size()];
		return knownBooks.values().toArray(books);
	}

	@Override
	public Medium[] getDiscs() {
		Medium[] discs = new Medium[knownDiscs.size()];
		return knownDiscs.values().toArray(discs);
	}

	@Override
	public MediaServiceResult updateBook(Book updated) {
		if(!knownBooks.containsKey(updated.getIsbn())) {
			return MediaServiceResult.IsbnNotFound;
		}
		
		if(updated.getAuthor() == null && updated.getTitle() == null) {
			return MediaServiceResult.NothingToModify;
		}
		
		Book toUpdate = knownBooks.get(updated.getIsbn());
		knownBooks.put(updated.getIsbn(), toUpdate.merge(updated));
		
		return MediaServiceResult.Ok;
	}

	@Override
	public MediaServiceResult updateDisc(Disc updated) {
		if(!knownDiscs.containsKey(updated.getBarcode())) {
			return MediaServiceResult.BarcodeNotFound;
		}
		
		if(updated.getDirector() == null && updated.getTitle() == null && updated.getFsk() < 0) {
			return MediaServiceResult.NothingToModify;
		}

		Disc toUpdate = knownDiscs.get(updated.getBarcode());
		knownDiscs.put(updated.getBarcode(), toUpdate.merge(updated));
		
		return MediaServiceResult.Ok;
	}
	
	/**
	 * Checks a book for validity. A book is valid if none of its fields is null, empty or contain an escape value and its ISBN matches the ISBN-13 pattern.
	 * @param toCheck The book to Check.
	 * @return A MediaServiceResult object indicating validity or an respective error.
	 */
	private MediaServiceResult validateBook(Book toCheck) {
		if(
				toCheck.getAuthor() == null ||
				toCheck.getAuthor().length() == 0 ||
				toCheck.getTitle() == null ||
				toCheck.getTitle().length() == 0 ||
				toCheck.getIsbn() == null ||
				toCheck.getIsbn().length() == 0
		) {
			return MediaServiceResult.MissingField;
		}
		
		final boolean isIsbnValid = toCheck.getIsbn().matches("ISBN [0-9]{3}-[0-9]-[0-9]{5}-[0-9]{3}-[0-9]");
		if(!isIsbnValid) {
			return MediaServiceResult.IsbnInvalid;
		}
		
		return MediaServiceResult.Ok;
	}
	
	/**
	 * Checks a disc for validity. A disc is valid if none of its fields is null, empty or contain an escape value and its Barcode matches the EAN pattern.
	 * @param toCheck The disc to Check.
	 * @return A MediaServiceResult object indicating validity or an respective error.
	 */
	private MediaServiceResult validateDisc(Disc toCheck) {
		if(
				toCheck.getDirector() == null ||
				toCheck.getDirector().length() == 0 ||
				toCheck.getTitle() == null ||
				toCheck.getTitle().length() == 0 ||
				toCheck.getBarcode() == null ||
				toCheck.getBarcode().length() == 0 ||
				toCheck.getFsk() < 0
		) {
			return MediaServiceResult.MissingField;
		}
		
		final boolean isBarcodeValid = toCheck.getBarcode().matches("[0-9]{13}");
		if(!isBarcodeValid) {
			return MediaServiceResult.BarcodeInvalid;
		}
		
		return MediaServiceResult.Ok;
	}

}
