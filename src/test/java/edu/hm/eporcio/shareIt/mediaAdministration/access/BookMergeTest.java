package edu.hm.eporcio.shareIt.mediaAdministration.access;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for merge method of class Book.
 * @author Elias Porcio, elias.porcio@hm.edu
 * @version May 4, 2017
 */
@RunWith(Parameterized.class)
public class BookMergeTest {

    private final Book old;
    private final Book newer;
    private final Book expected;
    
    public BookMergeTest(Book old, Book newer, Book expected) {
        this.old = old;
        this.newer = newer;
        this.expected = expected;
    }
    
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]
        {
            {new Book("old1", "old2", "ISBN 000-0-00000-000-0"), new Book(null, null, null), new Book("old1", "old2", "ISBN 000-0-00000-000-0")},
            {new Book("old1", "old2", "ISBN 000-0-00000-000-0"), new Book("new1", null, null), new Book("new1", "old2", "ISBN 000-0-00000-000-0")},
            {new Book("old1", "old2", "ISBN 000-0-00000-000-0"), new Book("", "", ""), new Book("old1", "old2", "ISBN 000-0-00000-000-0")},
            {new Book("old1", "old2", "ISBN 000-0-00000-000-0"), new Book("new1", "new2", "ISBN 111-1-11111-111-1"), new Book("new1", "new2", "ISBN 111-1-11111-111-1")},
            {new Book(null, null, null), new Book(null, null, null), new Book(null, null, null)}
        });
    }

    @Test
    public void merge() {
        Book actual = old.merge(newer);
        assertEquals(expected, actual);
    }

}
