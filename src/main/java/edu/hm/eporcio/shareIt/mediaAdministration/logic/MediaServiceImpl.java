package edu.hm.eporcio.shareIt.mediaAdministration.logic;

import java.util.HashMap;
import java.util.Map;
import edu.hm.eporcio.shareIt.mediaAdministration.access.Book;
import edu.hm.eporcio.shareIt.mediaAdministration.access.Disc;

/**
 * A service thats provides methods to access and change media information.
 * @author Elias Porcio
 * @version 27.04.2017
 */
public class MediaServiceImpl implements MediaService {
    
    /** All books known to the system, identified by their ISBN. */
    private final Map<String, Book> knownBooks = new HashMap<>();
    /** All discs known to the system, identified by their Barcode. */
    private final Map<String, Disc> knownDiscs = new HashMap<>();

    @Override
    public MediaServiceResult addBook(Book toAdd) {
        MediaServiceResult response = validateBook(toAdd);
        
        if (knownBooks.containsKey(toAdd.getIsbn())) {
            response = MediaServiceResult.IsbnAlreadyKnown;
        }
        
        if (response == MediaServiceResult.Ok) {
            knownBooks.put(toAdd.getIsbn(), toAdd);
        }
        
        return response;
    }

    @Override
    public MediaServiceResult addDisc(Disc toAdd) {
        MediaServiceResult response = validateDisc(toAdd);
        
        if (knownDiscs.containsKey(toAdd.getBarcode())) {
            response = MediaServiceResult.BarcodeAlreadyKnown;
        }
        
        if (response == MediaServiceResult.Ok) {
            knownDiscs.put(toAdd.getBarcode(), toAdd);
        }
        
        return response;
    }

    @Override
    public Book[] getBooks() {
        Book[] books = new Book[knownBooks.size()];
        return knownBooks.values().toArray(books);
    }

    @Override
    public Disc[] getDiscs() {
        Disc[] discs = new Disc[knownDiscs.size()];
        return knownDiscs.values().toArray(discs);
    }

    @Override
    public MediaServiceResult updateBook(Book updated) {
        if (!knownBooks.containsKey(updated.getIsbn())) {
            return MediaServiceResult.IsbnNotFound;
        }
        
        if ((updated.getAuthor() == null || updated.getAuthor().length() == 0) && (updated.getTitle() == null || updated.getTitle().length() == 0)) {
            return MediaServiceResult.NothingToModify;
        }
        
        Book toUpdate = knownBooks.get(updated.getIsbn());
        knownBooks.put(updated.getIsbn(), toUpdate.merge(updated));
        
        return MediaServiceResult.Ok;
    }

    @Override
    public MediaServiceResult updateDisc(Disc updated) {
        if (!knownDiscs.containsKey(updated.getBarcode())) {
            return MediaServiceResult.BarcodeNotFound;
        }
        
        if ((updated.getDirector() == null || updated.getDirector().length() == 0) && (updated.getTitle() == null || updated.getTitle().length() == 0) && updated.getFsk() < 0) {
            return MediaServiceResult.NothingToModify;
        }

        Disc toUpdate = knownDiscs.get(updated.getBarcode());
        knownDiscs.put(updated.getBarcode(), toUpdate.merge(updated));
        
        return MediaServiceResult.Ok;
    }
    
    /**
     * Checks a book for validity. A book is valid if none of its fields is null, empty or contain an escape value and its ISBN is a valid ISBN-13.
     * @param toCheck The book to Check.
     * @return A MediaServiceResult object indicating validity or an respective error.
     */
    public MediaServiceResult validateBook(Book toCheck) {
        if (
                toCheck.getAuthor() == null 
                 || toCheck.getAuthor().length() == 0 
                 || toCheck.getTitle() == null 
                 || toCheck.getTitle().length() == 0 
                 || toCheck.getIsbn() == null 
                 || toCheck.getIsbn().length() == 0
        ) {
            return MediaServiceResult.MissingField;
        }
        
      //CHECKSTYLE:OFF
        final boolean isIsbnValid =
                toCheck.getIsbn().length() == 17 //13 digits + 4 dashes
                && toCheck.getIsbn().matches("([0-9]+-){4}[0-9]")
                && isChecksumValid(toCheck.getIsbn().replaceAll("-", ""));
      //CHECKSTYLE:ON

        if (!isIsbnValid) {
            return MediaServiceResult.IsbnInvalid;
        }
        
        return MediaServiceResult.Ok;
    }
    
    /**
     * Checks a disc for validity. A disc is valid if none of its fields is null, empty or contain an escape value and its Barcode is valid.
     * @param toCheck The disc to Check.
     * @return A MediaServiceResult object indicating validity or an respective error.
     */
    private MediaServiceResult validateDisc(Disc toCheck) {
        if (
                toCheck.getDirector() == null
                 || toCheck.getDirector().length() == 0
                 || toCheck.getTitle() == null
                 || toCheck.getTitle().length() == 0
                 || toCheck.getBarcode() == null
                 || toCheck.getBarcode().length() == 0
                 || toCheck.getFsk() < 0
        ) {
            return MediaServiceResult.MissingField;
        }
        

        if (!isChecksumValid(toCheck.getBarcode())) {
            return MediaServiceResult.BarcodeInvalid;
        }
        
        return MediaServiceResult.Ok;
    }

    @Override
    public Book getBook(String isbn) {
        return knownBooks.get(isbn);
    }

    @Override
    public Disc getDisc(String barcode) {
        return knownDiscs.get(barcode);
    }
    
    /**
     * Checks whether a barcode has a valid checksum according to the rules of an EAN. Also works with ISBNs if the dashes are removed before calling this function.
     * @param code The code to check for a valid checksum.
     * @return True if the checksum is valid, false otherwise.
     */
    private boolean isChecksumValid(String code) {
        //CHECKSTYLE:OFF
        if (code == null || code.length() != 13) {
            return false;
        }
        
        int checksum = 0;
        char[] digits = code.toCharArray();
        for (int digitIndex = 0; digitIndex != 12; digitIndex++) {
            if (digitIndex % 2 == 0) {
                checksum += Character.getNumericValue(digits[digitIndex]);
            } 
            else {
                checksum += 3 * Character.getNumericValue(digits[digitIndex]);
            }
        }
        checksum = (10 - checksum % 10) % 10;
        //CHECKSTYLE:ON
        
        return checksum == Character.getNumericValue(digits[digits.length - 1]);
    }

}
