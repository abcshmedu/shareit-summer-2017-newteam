package edu.hm.eporcio.shareIt.mediaAdministration.access;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for equals method of class Book.
 * @author Elias Porcio, elias.porcio@hm.edu
 * @version May 4, 2017
 */
@RunWith(Parameterized.class)
public class BookEqualsTest {

    private final Book book;
    private final Object toCompare;
    private final boolean expected;
    

    
    public BookEqualsTest(Book book, Object toCompare, boolean expected) {
        super();
        this.book = book;
        this.toCompare = toCompare;
        this.expected = expected;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]
        {
            {new Book("valid1", "valid2", "000-0-00000-000-0"), new Book("valid1", "valid2", "000-0-00000-000-0"), true},
            {new Book(null, null, null), new Book(null, null, null), true},
            
            {new Book("valid1", "valid2", "000-0-00000-000-0"), null, false},
            {new Book("valid1", "valid2", "000-0-00000-000-0"), new Disc("invalid", "0000000000000", "invalid", 16), false},
            
            {new Book("valid1", "valid2", "000-0-00000-000-0"), new Book(null, null, null), false},
            {new Book("valid1", "valid2", "000-0-00000-000-0"), new Book("valid1", null, null), false},
            {new Book("valid1", "valid2", "000-0-00000-000-0"), new Book("valid1", "valid2", null), false},
            
            {new Book("valid1", "valid2", "000-0-00000-000-0"), new Book("invalid", null, null), false},
            {new Book("valid1", "valid2", "000-0-00000-000-0"), new Book("valid1", "invalid", null), false},
            {new Book("valid1", "valid2", "000-0-00000-000-0"), new Book("valid1", "valid2", "invalid"), false},
            
            {new Book(null, null, null), new Book("invalid", null, null), false},
            {new Book("valid1", null, null), new Book("valid1", "invalid", null), false},
            {new Book("valid1", "valid2", null), new Book("valid1", "valid2", "invalid"), false},
        });
    }

    @Test
    public void equals() {
        assertEquals(expected, book.equals(toCompare));
    }

}
