package edu.hm.eporcio.shareIt.mediaAdministration.logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.hm.eporcio.shareIt.mediaAdministration.access.Book;
import edu.hm.eporcio.shareIt.mediaAdministration.access.Disc;

public class MediaServiceImplTestNonStandardCases {
	
	private MediaService service;
	private final Book validBook = new Book ("valid", "valid", "000-0-00000-000-0");
	private final Disc validDisc = new Disc ("valid", "0000000000000", "valid", 16);
	
	@Before
	public void setUp() {
		service = new MediaServiceImpl();
	}
	

	@Test
	public void getBook() {
		service.addBook(validBook);
		Book actual = service.getBook(validBook.getIsbn());
		assertEquals(validBook, actual);
	}
	
	@Test
	public void getDisc() {
		service.addDisc(validDisc);
		Disc actual = service.getDisc(validDisc.getBarcode());
		assertEquals(validDisc, actual);
	}
	
	@Test
	public void getBooks_emptyList() {
		Book[] actual = service.getBooks();
		assertTrue(actual.length == 0);
	}
	
	@Test
	public void getDiscs_emptyList() {
		Disc[] actual = service.getDiscs();
		assertTrue(actual.length == 0);
	}
	
	@Test
	public void AddTwoBooksSameIsbn() {
		service.addBook(new Book("valid", "valid", "000-0-00000-000-0"));
		MediaServiceResult actual = service.addBook(new Book("valid", "valid", "000-0-00000-000-0"));
		assertEquals(MediaServiceResult.IsbnAlreadyKnown, actual);
	}
	
	@Test
	public void AddTwoDiscsSameBarcode() {
		service.addDisc(new Disc("valid", "0000000000000", "valid", 16));
		MediaServiceResult actual = service.addDisc(new Disc("valid", "0000000000000", "valid", 16));
		assertEquals(MediaServiceResult.BarcodeAlreadyKnown, actual);
	}
}
