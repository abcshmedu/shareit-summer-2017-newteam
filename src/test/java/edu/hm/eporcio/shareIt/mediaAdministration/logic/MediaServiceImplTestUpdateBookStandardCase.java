package edu.hm.eporcio.shareIt.mediaAdministration.logic;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.hm.eporcio.shareIt.mediaAdministration.access.Book;
import edu.hm.eporcio.shareIt.mediaAdministration.access.Disc;
import edu.hm.eporcio.shareIt.mediaAdministration.access.Book;

@RunWith(Parameterized.class)
public class MediaServiceImplTestUpdateBookStandardCase {
	
	private final Book toUpdate;
	private final Book updated;
	private final Book expectedMerged;
	private final MediaServiceResult expectedResult;
	
	
	public MediaServiceImplTestUpdateBookStandardCase(Book toUpdate, Book updated, Book merged, MediaServiceResult expectedResult) {
		this.toUpdate = toUpdate;
		this.updated = updated;
		this.expectedMerged = merged;
		this.expectedResult = expectedResult;
	}

	@Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]
        {
        	//Update book--------------------------------------------
        	//valid input
        	{
	        	new Book("old1", "old2", "000-0-00000-000-0"),
	        	new Book("new1", "new2", "000-0-00000-000-0"),
	        	new Book("new1", "new2", "000-0-00000-000-0"),
	        	MediaServiceResult.Ok
        	},
        	
        	{
            	new Book("old1", "old2", "000-0-00000-000-0"),
            	new Book("new1", null, "000-0-00000-000-0"),
            	new Book("new1", "old2", "000-0-00000-000-0"),
            	MediaServiceResult.Ok
            },
        	
        	{
            	new Book("old1", "old2", "000-0-00000-000-0"),
            	new Book("", "new2", "000-0-00000-000-0"),
            	new Book("old1", "new2", "000-0-00000-000-0"),
            	MediaServiceResult.Ok
            },
        	
        	//Isbn not found
        	{
	        	new Book("old1", "old2", "000-0-00000-000-0"),
	        	new Book("new1", "new2", "111-1-11111-111-6"),
	        	new Book("old1", "old2", "000-0-00000-000-0"),
	        	MediaServiceResult.IsbnNotFound
        	},
        	
        	//nothing to modify
        	{
	        	new Book("old1", "old2", "000-0-00000-000-0"),
	        	new Book(null, null, "000-0-00000-000-0"),
	        	new Book("old1", "old2", "000-0-00000-000-0"),
	        	MediaServiceResult.NothingToModify
        	},
        	
        	{
	        	new Book("old1", "old2", "000-0-00000-000-0"),
	        	new Book("", "", "000-0-00000-000-0"),
	        	new Book("old1", "old2", "000-0-00000-000-0"),
	        	MediaServiceResult.NothingToModify
        	},
        	
        	{
	        	new Book("old1", "old2", "000-0-00000-000-0"),
	        	new Book(null, "", "000-0-00000-000-0"),
	        	new Book("old1", "old2", "000-0-00000-000-0"),
	        	MediaServiceResult.NothingToModify
        	}
        	
        });
    }

	@Test
	public void addBook() {
		MediaServiceResult result;
		MediaService service = new MediaServiceImpl();
				
		result = service.addBook(toUpdate);
		assertEquals(MediaServiceResult.Ok, result);
		
		result = service.updateBook(updated);
		assertEquals(expectedResult, result);
		
		Book actualMerged = service.getBook(toUpdate.getIsbn());
		assertEquals(expectedMerged, actualMerged);
	}

}
