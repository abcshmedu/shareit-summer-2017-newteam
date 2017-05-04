package edu.hm.eporcio.shareIt.mediaAdministration.API;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.hm.eporcio.shareIt.mediaAdministration.access.Book;

/**
 * Tests for ResponseBuilder class.
 * @author Elias Porcio, elias.porcio@hm.edu
 * @version May 4, 2017
 */
public class ResponseBuilderTest {

    @Test
    public void status_standard() {
        String actual = ResponseBuilder.status(200, "OK").build();
        String expected = "200 OK\r\n\r\n";
        assertEquals(expected, actual);
    }
    
    @Test
    public void status_emptyString() {
        String actual = ResponseBuilder.status(200, "").build();
        String expected = "200 \r\n\r\n";
        assertEquals(expected, actual);
    }
    
    @Test
    public void addObject_standardBook() throws JsonProcessingException {
        Book book = new Book("valid", "valid", "000-0-00-000000-0");
        String actual = ResponseBuilder.status(200, "OK").addObject(book).build();
        ObjectMapper mapper = new ObjectMapper();
        String expected = "200 OK\r\n\r\n" + mapper.writeValueAsString(book) + "\r\n\r\n";
        assertEquals(expected, actual);
    }
    
    @Test
    public void addObject_null() throws JsonProcessingException {
        String actual = ResponseBuilder.status(200, "OK").addObject(null).build();
        String expected = "200 OK\r\n\r\n";
        assertEquals(expected, actual);
    }
    
    @Test
    public void addList_standardBooklist() throws JsonProcessingException {
        Book book1 = new Book("valid1", "valid1", "000-0-00-000000-0");
        Book book2 = new Book("valid2", "valid2", "100-0-00-000000-9");
        Collection<Object> list = new ArrayList<>();
        list.add(book1);
        list.add(book2);
        
        String actual = ResponseBuilder.status(200, "OK").addList(list).build();
        
        ObjectMapper mapper = new ObjectMapper();
        String expected = "200 OK\r\n\r\n[\r\n" + mapper.writeValueAsString(book1) + ",\r\n" + mapper.writeValueAsString(book2) + "\r\n]" + "\r\n\r\n";
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void addList_null() throws JsonProcessingException {        
        String actual = ResponseBuilder.status(200, "OK").addList(null).build();
        String expected = "200 OK\r\n\r\n";
        assertEquals(expected, actual);
    }
    
    @Test
    public void addList_emptyList() throws JsonProcessingException {
        Collection<Object> list = new ArrayList<>();
        String actual = ResponseBuilder.status(200, "OK").addList(list).build();
        String expected = "200 OK\r\n\r\n[\r\n]\r\n\r\n";
        assertEquals(expected, actual);
    }
}
