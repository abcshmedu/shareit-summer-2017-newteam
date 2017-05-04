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
import edu.hm.eporcio.shareIt.mediaAdministration.access.MediumTestMerge;
import edu.hm.eporcio.shareIt.mediaAdministration.logic.MediaServiceImplTestAddMediumStandardCase;
import edu.hm.eporcio.shareIt.mediaAdministration.logic.MediaServiceImplTestNonStandardCases;
import edu.hm.eporcio.shareIt.mediaAdministration.logic.MediaServiceImplTestUpdateBookStandardCase;
import edu.hm.eporcio.shareIt.mediaAdministration.logic.MediaServiceImplTestUpdateDiscStandardCase;

/**
 * Runs all tests.
 * @author Elias Porcio, elias.porcio@hm.edu
 * @version May 4, 2017
 */
@RunWith(Suite.class)
@SuiteClasses({
    BookTestEquals.class,
    DiscTestEquals.class,
    BookTestMerge.class,
    DiscTestMerge.class,
    MediumTestMerge.class,
    
    MediaServiceImplTestUpdateBookStandardCase.class,
    MediaServiceImplTestUpdateDiscStandardCase.class,
    MediaServiceImplTestAddMediumStandardCase.class,
    MediaServiceImplTestNonStandardCases.class,
    
    ResponseBuilderTest.class,
    MediaResourceTest.class
})
public class AllTests {

}
