/**
 * 
 * @author Hoang Do
 * Purpose: This is the ejb for the controller
 */
package com.mycompany.spritehoang;

import cst8218.assignment2.entity.Sprite;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class SpriteFacade extends AbstractFacade<Sprite> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SpriteFacade() {
        super(Sprite.class);
    }
    
}
