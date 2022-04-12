
package com.mycompany.spritehoang;

import cst8218.assignment2.entity.AppUser;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * AppUserFacade class
 * @author Hoang Do, Minh Duc
 * Purpose: This is the ejb for the controller, this facade will make a query
 * to talk and get data from databases depends on the request from controller
 */

@Stateless
public class AppUserFacade extends AbstractFacade<AppUser> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AppUserFacade() {
        super(AppUser.class);
    }
    
}
