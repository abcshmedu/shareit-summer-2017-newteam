package edu.hm.eporcio.shareIt.mediaAdministration.access;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for merge method of class Medium.
 * @author Elias Porcio, elias.porcio@hm.edu
 * @version May 4, 2017
 */
@RunWith(Parameterized.class)
public class MediumTestMerge {

    private final Medium old;
    private final Medium newer;
    private final Medium expected;
    
    public MediumTestMerge(Medium old, Medium newer, Medium expected) {
        this.old = old;
        this.newer = newer;
        this.expected = expected;
    }
    
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]
        {
            {new Medium("old"), new Medium("new"), new Medium("new")},
            {new Medium("old"), new Medium(null), new Medium("old")},
        });
    }

    @Test
    public void merge() {
        Medium actual = old.merge(newer);
        assertEquals(expected, actual);
    }

}
