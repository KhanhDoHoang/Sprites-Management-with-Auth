/**
 *
 * @author Hoang Do
 * Purpose: Talk to the databases
 */
package cst8218.do000075.game;

import cst8218.do000075.entity.Sprite;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class GameSession {
    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;
    private Class<Sprite> entityClass;

    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Sprite> findAll(){
        javax.persistence.criteria.CriteriaQuery eq = getEntityManager().getCriteriaBuilder().createQuery();
        eq.select(eq.from(Sprite.class));
        return getEntityManager().createQuery(eq).getResultList();
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
