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
	
	/** A list of all response elements in order. Will be concatenated to a String. */
	private List<String> elements = new ArrayList<>();
	/** True if a status has already been set. */
	private boolean statusExists = false;
	/** True if the response already has a body */
	private boolean bodyExists = false;
	
	
	private ResponseBuilder() {}
	
	/**
	 * Creates a new ResponseBuilder with the given status code.
	 * @param code The status code of the HTTP response.
	 * @return A new ResponseBuilder with the given status code.
	 */
	public static ResponseBuilder code(int code) {
		ResponseBuilder toReturn = new ResponseBuilder();
		toReturn.elements.add(Integer.toString(code));
		toReturn.elements.add("\r\n");
		return toReturn;
	}
	
	/**
	 * Adds a status message or overwrites the old one.
	 * @param message The new status message.
	 * @return This ResponseBuilder with new or altered status message.
	 */
	public ResponseBuilder status(String message) {
		if(statusExists) {
			elements.set(1, " " + message);	//overwrite old status
		}
		else {
			elements.add(1, " " + message);
			statusExists = true;
		}
		return this;
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
		}
		
			elements.add("[\r\n");
			for (Object listElement : toAdd) {
				elements.add(mapper.writeValueAsString(listElement));
				elements.add(",\r\n");
			}
			elements.add(elements.size() - 1, "]");
			elements.add(",\r\n");
		return this;
	}
	
	/**
	 * Build the current state of the ResponseBuilder to a response String.
	 * @return A String representing the response.
	 */
	public String build() {
		if(bodyExists) {
			elements.add("\r\n");	//double newline marks end of response
		}
		
		final StringBuilder response = new StringBuilder();
		elements.forEach(response::append);
		return response.toString();
	}
}
