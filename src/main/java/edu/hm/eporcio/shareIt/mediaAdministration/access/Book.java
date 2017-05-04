package edu.hm.eporcio.shareIt.mediaAdministration.access;

/**
 * A book.
 * @author Elias Porcio, elias.porcio@hm.edu
 * @version May 4, 2017
 */
public class Book extends Medium {

    private final String author;
    private final String isbn;
    
    /**
     * Default constructor.
     * @param title The titel of the book.
     * @param author The author of the book.
     * @param isbn The books ISBN. Should be a valid ISBN-13.
     */
    public Book(String title, String author, String isbn) {
        super(title);
        this.author = author;
        this.isbn = isbn;
    }
    
    /**
     * Constructor for Jackson.
     */
    private Book() {
        super("");
        author = "";
        isbn = "";
    }
    
    /**
     * Returns a new book with merged information of this one and the given one.
     * The given books field values are preferred over this ones. Only if a field of the given book is null, this books value is used instead.
     * @param newer The dominant book to merge with.
     * @return A new merged book. 
     */
    public Book merge(Book newer) {
        String author = getAuthor();
        String title = getTitle();
        String isbn = getIsbn();
        
        if (newer.getAuthor() != null && !newer.getAuthor().equals("")) {
            author = newer.getAuthor();
        }
        if (newer.getTitle() != null && !newer.getTitle().equals("")) {
            title = newer.getTitle();
        }
        if (newer.getIsbn() != null && !newer.getIsbn().equals("")) {
            isbn = newer.getIsbn();
        }
        
        return new Book(title, author, isbn);
    }

    //CHECKSTYLE:OFF
    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }
    //CHECKSTYLE:ON

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Book other = (Book) obj;
        if (author == null) {
            if (other.author != null) {
                return false;
            }
        } else if (!author.equals(other.author)) {
            return false;
        }
        if (isbn == null) {
            if (other.isbn != null) {
                return false;
            }
        } else if (!isbn.equals(other.isbn)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Book [title=" + super.getTitle() + ", author=" + author + ", isbn=" + isbn + "]";
    }
}
