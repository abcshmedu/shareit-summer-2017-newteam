package edu.hm.eporcio.shareIt.mediaAdministration.access;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for equals method of class Disc.
 * @author Elias Porcio, elias.porcio@hm.edu
 * @version May 4, 2017
 */
@RunWith(Parameterized.class)
public class DiscEqualsTest {

    private final Disc disc;
    private final Object toCompare;
    private final boolean expected;
    

    
    public DiscEqualsTest(Disc disc, Object toCompare, boolean expected) {
        super();
        this.disc = disc;
        this.toCompare = toCompare;
        this.expected = expected;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]
        {
            {new Disc("valid1", "0000000000000", "valid2", 16), new Disc("valid1", "0000000000000", "valid2", 16), true},
            {new Disc(null, null, null, -1), new Disc(null, null, null, -1), true},
            
            {new Disc("valid1", "0000000000000", "valid2", 16), null, false},
            {new Disc("valid1", "0000000000000", "valid2", 16), new Disc("invalid", "0000000000000", "invalid", 16), false},
            
            {new Disc("valid1", "0000000000000", "valid2", 16), new Disc(null, null, null, -1), false},
            {new Disc("valid1", "0000000000000", "valid2", 16), new Disc("valid1", null, null, -1), false},
            {new Disc("valid1", "0000000000000", "valid2", 16), new Disc("valid1", "0000000000000", null, -1), false},
            {new Disc("valid1", "0000000000000", "valid2", 16), new Disc("valid1", "0000000000000", "valid2", -1), false},
            
            {new Disc("valid1", "0000000000000", "valid2", 16), new Disc("invalid", null, null, -1), false},
            {new Disc("valid1", "0000000000000", "valid2", 16), new Disc("valid1", "invalid", null, -1), false},
            {new Disc("valid1", "0000000000000", "valid2", 16), new Disc("valid1", "0000000000000", "invalid", -1), false},
            {new Disc("valid1", "0000000000000", "valid2", 16), new Disc("valid1", "0000000000000", "valid2", -1), false},
            
            {new Disc(null, null, null, -1), new Disc("invalid", null, null, -1), false},
            {new Disc("valid1", null, null, -1), new Disc("valid1", "invalid", null, -1), false},
            {new Disc("valid1", "valid2", null, -1), new Disc("valid1", "valid2", "invalid", -1), false},
        });
    }

    @Test
    public void equals() {
        assertEquals(expected, disc.equals(toCompare));
    }

}
