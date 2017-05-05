package edu.hm.eporcio.shareIt.mediaAdministration.access;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for merge method of class Disc
 * @author Elias Porcio, elias.porcio@hm.edu
 * @version May 4, 2017
 */
@RunWith(Parameterized.class)
public class DiscMergeTest {

    private final Disc old;
    private final Disc newer;
    private final Disc expected;
    
    public DiscMergeTest(Disc old, Disc newer, Disc expected) {
        this.old = old;
        this.newer = newer;
        this.expected = expected;
    }
    
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]
        {
            {new Disc("old1", "0000000000000", "old2", 16), new Disc(null, null, null, -1), new Disc("old1", "0000000000000", "old2", 16)},
            {new Disc("old1", "0000000000000", "old2", 16), new Disc("new1", null, null, -1), new Disc("new1", "0000000000000", "old2", 16)},
            {new Disc("old1", "0000000000000", "old2", 16), new Disc(null, null, null, 12), new Disc("old1", "0000000000000", "old2", 12)},
            {new Disc("old1", "0000000000000", "old2", 16), new Disc("", "", "", -1), new Disc("old1", "0000000000000", "old2", 16)},
            {new Disc("old1", "0000000000000", "old2", 16), new Disc("new1", "111111111111", "new2", 12), new Disc("new1", "111111111111", "new2", 12)},
            {new Disc(null, null, null, -1), new Disc(null, null, null, -1), new Disc(null, null, null, -1)}
        });
    }

    @Test
    public void merge() {
        Disc actual = old.merge(newer);
        assertEquals(expected, actual);
    }

}
