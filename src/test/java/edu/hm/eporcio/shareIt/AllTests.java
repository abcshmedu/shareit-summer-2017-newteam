package edu.hm.eporcio.shareIt;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.hm.eporcio.shareIt.mediaAdministration.API.MediaResourceTest;
import edu.hm.eporcio.shareIt.mediaAdministration.API.ResponseBuilderTest;
import edu.hm.eporcio.shareIt.mediaAdministration.access.BookTestEquals;
import edu.hm.eporcio.shareIt.mediaAdministration.access.BookTestMerge;
import edu.hm.eporcio.shareIt.mediaAdministration.access.DiscTestEquals;
import edu.hm.eporcio.shareIt.mediaAdministration.access.DiscTestMerge;
import edu.hm.eporcio.shareIt.mediaAdministration.access.MediumTest;
import edu.hm.eporcio.shareIt.mediaAdministration.logic.MediaServiceImplTestAddMediumStandardCase;
import edu.hm.eporcio.shareIt.mediaAdministration.logic.MediaServiceImplTestNonStandardCases;
import edu.hm.eporcio.shareIt.mediaAdministration.logic.MediaServiceImplTestUpdateBookStandardCase;
import edu.hm.eporcio.shareIt.mediaAdministration.logic.MediaServiceImplTestUpdateDiscStandardCase;

@RunWith(Suite.class)
@SuiteClasses({
	BookTestEquals.class,
	DiscTestEquals.class,
	BookTestMerge.class,
	DiscTestMerge.class,
	MediumTest.class,
	
	MediaServiceImplTestUpdateBookStandardCase.class,
	MediaServiceImplTestUpdateDiscStandardCase.class,
	MediaServiceImplTestAddMediumStandardCase.class,
	MediaServiceImplTestNonStandardCases.class,
	
	ResponseBuilderTest.class,
	MediaResourceTest.class
})
public class AllTests {

}
