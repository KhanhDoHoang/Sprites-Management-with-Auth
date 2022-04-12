
package com.mycompany.spritehoang;

import cst8218.assignment2.entity.Sprite;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * SpriteFacade class
 * @author Hoang Do, Minh Duc
 * Purpose: This is the ejb for the controller 
 * which will talk with the databases to get data we ask for
 * it received the command from controller
 */

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
