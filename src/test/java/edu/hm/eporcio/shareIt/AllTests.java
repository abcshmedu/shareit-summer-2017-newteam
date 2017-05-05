package edu.hm.eporcio.shareIt;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.hm.eporcio.shareIt.mediaAdministration.API.MediaResourceTest;
import edu.hm.eporcio.shareIt.mediaAdministration.API.ResponseBuilderTest;
import edu.hm.eporcio.shareIt.mediaAdministration.access.BookEqualsTest;
import edu.hm.eporcio.shareIt.mediaAdministration.access.BookMergeTest;
import edu.hm.eporcio.shareIt.mediaAdministration.access.DiscEqualsTest;
import edu.hm.eporcio.shareIt.mediaAdministration.access.DiscMergeTest;
import edu.hm.eporcio.shareIt.mediaAdministration.access.MediumMergeTest;
import edu.hm.eporcio.shareIt.mediaAdministration.logic.MediaServiceImplAddMediumStandardCaseTest;
import edu.hm.eporcio.shareIt.mediaAdministration.logic.MediaServiceImplNonStandardCasesTest;
import edu.hm.eporcio.shareIt.mediaAdministration.logic.MediaServiceImplUpdateBookStandardCaseTest;
import edu.hm.eporcio.shareIt.mediaAdministration.logic.MediaServiceImplUpdateDiscStandardCaseTest;

/**
 * Runs all tests.
 * @author Elias Porcio, elias.porcio@hm.edu
 * @version May 4, 2017
 */
@RunWith(Suite.class)
@SuiteClasses({
    BookEqualsTest.class,
    DiscEqualsTest.class,
    BookMergeTest.class,
    DiscMergeTest.class,
    MediumMergeTest.class,
    
    MediaServiceImplUpdateBookStandardCaseTest.class,
    MediaServiceImplUpdateDiscStandardCaseTest.class,
    MediaServiceImplAddMediumStandardCaseTest.class,
    MediaServiceImplNonStandardCasesTest.class,
    
    ResponseBuilderTest.class,
    MediaResourceTest.class
})
public class AllTests {

}
