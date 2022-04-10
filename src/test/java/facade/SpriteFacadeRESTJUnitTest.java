/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import com.mycompany.spritehoang.facade.SpriteFacadeREST;
import cst8218.assignment2.entity.Sprite;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Khanh Do
 */
@RunWith(MockitoJUnitRunner.class)
public class SpriteFacadeRESTJUnitTest {
    
    private final EntityManager em = Mockito.mock(EntityManager.class);
    
    private final CriteriaQuery criteriaQuery = Mockito.mock(CriteriaQuery.class);
    
    private final CriteriaQuery mockedCQAll = Mockito.mock(CriteriaQuery.class);
    
    private final Root mockedRoot = Mockito.mock(Root.class);
    
    private final CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
    
    private final TypedQuery<Sprite> mockedTQ = Mockito.mock(TypedQuery.class);
    
    private final TypedQuery<Sprite> maxResultQuery = Mockito.mock(TypedQuery.class);
    private final TypedQuery<Sprite> firstResultQuery = Mockito.mock(TypedQuery.class);
    
    private SpriteFacadeREST spriteFacade;
    

    @BeforeEach
    public void setUp() throws Exception {
        spriteFacade = new SpriteFacadeREST();
        spriteFacade.setEntityManager(em);
        
        //CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        when(em.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        //CriteriaQuery eq1 = cb.createQuery(); 
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        //Root rootEntry = eq1.from(Sprite.class); 
        when(criteriaQuery.from(Sprite.class)).thenReturn(mockedRoot);
        //eq1.select(rootEntry);
        when(criteriaQuery.select(any())).thenReturn(mockedCQAll);
        
    }
    
    /**
     * Create
     */
    @Test
    public void testCreateNewValidSpriteWithoutId() {
        Sprite sprite = setSprite(null);
        //super.create(newSprite);
        Mockito.doAnswer(i -> i.getArguments()[0]).when(em).persist(sprite);
        Response responseTest = spriteFacade.createNewSprite(sprite);
        
        verify(em, times(1)).persist(any());

        assertEquals(
          (Response.Status.CREATED).getStatusCode(), responseTest.getStatus());
    }
    
    /**
     * Create
     */
    @Test
    public void testCreateNewValidSpriteWithId() {
        Sprite newSprite = setSprite(1L);
        Sprite oldSprite = setSprite(1L);
        oldSprite.setX(20);
        //super.find(newSprite.getId())
        when(em.find(any(), any())).thenReturn(newSprite);
        
        Response responseTest = spriteFacade.createNewSprite(newSprite);
        Sprite updatedSprite = (Sprite) responseTest.getEntity();
        
        assertEquals(10, updatedSprite.getX());
        assertEquals(
          (Response.Status.OK).getStatusCode(), responseTest.getStatus());
    }
    
    /**
     * Create invalid sprite with null sprite
     */
    @Test
    public void testCreateNewInvalidNullSprite() {
        Response responseTest = spriteFacade.createNewSprite(null);
        
        assertEquals(
          (Response.Status.BAD_REQUEST).getStatusCode(), responseTest.getStatus());
    }
    
    /**
     * Create invalid sprite with null sprite
     */
    @Test
    public void testCreateNewInvalidSpriteWithNegativeX() {
        Sprite newSprite = setSprite(1L);
        newSprite.setX(-10);
        
        Response responseTest = spriteFacade.createNewSprite(newSprite);
        assertEquals(
          (Response.Status.BAD_REQUEST).getStatusCode(), responseTest.getStatus());
    }
    
    /**
     * Test edit on root with 405 Error or METHOD NOT ALLOWED
     */
    @Test
    public void testEditOnRoot(){
        Response responseTest = spriteFacade.editSpriteOnRoot(null);
        assertEquals(
          (Response.Status.METHOD_NOT_ALLOWED).getStatusCode(), responseTest.getStatus());
    }

    /**
     * Test findAll
     */
    @Test
    public void testSpritesAreFound() {
        List<Sprite> spriteList = new ArrayList<Sprite>(){{
            add(setSprite(1L));
            add(setSprite(2L));
        }};
        //TypedQuery<Sprite> allQuery = em.createQuery(eq1);
        when(em.createQuery(criteriaQuery)).thenReturn(mockedTQ);
        when(mockedTQ.getResultList()).thenReturn(spriteList);
        
        List<Sprite> sprites = spriteFacade.findAll();
        assertEquals(sprites, spriteList);
    }

    /**
     * Test Post edit
     */
    @Test
    public void testEditValidSpriteforPost() {
        Sprite newSprite = setSprite(1L);
        Sprite oldSprite = setSprite(1L);
        oldSprite.setX(20);
        
        when(em.find(any(), any())).thenReturn(newSprite);
        
        Response responseTest = spriteFacade.editWithPost(1L, newSprite);
        Sprite updatedSprite = (Sprite) responseTest.getEntity();
        
        assertEquals(10, updatedSprite.getX());
        assertEquals(
          (Response.Status.OK).getStatusCode(), responseTest.getStatus());
    }

    /**
     * Test Post edit
     */
    @Test
    public void testEditInvalidSpriteWithMismatchedIdforPost() {
        Sprite newSprite = setSprite(1L);
                
        Response responseTest = spriteFacade.editWithPost(2L, newSprite);
        assertEquals(
          (Response.Status.BAD_REQUEST).getStatusCode(), responseTest.getStatus());
    }

    /**
     * Test Post edit
     */
    @Test
    public void testEditInvalidSpriteWithUnfoundIdforPost() {
        Sprite newSprite = setSprite(1L);
        
        when(em.find(any(), any())).thenReturn(null);
        
        Response responseTest = spriteFacade.editWithPost(1L, newSprite);
        assertEquals(
          (Response.Status.NOT_FOUND).getStatusCode(), responseTest.getStatus());
    }

    /**
     * Test Post edit
     */
    @Test
    public void testEditInvalidSpriteWithNegativeXforPost() {
        Sprite newSprite = setSprite(1L);
        newSprite.setX(-10);
                
        Response responseTest = spriteFacade.editWithPost(1L, newSprite);
        assertEquals(
          (Response.Status.BAD_REQUEST).getStatusCode(), responseTest.getStatus());
    }
    
    /**
     * Test Put edit
     */
    @Test
    public void testEditValidSpriteforPut() {
        Sprite newSprite = setSprite(1L);
        Sprite oldSprite = setSprite(1L);
        oldSprite.setX(20);
        
        when(em.find(any(), any())).thenReturn(oldSprite);
        Mockito.doAnswer(i -> i.getArguments()[0] )
                .when(em).merge(any());
        
        Response responseTest = spriteFacade.editWithPut(1L, newSprite);
        Sprite updatedSprite = (Sprite) responseTest.getEntity();
        
        verify(em, times(1)).merge(any());
        assertEquals(10, updatedSprite.getX());
        assertEquals(
          (Response.Status.OK).getStatusCode(), responseTest.getStatus());
    }

    /**
     * Test remove
     */
    @Test
    public void testValidSpriteRemoval() {
        Sprite sprite = setSprite(1L);
        when(em.find(any(), any())).thenReturn(sprite);
        when(em.merge(any())).thenReturn(sprite);
        Mockito.doAnswer(i -> i.getArguments()[0])
                .when(em).remove(any());
        
        Response responseTest = spriteFacade.remove(1L);
        
        verify(em, times(1)).remove(sprite);
        assertEquals(
          (Response.Status.OK).getStatusCode(), responseTest.getStatus());
    }

    /**
     * Test remove
     */
    @Test
    public void testInvalidSpriteRemoval() {
        when(em.find(any(), any())).thenReturn(null);
  
        Response responseTest = spriteFacade.remove(1L);
        assertEquals(
          (Response.Status.NOT_FOUND).getStatusCode(), responseTest.getStatus());

    }
    
    
    /**
     * Test find
     */
    @Test
    public void testSpriteIsFound() {
        Sprite sprite = setSprite(1L);
        when(em.find(any(), any())).thenReturn(sprite);
        
        Response responseTest = spriteFacade.find(1L);
        Sprite foundSprite = (Sprite) responseTest.getEntity();
        
        assertEquals(
          (Response.Status.OK).getStatusCode(), responseTest.getStatus());
        assertEquals(sprite, foundSprite);

    }
    
    /**
     * Test find
     */
    @Test
    public void testSpriteIsNotFound() {
        when(em.find(any(), any())).thenReturn(null);
        
        Response responseTest = spriteFacade.find(1L);
        assertEquals(
          (Response.Status.NOT_FOUND).getStatusCode(), responseTest.getStatus());
    }
    
    /**
     * Test find range
     */
    @Test
    public void testFindRangeHasContent(){
        List<Sprite> spriteList = new ArrayList<Sprite>(){{
            add(setSprite(1L));
            add(setSprite(2L));
        }};
        
        setupFindRange(spriteList);
                
        Response responseTest = spriteFacade.findRange(0, 1);
        assertEquals(
          (Response.Status.OK).getStatusCode(), responseTest.getStatus());

    }
    
    /**
     * Test find range
     */
    @Test
    public void testFindRangeHasNoContent(){
        List<Sprite> spriteList = new ArrayList<>();
        setupFindRange(spriteList);
        
        Response responseTest = spriteFacade.findRange(0, 1);
        assertEquals(
          (Response.Status.NO_CONTENT).getStatusCode(), responseTest.getStatus());
    }
    
    /**
    * Helper
    */
    
    private void setupFindRange(List<Sprite> spriteList){
        int[] range = {0, 1};
                
        when(em.createQuery(criteriaQuery)).thenReturn(mockedTQ);
        when(mockedTQ.setMaxResults(range[1] - range[0] + 1)).thenReturn(maxResultQuery);
        when(mockedTQ.setFirstResult(range[0])).thenReturn(firstResultQuery);
        when(mockedTQ.getResultList()).thenReturn(spriteList);
    }

    private Sprite setSprite(Long id){
     Sprite sprite = new Sprite();
     if(id != null){
         sprite.setId(id);
     }
     sprite.setId(id);
     sprite.setX(10);
     sprite.setY(10);
     sprite.setxSpeed(10);
     sprite.setySpeed(10);

     return sprite;
    }
}
