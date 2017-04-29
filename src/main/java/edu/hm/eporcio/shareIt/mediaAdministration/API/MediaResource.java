package edu.hm.eporcio.shareIt.mediaAdministration.API;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.hm.eporcio.shareIt.mediaAdministration.access.Book;
import edu.hm.eporcio.shareIt.mediaAdministration.access.Medium;
import edu.hm.eporcio.shareIt.mediaAdministration.logic.MediaServiceImpl;
import edu.hm.eporcio.shareIt.mediaAdministration.logic.MediaServiceResult;

@Path("/media")
public class MediaResource {
	
	private static final MediaServiceImpl SERVICE = new MediaServiceImpl();
	
	@POST
	@Path("/books")
	@Consumes("application/JSON")
	public String createBook(String bookJson) {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			Book book = mapper.readValue(bookJson, Book.class);
			
			MediaServiceResult result = SERVICE.addBook(book);
			if(result == MediaServiceResult.Ok) {
				return ResponseBuilder.status(200, "OK").build();
			}
			else {
				return fail(result);
			}
			
		} catch (IOException exeption) {
			if (exeption.getClass() == JsonParseException.class) {
				return fail(MediaServiceResult.JsonInvalid);
			}
			if (exeption.getClass() == JsonMappingException.class) {
				return fail(MediaServiceResult.NotABook);
			}
			return fail(MediaServiceResult.InternalServerError);	//return generic error response for unknown error
		}
	}
	
	@GET
	@Path("/books")
	public String getBooks() throws JsonProcessingException {
			return ResponseBuilder.status(200, "OK").addList(Arrays.asList(SERVICE.getBooks())).build();
	}
	
	@GET
	@Path("/books/{isbn}")
	public String getBooks(@PathParam("isbn")String isbn) throws JsonProcessingException {
		Medium book = SERVICE.getBook(isbn);
		if(book == null) {
			return fail(MediaServiceResult.IsbnNotFound);
		}
		else {
			return ResponseBuilder.status(200, "OK").addObject(book).build();
		}
	}
	
	@PUT
	@Path("/books")
	public String updateBook(@PathParam("isbn")String isbn, String bookJson) {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			Book book = mapper.readValue(bookJson, Book.class);
			
			if(book.getIsbn() != isbn) {
				fail(MediaServiceResult.IsbnModification);
			}
			
			MediaServiceResult result = SERVICE.updateBook(book);
			if(result == MediaServiceResult.Ok) {
				return ResponseBuilder.status(200, "OK").build();
			}
			else {
				return fail(result);
			}
			
		} catch (IOException exeption) {
			if (exeption.getClass() == JsonParseException.class) {
				return fail(MediaServiceResult.JsonInvalid);
			}
			if (exeption.getClass() == JsonMappingException.class) {
				return fail(MediaServiceResult.NotABook);
			}
			return fail(MediaServiceResult.InternalServerError);	//return generic error response for unknown error
		}
	}
	
	private String fail(MediaServiceResult status) {
		return ResponseBuilder.status(status.getCode(), status.getHttpStatus())
				.body("code", String.valueOf(status.getCode()))
				.body("detail", status.getStatus())
				.build();
	}
	
}
