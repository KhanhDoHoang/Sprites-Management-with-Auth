/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import com.mycompany.spritehoang.facade.SpriteFacadeREST;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Khanh Do
 */
public class SpriteFacadeRESTJUnitTest {
    
    private SpriteFacadeREST spriteFacade;
    
//    public SpriteFacadeRESTJUnitTest() {
//    }

    @BeforeEach
    public void setUp() throws Exception {
        spriteFacade = new SpriteFacadeREST();
    }

     @Test
     public void testSpritesAreFound() {
         //Mockito
     }
     
     @Test
     public void testSpriteIsFound() {
         
     }
    
     @Test
     public void testSpriteIsNotFound() {
         
     }
    
    
     @Test
     public void testCreateNewValidSprite() {

     }
     
     @Test
     public void testCreateNewInvalidSprite() {

     }
     
     @Test
     public void testEditValidSpriteforPost() {

     }
     
     @Test
     public void testEditInvalidSpriteWithMismatchedIdforPost() {

     }
     
     @Test
     public void testEditInvalidSpriteWithUnfoundIdforPost() {

     }
     
     @Test
     public void testEditInvalidSpriteWithNegativeXforPost() {

     }
     
     @Test
     public void testEditInvalidSpriteWithNegativeYforPost() {

     }
     
     @Test
     public void testEditValidSpriteforPut() {

     }
     
     @Test
     public void testValidSpriteRemoval() {

     }
     
     @Test
     public void testInvalidSpriteRemoval() {

     }
}
