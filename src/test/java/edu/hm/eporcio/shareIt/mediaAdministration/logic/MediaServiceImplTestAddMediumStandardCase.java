package edu.hm.eporcio.shareIt.mediaAdministration.logic;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import edu.hm.eporcio.shareIt.mediaAdministration.access.Book;
import edu.hm.eporcio.shareIt.mediaAdministration.access.Disc;
import edu.hm.eporcio.shareIt.mediaAdministration.access.Medium;
/**
 * Tests for the addBook and addDisc methods of class MediaServiceImpl. Only covers the standard case of creating a medium and adding it.
 * @author Elias Porcio, elias.porcio@hm.edu
 * @version May 4, 2017
 */
@RunWith(Parameterized.class)
public class MediaServiceImplTestAddMediumStandardCase {
    
    private final Medium medium;
    private final MediaServiceResult expected;
    
    public MediaServiceImplTestAddMediumStandardCase(Medium medium, MediaServiceResult expected) {
        this.medium = medium;
        this.expected = expected;
    }
    
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]
        {
            //AddBook------------------------------------------------------------------------
            //valid input
            {new Book("valid", "valid", "000-0-00-000000-0"), MediaServiceResult.Ok},
            
            //One field missing
            {new Book("", "valid", "000-0-00-000000-0"), MediaServiceResult.MissingField},
            {new Book(null, "valid", "000-0-00-000000-0"), MediaServiceResult.MissingField},
            {new Book("valid", "", "000-0-00-000000-0"), MediaServiceResult.MissingField},
            {new Book("valid", null, "000-0-00-000000-0"), MediaServiceResult.MissingField},
            {new Book("valid", "valid", ""), MediaServiceResult.MissingField},
            {new Book("valid", "valid", null), MediaServiceResult.MissingField},
            
            //invalid ISBN
            {new Book("valid", "valid", "2"), MediaServiceResult.IsbnInvalid},
            {new Book("valid", "valid", "0000-00000000"), MediaServiceResult.IsbnInvalid},
            {new Book("valid", "valid", "000-0-00-000000-1"), MediaServiceResult.IsbnInvalid},
            
            
            //AddDisc------------------------------------------------------------------------
            //valid input
            {new Disc("valid", "0000000000000", "valid", 16), MediaServiceResult.Ok},
            
            //One field missing
            {new Disc(null, "0000000000000", "valid", 16), MediaServiceResult.MissingField},
            {new Disc("", "0000000000000", "valid", 16), MediaServiceResult.MissingField},
            {new Disc("valid", "", "valid", 16), MediaServiceResult.MissingField},
            {new Disc("valid", null, "valid", 16), MediaServiceResult.MissingField},
            {new Disc("valid", "0000000000000", null, 16), MediaServiceResult.MissingField},
            {new Disc("valid", "0000000000000", "", 16), MediaServiceResult.MissingField},
            {new Disc("valid", "0000000000000", "valid", -1), MediaServiceResult.MissingField},
            
            //invalid Barcode
            {new Disc("valid", "1", "valid", 16), MediaServiceResult.BarcodeInvalid},
            {new Disc("valid", "000000000001", "valid", 16), MediaServiceResult.BarcodeInvalid}
        });
    }

    @Test
    public void addMedium() {
        MediaServiceResult actual;
        MediaService service = new MediaServiceImpl();
        
        if (medium.getClass() == Book.class) {
            actual = service.addBook((Book) medium);
        }
        else {
            actual = service.addDisc((Disc) medium);
        }
        
        assertEquals(expected, actual);
    }

}
