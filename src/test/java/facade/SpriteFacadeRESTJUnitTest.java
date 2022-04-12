package facade;

import com.mycompany.spritehoang.facade.SpriteFacadeREST;
import cst8218.assignment2.entity.Sprite;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * SpriteFacadeRESTJUnitTest class 
 * Testing all methods within the rest class with invalid and valid expectations
 * @author Khanh Do, Minh Duc
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
    
    /**
     * Setting the inject mock object and mock all the query builder used for later
     * @throws Exception 
     */
    @Before
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
     * Testing valid Sprite Created without Id
     * This will not provide id and the new sprite will be created
     * The expectation will be Status Created with persist method got called 1 time
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
     * Testing valid Sprite Created with Id
     * This will provide id and will be checked to see if existed or not
     * The expectation will be sprite existed and the new value will overwrite old values
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
        
        Integer expectedX = 10;
        assertEquals(expectedX, (updatedSprite.getX()));
        assertEquals(
          (Response.Status.OK).getStatusCode(), responseTest.getStatus());
    }
    
    /**
     * Create invalid sprite with null sprite
     * The expectation will be BAD REQUEST status return
     */
    @Test
    public void testCreateNewInvalidNullSprite() {
        Response responseTest = spriteFacade.createNewSprite(null);
        
        assertEquals(
          (Response.Status.BAD_REQUEST).getStatusCode(), responseTest.getStatus());
    }
    
    /**
     * Create invalid sprite with negative x
     * This will be caught in validSpriteCheck, because of negative x
     * The expectation will be BAD REQUEST status
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
     * 
     */
    @Test
    public void testEditOnRoot(){
        Response responseTest = spriteFacade.editSpriteOnRoot(null);
        assertEquals(
          (Response.Status.METHOD_NOT_ALLOWED).getStatusCode(), responseTest.getStatus());
    }

    /**
     * Test findAll method with all sprites are found
     * The expectation will be the mock Sprite list is equal to
     * the sprite list got from calling the method
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
     * Test valid Post edit
     * Provided with newSprite with ID, the existed sprite will be called
     * Used new value from new Sprite to overwrite old Sprite values
     * The expectation will be OK status with the new X from new Sprite overwrite the old one
     */
    @Test
    public void testEditValidSpriteforPost() {
        Sprite newSprite = setSprite(1L);
        Sprite oldSprite = setSprite(1L);
        oldSprite.setX(20);
        
        when(em.find(any(), any())).thenReturn(newSprite);
        
        Response responseTest = spriteFacade.editWithPost(1L, newSprite);
        Sprite updatedSprite = (Sprite) responseTest.getEntity();
        
        Integer expectedX = 10;
        assertEquals(expectedX, (updatedSprite.getX()));
        assertEquals(
          (Response.Status.OK).getStatusCode(), responseTest.getStatus());
    }

    /**
     * Test invalid Post edit with mismatched id
     * The id will be not the same between body and url 
     * The expectation will be BAD REQUEST status
     */
    @Test
    public void testEditInvalidSpriteWithMismatchedIdforPost() {
        Sprite newSprite = setSprite(1L);
                
        Response responseTest = spriteFacade.editWithPost(2L, newSprite);
        assertEquals(
          (Response.Status.BAD_REQUEST).getStatusCode(), responseTest.getStatus());
    }

    /**
     * Test invalid Post edit with not found id
     * With mock data is the body from request and the id is not existed within databases
     * The expectation will be NOT FOUND
     */
    @Test
    public void testEditInvalidSpriteWithNotFoundIdforPost() {
        Sprite newSprite = setSprite(1L);
        
        when(em.find(any(), any())).thenReturn(null);
        
        Response responseTest = spriteFacade.editWithPost(1L, newSprite);
        assertEquals(
          (Response.Status.NOT_FOUND).getStatusCode(), responseTest.getStatus());
    }

    /**
     * Test invalid Post edit with negative x
     * Because of the negative x, the sprite is invalid to be save to db
     * The expectation will be BAD REQUEST 
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
     * Test valid Put edit
     * Provided new Sprite with different x value compared to old Sprite
     * The expectation will be x value will be overwritten and OK status return
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
        Integer expectedX = 10;
        assertEquals(expectedX, (updatedSprite.getX()));
        assertEquals(
          (Response.Status.OK).getStatusCode(), responseTest.getStatus());
    }

    /**
     * Test valid remove
     * Provided with valid id, the existed Sprite will be found and delete
     * The expectation is remove method within AbstractFacade got called
     * and OK status return
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
     * Test invalid remove with not found id
     * The expectation will be NOT FOUND
     */
    @Test
    public void testInvalidSpriteRemoval() {
        when(em.find(any(), any())).thenReturn(null);
  
        Response responseTest = spriteFacade.remove(1L);
        assertEquals(
          (Response.Status.NOT_FOUND).getStatusCode(), responseTest.getStatus());

    }
    
    
    /**
     * Test valid find method
     * Provided with valid id and existed sprite will be found
     * The expectation will be OK status return and mocked Sprite is equal to Sprite got called from method
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
     * Test find range has content
     * Provided with the list of 2 sprites
     * The expectation will be OK status return
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
     * Test find range without content
     * Provided with no Sprite
     * The expectation will be NO CONTENT status return
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
    /**
     * Helper method to set up mocking for findRange test
     * @param spriteList
     */
    private void setupFindRange(List<Sprite> spriteList){
        int[] range = {0, 1};
                
        when(em.createQuery(criteriaQuery)).thenReturn(mockedTQ);
        when(mockedTQ.setMaxResults(range[1] - range[0] + 1)).thenReturn(maxResultQuery);
        when(mockedTQ.setFirstResult(range[0])).thenReturn(firstResultQuery);
        when(mockedTQ.getResultList()).thenReturn(spriteList);
    }

    /**
     * Helper method to set up sprite within the test
     * @param id
     * @return sprite
     */
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
