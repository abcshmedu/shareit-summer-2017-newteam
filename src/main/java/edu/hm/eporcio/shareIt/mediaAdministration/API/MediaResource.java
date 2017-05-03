package edu.hm.eporcio.shareIt.mediaAdministration.API;

import java.io.IOException;
import java.util.Arrays;

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
import edu.hm.eporcio.shareIt.mediaAdministration.access.Disc;
import edu.hm.eporcio.shareIt.mediaAdministration.access.Medium;
import edu.hm.eporcio.shareIt.mediaAdministration.logic.MediaServiceImpl;
import edu.hm.eporcio.shareIt.mediaAdministration.logic.MediaServiceResult;

/**
 * Definition of the available URIs.
 * 
 * @author Elias Porcio elias.porcio@hm.edu
 * @version 03.05.2017
 */
@Path("/media")
public class MediaResource {
    
    private static final MediaServiceImpl SERVICE = new MediaServiceImpl();
    
    /**
     * Add a new book to the knownBooks list.
     * @param bookJson A JSON representation of the book to add.
     * @return An HTTP response indicating success or errors.
     */
    @POST
    @Path("/books")
    @Consumes("application/JSON")
    public String createBook(String bookJson) {
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            Book book = mapper.readValue(bookJson, Book.class);
            
            MediaServiceResult result = SERVICE.addBook(book);
            if (result == MediaServiceResult.Ok) {
                return ResponseBuilder.status(result.getCode(), result.getHttpStatus()).build();
            }
            else {
                return fail(result);
            }
            
        } catch (IOException exeption) {
            if (exeption instanceof JsonParseException) {
                return fail(MediaServiceResult.JsonInvalid);
            }
            if (exeption instanceof JsonMappingException) {
                return fail(MediaServiceResult.NotABook);
            }
            return fail(MediaServiceResult.InternalServerError);    //return generic error response for unknown error
        }
    }
    
    /**
     * Add a new disc to the knownDiscs list.
     * @param discJson A JSON representation of the disc to add.
     * @return An HTTP response indicating success or errors.
     */
    @POST
    @Path("/discs")
    @Consumes("application/JSON")
    public String createDisc(String discJson) {
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            Disc disc = mapper.readValue(discJson, Disc.class);
            
            MediaServiceResult result = SERVICE.addDisc(disc);
            if (result == MediaServiceResult.Ok) {
                return ResponseBuilder.status(result.getCode(), result.getHttpStatus()).build();
            }
            else {
                return fail(result);
            }
            
        } catch (IOException exeption) {
            if (exeption instanceof JsonParseException) {
                return fail(MediaServiceResult.JsonInvalid);
            }
            if (exeption instanceof JsonMappingException) {
                return fail(MediaServiceResult.NotADisc);
            }
            return fail(MediaServiceResult.InternalServerError);    //return generic error response for unknown error
        }
    }
    
    /**
     * Get a list of all books known to the system.
     * @return An HTTP response with a JSON list of books in the body.
     * @throws JsonProcessingException When a Jackson wrapping problem occurs.
     */
    @GET
    @Path("/books")
    public String getBooks() throws JsonProcessingException {
            return ResponseBuilder
                    .status(MediaServiceResult.Ok.getCode(), MediaServiceResult.Ok.getStatus())
                    .addList(Arrays.asList(SERVICE.getBooks()))
                    .build();
    }
    
    /**
     * Get a list of all discs known to the system.
     * @return An HTTP response with a JSON list of discs in the body.
     * @throws JsonProcessingException When a Jackson wrapping problem occurs.
     */
    @GET
    @Path("/discs")
    public String getDiscs() throws JsonProcessingException {
            return ResponseBuilder
                    .status(MediaServiceResult.Ok.getCode(), MediaServiceResult.Ok.getStatus())
                    .addList(Arrays.asList(SERVICE.getDiscs()))
                    .build();
    }
    
    /**
     * Get a specific book by ISBN.
     * @param isbn The ISBN of the book to get
     * @return An HTTP response - with a book object in the body, if successful.
     * @throws JsonProcessingException When a Jackson wrapping problem occurs.
     */
    @GET
    @Path("/books/{isbn}")
    public String getBook(@PathParam("isbn")String isbn) throws JsonProcessingException {
        Medium book = SERVICE.getBook(isbn);
        if (book == null) {
            return fail(MediaServiceResult.IsbnNotFound);
        }
        else {
            return ResponseBuilder
                    .status(MediaServiceResult.Ok.getCode(), MediaServiceResult.Ok.getStatus())
                    .addObject(book)
                    .build();
        }
    }
    
    /**
     * Get a specific disc by its barcode.
     * @param barcode The barcode of the disc to get
     * @return An HTTP response - with a disc object in the body, if successful.
     * @throws JsonProcessingException When a Jackson wrapping problem occurs.
     */
    @GET
    @Path("/discs/{barcode}")
    public String getDisc(@PathParam("barcode")String barcode) throws JsonProcessingException {
        Medium disc = SERVICE.getDisc(barcode);
        if (disc == null) {
            return fail(MediaServiceResult.BarcodeNotFound);
        }
        else {
            return ResponseBuilder
                    .status(MediaServiceResult.Ok.getCode(), MediaServiceResult.Ok.getStatus())
                    .addObject(disc)
                    .build();
        }
    }
    
    /**
     * Updates a book.
     * The given books fields may be null or have an escape value if the field is not to be changed. At least one field must be changed. The ISBN cannot be changed.
     * @param isbn The ISBN of the book to be changed. Should match the ISBN in the bookJson parameter
     * @param bookJson A JSON representation of a book containing the changes to be made.
     * @return An HTTP response indicating success or errors.
     */
    @PUT
    @Path("/books")
    public String updateBook(@PathParam("isbn")String isbn, String bookJson) {
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            Book book = mapper.readValue(bookJson, Book.class);
            
            if (!book.getIsbn().equals(isbn)) {
                return fail(MediaServiceResult.IsbnModification);
            }
            
            MediaServiceResult result = SERVICE.updateBook(book);
            if (result == MediaServiceResult.Ok) {
                return ResponseBuilder.status(MediaServiceResult.Ok.getCode(), MediaServiceResult.Ok.getStatus()).build();
            }
            else {
                return fail(result);
            }
            
        } catch (IOException exeption) {
            if (exeption instanceof JsonParseException) {
                return fail(MediaServiceResult.JsonInvalid);
            }
            if (exeption instanceof JsonMappingException) {
                return fail(MediaServiceResult.NotABook);
            }
            return fail(MediaServiceResult.InternalServerError);    //return generic error response for unknown error
        }
    }
    
    /**
     * Updates a disc.
     * The given discs fields may be null or have an escape value if the field is not to be changed. At least one field must be changed. The barcode cannot be changed.
     * @param barcode The barcode of the disc to be changed. Should match the barcode in the discJson parameter
     * @param discJson A JSON representation of a disc containing the changes to be made.
     * @return An HTTP response indicating success or errors.
     */
    @PUT
    @Path("/discs")
    public String updateDisc(@PathParam("barcode")String barcode, String discJson) {
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            Disc disc = mapper.readValue(discJson, Disc.class);
            
            if (!disc.getBarcode().equals(barcode)) {
                return fail(MediaServiceResult.BarcodeModification);
            }
            
            MediaServiceResult result = SERVICE.updateDisc(disc);
            if (result == MediaServiceResult.Ok) {
                return ResponseBuilder.status(MediaServiceResult.Ok.getCode(), MediaServiceResult.Ok.getStatus()).build();
            }
            else {
                return fail(result);
            }
            
        } catch (IOException exeption) {
            if (exeption instanceof JsonParseException) {
                return fail(MediaServiceResult.JsonInvalid);
            }
            if (exeption instanceof JsonMappingException) {
                return fail(MediaServiceResult.NotADisc);
            }
            return fail(MediaServiceResult.InternalServerError);    //return generic error response for unknown error
        }
    }
    
    /**
     * Builds a standard error response from a MediaServiceResult object.
     * @param status The media response error to generate a response from.
     * @return An HTTP error response.
     */
    private String fail(MediaServiceResult status) {
        return ResponseBuilder.status(status.getCode(), status.getHttpStatus())
                .body("code", String.valueOf(status.getCode()))
                .body("detail", status.getStatus())
                .build();
    }
    
}
