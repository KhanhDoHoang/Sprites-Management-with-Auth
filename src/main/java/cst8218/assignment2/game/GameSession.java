/**
 *
 * @author Hoang Do
 * Purpose: Talk to the databases
 */
package cst8218.assignment2.game;

import cst8218.assignment2.entity.Sprite;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


@Stateless
public class GameSession {
    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;
    private Class<Sprite> entityClass;

    protected EntityManager getEntityManager() {
        return em;
    }
    
    public void setEntityManager(EntityManager em){
        this.em = em;
    }
    
    
    
    public List<Sprite> findAll(){
//        javax.persistence.criteria.CriteriaQuery eq = getEntityManager().getCriteriaBuilder().createQuery();
//        eq.select(eq.from(Sprite.class));
//        return getEntityManager().createQuery(eq).getResultList();
        
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery eq1 = cb.createQuery();
        Root rootEntry = eq1.from(Sprite.class);
        eq1.select(rootEntry);
        TypedQuery<Sprite> allQuery = em.createQuery(eq1);
        List<Sprite> sprites = allQuery.getResultList();
        return sprites;
    }
    
    public void create(Sprite entity) {
        getEntityManager().persist(entity);
    }

    public void edit(Sprite entity) {
        getEntityManager().merge(entity);
    }

    public void remove(Sprite entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public Sprite find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
}
