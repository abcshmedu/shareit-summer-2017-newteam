package edu.hm.eporcio.shareIt.mediaAdministration.logic;

import edu.hm.eporcio.shareIt.mediaAdministration.access.Book;
import edu.hm.eporcio.shareIt.mediaAdministration.access.Disc;

/**
 * A service administrating media.
 * @author Elias Porcio, elias.porcio@hm.edu
 * @version May 4, 2017
 */
public interface MediaService {

    /**
     * Adds a book to the system.
     * All fields of the book must have valid values.
     * @param toAdd The book to add.
     * @return A MediaServiceResult object indicating problems or success.
     */
    MediaServiceResult addBook(Book toAdd);
    
    /**
     * Adds a disc to the system.
     * All fields of the disc must have valid values.
     * @param toAdd The disc to add.
     * @return A MediaServiceResult object indicating problems or success.
     */
    MediaServiceResult addDisc(Disc toAdd);
    
    /**
     * Returns a book by its ISBN.
     * @param isbn ISBN of the book to get.
     * @return The book with the specified ISBN, null if the book is not known.
     */
    Book getBook(String isbn);
    
    /**
     * Returns a disc by its barcode.
     * @param barcode Barcode of the disc to get.
     * @return The disc with the specified barcode, null if the disc is not known.
     */
    Disc getDisc(String barcode);
    
    /**
     * Lists all currently known Books.
     * @return An array of all currently known Books.
     */
    Book[] getBooks();
    
    /**
     * Lists all currently known Discs.
     * @return An array of all currently known Discs.
     */
    Disc[] getDiscs();
    
    /**
     * Updates the fields of a book that is already known to the system.
     * Fields that should not be updated must be null or have an escape value.
     * The book is identified via its ISBN which cannot be changed.
     * @param updated A Book containing the updated data.
     * @return A MediaServiceResult object indicating problems or success.
     */
    MediaServiceResult updateBook(Book updated);
    
    /**
     * Updates the fields of a Disc that is already known to the system.
     * Fields that should not be updated must be null or have an escape value.
     * The disc is identified via its Barcode which cannot be changed.
     * @param updated A Disc containing the updated data.
     * @return A MediaServiceResult object indicating problems or success.
     */
    MediaServiceResult updateDisc(Disc updated);
}
