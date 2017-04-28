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
			return fail(SERVICE.addBook(book));
			
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
	public String getBooks() {
		ResponseBuilder response = ResponseBuilder.code(200).status("Ok");
		response.addList(Arrays.asList(SERVICE.getBooks()));
		return response.build();
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
			
			return fail(SERVICE.updateBook(book));
			
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
		return ResponseBuilder.code(status.getCode())
				.status(status.getStatus())
				.body("code", String.valueOf(status.getCode()))
				.body("detail", status.getStatus())
				.build();
	}
	
}
