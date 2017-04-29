package edu.hm.eporcio.shareIt.mediaAdministration.API;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A builder for HTTP responses.
 * The body is of type JSON.
 * @author Elias Porcio
 * @version 27.04.2017
 */
public class ResponseBuilder {
	
	/** A list of all response elements in order. Will be concatenated to a String. Allows more dynamic modification of the response. */
	private List<String> elements = new ArrayList<>();
	/** True if the response already has a body */
	private boolean bodyExists = false;
	
	/** Forces user to use factory method "status". */
	private ResponseBuilder() {}
	
	/**
	 * Creates a new ResponseBuilder with the given status code.
	 * @param code The status code of the HTTP response.
	 * @return A new ResponseBuilder with the given status code.
	 */
	public static ResponseBuilder status(int code, String status) {
		ResponseBuilder toReturn = new ResponseBuilder();
		toReturn.elements.add(Integer.toString(code));
		toReturn.elements.add(1, " " + status);
		toReturn.elements.add("\r\n");
		return toReturn;
	}
	
	/**
	 * Adds a key value pair to the response body.
	 * @param key The key to be added.
	 * @param value The value to be added.
	 * @return This ResponseBuilder with the key value pair added.
	 */
	public ResponseBuilder body(String key, String value) {
		if(!bodyExists) {
			elements.add("\r\n");	//double newline marks start of body
			bodyExists = true;
		}
		elements.add("\"" + key + "\": " + value);
		elements.add(",\r\n");

		return this;
	}
	
	/**
	 * Adds a JSON list of objects to the response body.
	 * @param toAdd A collection containing the objects to add.
	 * @return This ResponseBuilder with the list added.
	 * @throws JsonProcessingException When a Jackson mapping problem occurs.
	 */
	public ResponseBuilder addList(Collection<Object> toAdd) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		if(!bodyExists) {
			elements.add("\r\n");	//double newline marks start of body
			bodyExists = true;
		}
		
			elements.add("[\r\n");
			for (Object listElement : toAdd) {
				elements.add(mapper.writeValueAsString(listElement));
				elements.add(",\r\n");
			}
			elements.set(elements.size() - 1, "\r\n]");
			elements.add(",\r\n");
		return this;
	}
	
	/**
	 * Adds an object to the response body.
	 * @param toAdd The object to add.
	 * @return This ResponseBuilder with the object added.
	 * @throws JsonProcessingException When a Jackson mapping problem occurs.
	 */
	public ResponseBuilder addObject(Object toAdd) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		if(!bodyExists) {
			elements.add("\r\n");	//double newline marks start of body
			bodyExists = true;
		}
		
		elements.add(mapper.writeValueAsString(toAdd));
		elements.add(",\r\n");
				
		return this;
	}
	
	/**
	 * Build the current state of the ResponseBuilder to a response String.
	 * @return A String representing the response.
	 */
	public String build() {
		if(bodyExists) {
			elements.set(elements.size() - 1, "\r\n\r\n");	//double newline marks end of response
		}
		else {
			elements.add("\r\n");
		}
		
		final StringBuilder response = new StringBuilder();
		elements.forEach(response::append);
		return response.toString();
	}
}
