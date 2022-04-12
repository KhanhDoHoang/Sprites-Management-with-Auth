
package test;

import facade.GameSessionTest;
import facade.SpriteFacadeRESTJUnitTest;
import model.SpriteTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/*
 * JUnitTestSuit is a class to call all tests cases
 * Author: Hoang Do, Minh Duc
 */

//JUnit Suite Test
@RunWith(Suite.class)
@Suite.SuiteClasses({
    SpriteFacadeRESTJUnitTest.class, 
    SpriteTest.class, 
    GameSessionTest.class
})
public class JUnitTestSuite {
    
}
