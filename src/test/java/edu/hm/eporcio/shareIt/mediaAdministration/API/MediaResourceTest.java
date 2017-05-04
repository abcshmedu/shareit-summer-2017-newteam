package edu.hm.eporcio.shareIt.mediaAdministration.API;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.junit.After;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.hm.eporcio.shareIt.mediaAdministration.access.Book;
import edu.hm.eporcio.shareIt.mediaAdministration.access.Disc;
import edu.hm.eporcio.shareIt.mediaAdministration.logic.MediaServiceImpl;
import edu.hm.eporcio.shareIt.mediaAdministration.logic.MediaServiceResult;

/**
 * Tests for class MediaResource.
 * @author Elias Porcio, elias.porcio@hm.edu
 * @version May 4, 2017
 */
public class MediaResourceTest {
    
    /**
     * Resets the static service object after each test, to ensure independent tests.
     */
    @After
    public void resetServiceObject() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field service = MediaResource.class.getDeclaredField("SERVICE");
        service.setAccessible(true);
        
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(service, service.getModifiers() & ~Modifier.FINAL);
        
        service.set(null, new MediaServiceImpl());
    }
    
    //createBook------------------------------------------------------------------
    @Test
    public void createBook_validInput() throws JsonProcessingException {
        MediaResource resource = new MediaResource();
        ObjectMapper mapper = new ObjectMapper();
        Book book = new Book("valid", "valid", "000-0-00-000000-0");
        
        String actual = resource.createBook(mapper.writeValueAsString(book));
        String expected = "200 OK\r\n\r\n";
        assertEquals(expected, actual);
    }
    

    @Test
    public void createBook_invalidJson() throws JsonProcessingException {
        MediaResource resource = new MediaResource();
        ObjectMapper mapper = new ObjectMapper();
        Book book = new Book("valid", "valid", "000-0-00-000000-0");
        
        String actual = resource.createBook(mapper.writeValueAsString(book).replaceAll("\"", "'"));
        assertTrue(actual.startsWith("400"));
        assertTrue(actual.contains(MediaServiceResult.JsonInvalid.getStatus()));
        
    }
    
    @Test
    public void createBook_notABook() throws JsonProcessingException {
        MediaResource resource = new MediaResource();
        ObjectMapper mapper = new ObjectMapper();
        Disc disc = new Disc("valid", "0000000000000", "valid", 16);
        
        String actual = resource.createBook(mapper.writeValueAsString(disc));
        assertTrue(actual.startsWith("400"));
        assertTrue(actual.contains(MediaServiceResult.NotABook.getStatus()));
    }
    
    //createDisc------------------------------------------------------------------
    @Test
    public void createDisc_validInput() throws JsonProcessingException {
        MediaResource resource = new MediaResource();
        ObjectMapper mapper = new ObjectMapper();
        Disc disc = new Disc("valid", "0000000000000", "valid", 16);
        
        String actual = resource.createDisc(mapper.writeValueAsString(disc));
        String expected = "200 OK\r\n\r\n";
        assertEquals(expected, actual);
    }
    

    @Test
    public void createDisc_invalidJson() throws JsonProcessingException {
        MediaResource resource = new MediaResource();
        ObjectMapper mapper = new ObjectMapper();
        Disc disc = new Disc("valid", "0000000000000", "valid", 16);
        
        String actual = resource.createDisc(mapper.writeValueAsString(disc).replaceAll("\"", "'"));
        assertTrue(actual.startsWith("400"));
        assertTrue(actual.contains(MediaServiceResult.JsonInvalid.getStatus()));
        
    }
    
    @Test
    public void createDisc_notADisc() throws JsonProcessingException {
        MediaResource resource = new MediaResource();
        ObjectMapper mapper = new ObjectMapper();
        Book book = new Book("valid", "valid", "000-0-00-000000-0");
        
        String actual = resource.createDisc(mapper.writeValueAsString(book));
        assertTrue(actual.startsWith("400"));
        assertTrue(actual.contains(MediaServiceResult.NotADisc.getStatus()));
    }
    
    //getBooks----------------------------------------------------------------
    @Test
    public void getBooks_emptyList() throws JsonProcessingException {
        MediaResource resource = new MediaResource();
        String actual = resource.getBooks();
        String expected = "200 OK\r\n\r\n[\r\n]\r\n\r\n";
        assertEquals(expected, actual);
    }
    
    //getDiscs----------------------------------------------------------------
    @Test
    public void getDiscs_emptyList() throws JsonProcessingException {
        MediaResource resource = new MediaResource();
        String actual = resource.getDiscs();
        String expected = "200 OK\r\n\r\n[\r\n]\r\n\r\n";
        assertEquals(expected, actual);
    }
    
    //getBook-----------------------------------------------------------------
    @Test
    public void getBook_standardCase() throws JsonProcessingException {
        MediaResource resource = new MediaResource();
        ObjectMapper mapper = new ObjectMapper();
        Book book = new Book("valid", "valid", "000-0-00-000000-0");
        
        String actual = resource.createBook(mapper.writeValueAsString(book));
        assertTrue(actual.startsWith("200"));
        
        actual = resource.getBook(book.getIsbn());
        String expected = "200 OK\r\n\r\n" + mapper.writeValueAsString(book) + "\r\n\r\n";
        assertEquals(expected, actual);
    }
    
    @Test
    public void getBook_isbnNotFound() throws JsonProcessingException {
        MediaResource resource = new MediaResource();
        
        String actual = resource.getBook("000-0-00-000000-0");
        assertTrue(actual.startsWith("400"));
        assertTrue(actual.contains(MediaServiceResult.IsbnNotFound.getStatus()));
    }
    
    //getDisc-----------------------------------------------------------------
    @Test
    public void getDisc_standardCase() throws JsonProcessingException {
        MediaResource resource = new MediaResource();
        ObjectMapper mapper = new ObjectMapper();
        Disc disc = new Disc("valid", "0000000000000", "valid", 16);
        
        String actual = resource.createDisc(mapper.writeValueAsString(disc));
        assertTrue(actual.startsWith("200"));
        
        actual = resource.getDisc(disc.getBarcode());
        String expected = "200 OK\r\n\r\n" + mapper.writeValueAsString(disc) + "\r\n\r\n";
        assertEquals(expected, actual);
    }
    
    @Test
    public void getDisc_isbnNotFound() throws JsonProcessingException {
        MediaResource resource = new MediaResource();
        
        String actual = resource.getDisc("0000000000000");
        assertTrue(actual.startsWith("400"));
        assertTrue(actual.contains(MediaServiceResult.BarcodeNotFound.getStatus()));
    }
    
    //updateBook------------------------------------------------------------------
    @Test
    public void updateBook_validInput() throws JsonProcessingException {
        MediaResource resource = new MediaResource();
        ObjectMapper mapper = new ObjectMapper();
        Book oldBook = new Book("old1", "old2", "000-0-00-000000-0");
        Book newBook = new Book("new1", null, "000-0-00-000000-0");
        Book merged = new Book("new1", "old2", "000-0-00-000000-0");
        
        String actual = resource.createBook(mapper.writeValueAsString(oldBook));
        assertTrue(actual.startsWith("200"));
        
        actual = resource.updateBook(newBook.getIsbn(), mapper.writeValueAsString(newBook));
        String expected = "200 OK\r\n\r\n";
        assertEquals(expected, actual);
        
        actual = resource.getBook(merged.getIsbn());
        assertTrue(actual.contains(mapper.writeValueAsString(merged)));
    }
    
    @Test
    public void updateBook_isbnModification() throws JsonProcessingException {
        MediaResource resource = new MediaResource();
        ObjectMapper mapper = new ObjectMapper();
        Book oldBook = new Book("old1", "old2", "000-0-00-000000-0");
        Book newBook = new Book("new1", null, "100-0-00-000000-9");
        
        String actual = resource.createBook(mapper.writeValueAsString(oldBook));
        assertTrue(actual.startsWith("200"));
        
        actual = resource.updateBook(oldBook.getIsbn(), mapper.writeValueAsString(newBook));
        assertTrue(actual.startsWith("400"));
        assertTrue(actual.contains(MediaServiceResult.IsbnModification.getStatus()));
    }
    
    @Test
    public void updateBook_invalidJson() throws JsonProcessingException {
        MediaResource resource = new MediaResource();
        ObjectMapper mapper = new ObjectMapper();
        Book oldBook = new Book("old1", "old2", "000-0-00-000000-0");
        Book newBook = new Book("new1", null, "000-0-00-000000-0");
        
        String actual = resource.createBook(mapper.writeValueAsString(oldBook));
        assertTrue(actual.startsWith("200"));
        
        actual = resource.updateBook(newBook.getIsbn(), mapper.writeValueAsString(newBook).replaceAll("\"", "-"));
        assertTrue(actual.startsWith("400"));
        assertTrue(actual.contains(MediaServiceResult.JsonInvalid.getStatus()));
    }
    
    @Test
    public void updateBook_notABook() throws JsonProcessingException {
        MediaResource resource = new MediaResource();
        ObjectMapper mapper = new ObjectMapper();
        Book oldBook = new Book("old1", "old2", "000-0-00-000000-0");
        Disc newDisc = new Disc("new1", "0000000000000", null, -1);
        
        String actual = resource.createBook(mapper.writeValueAsString(oldBook));
        assertTrue(actual.startsWith("200"));
        
        actual = resource.updateBook(newDisc.getBarcode(), mapper.writeValueAsString(newDisc));
        assertTrue(actual.startsWith("400"));
        assertTrue(actual.contains(MediaServiceResult.NotABook.getStatus()));
    }
    
    //updateDisc------------------------------------------------------------------
        @Test
        public void updateDisc_validInput() throws JsonProcessingException {
            MediaResource resource = new MediaResource();
            ObjectMapper mapper = new ObjectMapper();
            Disc oldDisc = new Disc("old1", "0000000000000", "old2", 16);
            Disc newDisc = new Disc("new1", "0000000000000", null, -1);
            Disc merged = new Disc("new1", "0000000000000", "old2", 16);
            
            String actual = resource.createDisc(mapper.writeValueAsString(oldDisc));
            assertTrue(actual.startsWith("200"));
            
            actual = resource.updateDisc(newDisc.getBarcode(), mapper.writeValueAsString(newDisc));
            String expected = "200 OK\r\n\r\n";
            assertEquals(expected, actual);
            
            actual = resource.getDisc(merged.getBarcode());
            assertTrue(actual.contains(mapper.writeValueAsString(merged)));
        }
        
        @Test
        public void updateDisc_barcodeModification() throws JsonProcessingException {
            MediaResource resource = new MediaResource();
            ObjectMapper mapper = new ObjectMapper();
            Disc oldDisc = new Disc("old1", "0000000000000", "old2", 16);
            Disc newDisc = new Disc("new1", "1000000000009", null, -1);
            
            String actual = resource.createDisc(mapper.writeValueAsString(oldDisc));
            assertTrue(actual.startsWith("200"));
            
            actual = resource.updateDisc(oldDisc.getBarcode(), mapper.writeValueAsString(newDisc));
            assertTrue(actual.startsWith("400"));
            assertTrue(actual.contains(MediaServiceResult.BarcodeModification.getStatus()));
        }
        
        @Test
        public void updateDisc_invalidJson() throws JsonProcessingException {
            MediaResource resource = new MediaResource();
            ObjectMapper mapper = new ObjectMapper();
            Disc oldDisc = new Disc("old1", "0000000000000", "old2", 16);
            Disc newDisc = new Disc("new1", "0000000000000", null, -1);
            
            String actual = resource.createDisc(mapper.writeValueAsString(oldDisc));
            assertTrue(actual.startsWith("200"));
            
            actual = resource.updateDisc(newDisc.getBarcode(), mapper.writeValueAsString(newDisc).replaceAll("\"", "-"));
            assertTrue(actual.startsWith("400"));
            assertTrue(actual.contains(MediaServiceResult.JsonInvalid.getStatus()));
        }
        
        @Test
        public void updateDisc_notADisc() throws JsonProcessingException {
            MediaResource resource = new MediaResource();
            ObjectMapper mapper = new ObjectMapper();
            Disc oldDisc = new Disc("old1", "0000000000000", "old2", 16);
            Book newBook = new Book("new1", null, "000-0-00-000000-0");
            
            String actual = resource.createDisc(mapper.writeValueAsString(oldDisc));
            assertTrue(actual.startsWith("200"));
            
            actual = resource.updateDisc(newBook.getIsbn(), mapper.writeValueAsString(newBook));
            assertTrue(actual.startsWith("400"));
            assertTrue(actual.contains(MediaServiceResult.NotADisc.getStatus()));
        }
    
}
