
package model;

import cst8218.assignment2.entity.Sprite;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
/*
 * SpriteTest class is test cases for the Sprite class
 * Author: Hoang Do, Minh Duc
 * Version: 1.1
 */

@RunWith(MockitoJUnitRunner.class)
public class SpriteTest {
    
    Sprite sprite;
    
    @Before
    public void setUp() throws Exception {
        sprite = new Sprite();
    }
    
    /**
     * Testing if the sprite has moved after calling move method
     */
     @Test
     public void testMove() {
         Sprite currentSprite = setSprite();
         Sprite expectedSprite = setSprite();
         expectedSprite.setX(20);
         expectedSprite.setY(20); 
         
         currentSprite.move();
                 
         assertEquals(currentSprite, expectedSprite);
     }
     
     /**
      * Testing bounce top wall, this test the ySpeed if it's changed after calling method
      */
     @Test
     public void testBounceTopWall() {
         Sprite currentSprite = setSprite();
         Sprite expectedSprite = setSprite();
         expectedSprite.setySpeed(0);
         
         currentSprite.bounceTopWall();
                 
         assertEquals(currentSprite, expectedSprite);
     }
     
     /**
      * Testing bounce bottom wall, this test ySpeed changed after hit bottom wall
      */
     @Test
     public void testBounceBottomWall() {
         Sprite currentSprite = setSprite();
         Sprite expectedSprite = setSprite();
         expectedSprite.setySpeed(0);
         
         currentSprite.bounceBottomWall();
                 
         assertEquals(currentSprite, expectedSprite);
     }
     
     /**
      * Testing bounce right wall, this test xSpeed changed after hit right wall
      */
     @Test
     public void testBounceRightWall() {
         Sprite currentSprite = setSprite();
         Sprite expectedSprite = setSprite();
         expectedSprite.setxSpeed(0);
         
         currentSprite.bounceLeftWall();
                 
         assertEquals(currentSprite, expectedSprite);
     }
     
     /**
      * Testing bounce left wall, this test xSpeed changed after hit left wall
      */
     @Test
     public void testBounceLeftWall() {
         Sprite currentSprite = setSprite();
         Sprite expectedSprite = setSprite();
         expectedSprite.setxSpeed(0);
         
         currentSprite.bounceRightWall();
                 
         assertEquals(currentSprite, expectedSprite);
     }
     
     /**
      * Testing update method used for POST edit with the changed Y
      */
     @Test
     public void testUpdateWithChangedY() {
         Sprite oldSprite = setSprite();
         Sprite newSprite = setSprite();
         newSprite.setY(50);
         
         Sprite expectedSprite = setSprite();
         expectedSprite.setY(50);
         
         newSprite.updates( oldSprite );
                 
         assertEquals(newSprite, expectedSprite);
     }
     
     /**
      * Helper method
      */
     
     /**
      * This is setup class for setting sprite within this class
      * @return 
      */
     private Sprite setSprite(){
         Sprite sprite = new Sprite();
         sprite.setId(1L);
         sprite.setX(10);
         sprite.setY(10);
         sprite.setxSpeed(10);
         sprite.setySpeed(10);
         
         return sprite;
     }
}
