package edu.hm.eporcio.shareIt.mediaAdministration.logic;

import edu.hm.eporcio.shareIt.mediaAdministration.access.Book;
import edu.hm.eporcio.shareIt.mediaAdministration.access.Disc;
import edu.hm.eporcio.shareIt.mediaAdministration.access.Medium;

public interface MediaService {

	/**
	 * Adds a book to the system.
	 * All fields of the book must have valid values.
	 * @param toAdd The book to add.
	 * @return A MediaServiceResult object indicating problems or success.
	 */
	public MediaServiceResult addBook(Book toAdd);
	
	/**
	 * Adds a disc to the system.
	 * All fields of the disc must have valid values.
	 * @param toAdd The disc to add.
	 * @return A MediaServiceResult object indicating problems or success.
	 */
	public MediaServiceResult addDisc(Disc toAdd);
	
	/**
	 * Lists all currently known Books.
	 * @return An array of all currently known Books.
	 */
	public Medium[] getBooks();
	
	/**
	 * Lists all currently known Discs.
	 * @return An array of all currently known Discs.
	 */
	public Medium[] getDiscs();
	
	/**
	 * Updates the fields of a book that is already known to the system.
	 * Fields that should not be updated must be null or have an escape value.
	 * The book is identified via its ISBN which cannot be changed.
	 * @param updated A Book containing the updated data.
	 * @return A MediaServiceResult object indicating problems or success.
	 */
	public MediaServiceResult updateBook(Book updated);
	
	/**
	 * Updates the fields of a Disc that is already known to the system.
	 * Fields that should not be updated must be null or have an escape value.
	 * The disc is identified via its Barcode which cannot be changed.
	 * @param updated A Disc containing the updated data.
	 * @return A MediaServiceResult object indicating problems or success.
	 */
	public MediaServiceResult updateDisc(Disc updated);
}
