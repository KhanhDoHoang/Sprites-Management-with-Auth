/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import cst8218.assignment2.entity.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Khanh Do
 */
public class SpriteTest {
    
//    public SpriteTest() {
//    }
    
    Sprite sprite;
    
    @BeforeEach
    public void setUp() throws Exception {
        sprite = new Sprite();
    }
    
     @Test
     public void testMove() {
         Sprite currentSprite = setSprite();
         Sprite expectedSprite = setSprite();
         expectedSprite.setX(20);
         expectedSprite.setY(20); 
         
         currentSprite.move();
                 
         assertEquals(currentSprite, expectedSprite);
     }
     
     @Test
     public void testBounceTopWall() {
         Sprite currentSprite = setSprite();
         Sprite expectedSprite = setSprite();
         expectedSprite.setySpeed(0);
         
         currentSprite.bounceTopWall();
                 
         assertEquals(currentSprite, expectedSprite);
     }
     
     @Test
     public void testBounceBottomWall() {
         Sprite currentSprite = setSprite();
         Sprite expectedSprite = setSprite();
         expectedSprite.setySpeed(0);
         
         currentSprite.bounceBottomWall();
                 
         assertEquals(currentSprite, expectedSprite);
     }
     
     @Test
     public void testBounceRightWall() {
         Sprite currentSprite = setSprite();
         Sprite expectedSprite = setSprite();
         expectedSprite.setxSpeed(0);
         
         currentSprite.bounceLeftWall();
                 
         assertEquals(currentSprite, expectedSprite);
     }
     
     @Test
     public void testBounceLeftWall() {
         Sprite currentSprite = setSprite();
         Sprite expectedSprite = setSprite();
         expectedSprite.setxSpeed(0);
         
         currentSprite.bounceRightWall();
                 
         assertEquals(currentSprite, expectedSprite);
     }
     
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
