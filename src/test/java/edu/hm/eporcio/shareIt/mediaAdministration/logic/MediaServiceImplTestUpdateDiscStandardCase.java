package edu.hm.eporcio.shareIt.mediaAdministration.logic;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import edu.hm.eporcio.shareIt.mediaAdministration.access.Disc;

/**
 * Tests for the updateDisc method of class MediaServiceImpl. Only covers the standard case of adding a disc, then updating it.
 * @author Elias Porcio, elias.porcio@hm.edu
 * @version May 4, 2017
 */
@RunWith(Parameterized.class)
public class MediaServiceImplTestUpdateDiscStandardCase {
    
    private final Disc toUpdate;
    private final Disc updated;
    private final Disc expectedMerged;
    private final MediaServiceResult expectedResult;
    
    
    public MediaServiceImplTestUpdateDiscStandardCase(Disc toUpdate, Disc updated, Disc merged, MediaServiceResult expectedResult) {
        this.toUpdate = toUpdate;
        this.updated = updated;
        this.expectedMerged = merged;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]
        {
            //valid input
            {
                new Disc("old1", "0000000000000", "old2", 16),
                new Disc("new1", "0000000000000", "new2", 12),
                new Disc("new1", "0000000000000", "new2", 12),
                MediaServiceResult.Ok
            },
            
            {
                new Disc("old1", "0000000000000", "old2", 16),
                new Disc(null, "0000000000000", "new2", 12),
                new Disc("old1", "0000000000000", "new2", 12),
                MediaServiceResult.Ok
            },
            
            {
                new Disc("old1", "0000000000000", "old2", 16),
                new Disc("", "0000000000000", "", 12),
                new Disc("old1", "0000000000000", "old2", 12),
                MediaServiceResult.Ok
            },
            
            {
                new Disc("old1", "0000000000000", "old2", 16),
                new Disc("new1", "0000000000000", null, -1),
                new Disc("new1", "0000000000000", "old2", 16),
                MediaServiceResult.Ok
            },
            
            //Barcode modification
            {
                new Disc("old1", "0000000000000", "old2", 16),
                new Disc("new1", "1111111111111", "new2", 12),
                new Disc("old1", "0000000000000", "old2", 16),
                MediaServiceResult.BarcodeNotFound
            },
            
            //nothing to modify
            {
                new Disc("old1", "0000000000000", "old2", 16),
                new Disc(null, "0000000000000", null, -1),
                new Disc("old1", "0000000000000", "old2", 16),
                MediaServiceResult.NothingToModify
            },
            
            {
                new Disc("old1", "0000000000000", "old2", 16),
                new Disc("", "0000000000000", "", -1),
                new Disc("old1", "0000000000000", "old2", 16),
                MediaServiceResult.NothingToModify
            },
            
            {
                new Disc("old1", "0000000000000", "old2", 16),
                new Disc(null, "0000000000000", "", -1),
                new Disc("old1", "0000000000000", "old2", 16),
                MediaServiceResult.NothingToModify
            },
        });
    }

    @Test
    public void addDisc() {
        MediaServiceResult result;
        MediaService service = new MediaServiceImpl();
                
        result = service.addDisc(toUpdate);
        assertEquals(MediaServiceResult.Ok, result);
        
        result = service.updateDisc(updated);
        assertEquals(expectedResult, result);
        
        Disc actualMerged = service.getDisc(toUpdate.getBarcode());
        assertEquals(expectedMerged, actualMerged);
    }

}
