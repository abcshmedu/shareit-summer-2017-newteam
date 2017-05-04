package edu.hm.eporcio.shareIt.mediaAdministration.logic;

import java.util.HashMap;
import java.util.Map;

/**
 * The result of a method call of a mediaService.
 * @author Elias Porcio, elias.porcio@hm.edu
 * @version May 4, 2017
 */
public enum MediaServiceResult {
    
    Ok(200, "Ok"),
    NotFound(404, "Not Found"),
    JsonInvalid(400, "Syntax error in JSON body"),
    InternalServerError(500, "Internal Server Error"),
    
    //books
    NotABook(400, "The specified JSON does not represent a book"),
    IsbnNotFound(400, "No book with this ISBN found"),
    IsbnModification(400, "A books ISBN cannot be modified"),
    IsbnAlreadyKnown(400, "There already is a book with this ISBN"),
    IsbnInvalid(400, "ISBN invalid"),
    
    
    //discs
    NotADisc(400, "The specified JSON does not represent a disc"),
    BarcodeNotFound(400, "No disc with this barcode found"),
    BarcodeModification(400, "A discs barcode cannot be modified"),
    BarcodeAlreadyKnown(400, "There already is a disc with this barcode"),
    BarcodeInvalid(400, "Barcode invalid"),
    
    //all media
    MissingField(400, "A field is missing"),
    NothingToModify(400, "No modifiable field specified");
    
    /** The HTTP status code corresponding to the result. */
    private final int code;
    /** An informal explanation about what caused the error. */
    private final String status;
    
    /** Maps the HTTP status code to the corresponding HTTP status message. */
    private final Map<Integer, String> httpStatus;
    
    //CHECKSTYLE:OFF
    MediaServiceResult(int code, String status) {
        this.code = code;
        this.status = status;
        
        httpStatus = new HashMap<>();
        httpStatus.put(200, "OK");
        httpStatus.put(400, "Bad Request");
        httpStatus.put(404, "Not Found");
        httpStatus.put(500, "Internal Server Error");
    }
    
    
    public int getCode() {
        return code;
    }
    
    public String getStatus() {
        return status;
    }
    
    public String getHttpStatus() {
        return httpStatus.get(getCode());
    }
    //CHECKSTYLE:ON
}
