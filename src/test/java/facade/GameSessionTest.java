/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import cst8218.assignment2.entity.Sprite;
import cst8218.assignment2.game.GameSession;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
/**
 *
 * @author Khanh Do
 */
@RunWith(MockitoJUnitRunner.class)
public class GameSessionTest {
    
    
    private final EntityManager em = Mockito.mock(EntityManager.class);
    
    private final CriteriaQuery criteriaQuery = Mockito.mock(CriteriaQuery.class);
    
    private final CriteriaQuery mockedCQAll = Mockito.mock(CriteriaQuery.class);
    
    private final Root mockedRoot = Mockito.mock(Root.class);
    
    private final CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
    
    private final TypedQuery<Sprite> mockedTQ = Mockito.mock(TypedQuery.class);
    
    private Class<Sprite> entityClass;
    
    private GameSession gameSession;
    
    public void setSpriteClass(Class<Sprite> entityClass) {
        this.entityClass = entityClass;
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        gameSession = new GameSession();
        gameSession.setEntityManager(em);
                
        //CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        when(em.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        //CriteriaQuery eq1 = cb.createQuery(); //cq is null!!!!
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        //Root rootEntry = eq1.from(Sprite.class); //cq is null!!!!
        when(criteriaQuery.from(Sprite.class)).thenReturn(mockedRoot);
        //eq1.select(rootEntry);
        when(criteriaQuery.select(any())).thenReturn(mockedCQAll);
        //TypedQuery<Sprite> allQuery = em.createQuery(eq1);
        when(em.createQuery(criteriaQuery)).thenReturn(mockedTQ);
        
    }
    
    @Test
    public void testSpritesAreFound(){
        List<Sprite> spriteList = new ArrayList<Sprite>(){{
            add(setSprite(1L));
            add(setSprite(2L));
        }};
        when(mockedTQ.getResultList()).thenReturn(spriteList);
        
        List<Sprite> sprites = gameSession.findAll();
        assertEquals(sprites, spriteList);
    }

    @Test
    public void testValidCreate() {
        Sprite sprite = setSprite(1L);     
        Mockito.doAnswer(i -> i.getArguments()[0] )
                .when(em).persist(any());
        gameSession.create(sprite);
        verify(em, times(1)).persist(any());
    }

    @Test
    public void testValidEdit() {
        Sprite sprite = setSprite(1L);     
        Mockito.doAnswer(i -> i.getArguments()[0] )
                .when(em).merge(any());
        gameSession.edit(sprite);
        verify(em, times(1)).merge(any());
    }

    @Test
    public void testValidRemove() {
        Sprite sprite = setSprite(1L);
        when(em.merge(any())).thenReturn(sprite);
        Mockito.doAnswer(i -> i.getArguments()[0])
                .when(em).remove(any());
        gameSession.remove(sprite);
        verify(em, times(1)).remove(any());
        //getEntityManager().remove(getEntityManager().merge(entity));

    }

    @Test
    public void testSpriteIsFound() {
        Sprite sprite = setSprite(1L);
        when(em.find(any(), any())).thenReturn(sprite);
        
        Sprite testSprite = gameSession.find(1L);
        assertEquals(sprite, testSprite);
    }

    /**
    * Helper
    */

    private Sprite setSprite(Long id){
     Sprite sprite = new Sprite();
     sprite.setId(id);
     sprite.setX(10);
     sprite.setY(10);
     sprite.setxSpeed(10);
     sprite.setySpeed(10);

     return sprite;
    }
}
