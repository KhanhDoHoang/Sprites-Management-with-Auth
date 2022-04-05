/*
 * This is a sprite facade rest interface
 * @author Hoang Do
 * @version 1.9
 */

package com.mycompany.spritehoang.facade;

import cst8218.do000075.entity.Sprite;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Stateless
@Path("cst8218.entity.sprite")
public class SpriteFacadeREST extends AbstractFacade<Sprite> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;
    private static final Logger logger = Logger.getLogger(SpriteFacadeREST.class.getName());

    public SpriteFacadeREST() {
        super(Sprite.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Create a new Sprite with POST, if there comes with no id, create a new one, comes with an id, update the existing one
     * @param newSprite
     * @return
     */
    @POST
    @Path("/")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createNewSprite(Sprite newSprite) {
        logger.log(Level.INFO, "Create a new sprite");        
        if(newSprite == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }       
        
        //check if x,y is negative
        Response isSpriteValid = isValidSprite(newSprite);
        if(isSpriteValid != null){
            return isSpriteValid;
        }

        setupNullProperty(newSprite);
        //1. Creates the new sprite if id is null, and returns the created sprite
        if(newSprite.getId() == null){
            super.create(newSprite);
            return Response.status(Response.Status.CREATED).entity(newSprite).build();
        }

        //2. Updates an existing sprite if id is not null and exists
        Sprite oldSprite = super.find(newSprite.getId());
        if(oldSprite != null){
            newSprite.updates(oldSprite);
            return Response.status(Response.Status.OK).entity(oldSprite).build();
        }
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }
    
    @PUT
    @Path("/")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response editSpriteOnRoot(Sprite newSprite) {
        logger.log(Level.INFO, "Edit a sprite on root");

        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }
    
    /**
     * Find all sprite within the Sprite databases
     * @Param: none
     * @return
     */
    @GET
    @Path("/")
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Sprite> findAll() {
        logger.log(Level.INFO, "Find all sprites");
        return super.findAll();
    }
    
    /**
     * POST on a specific id should update the Sprite having that id with the new non-null information given by the Sprite in the body of the request
     * @param id
     * @param newSprite
     * @return
     */
    @POST
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response editWithPost(@PathParam("id") Long id, Sprite newSprite) {
        logger.log(Level.INFO, "Edit with POST request");
        //check if x,y is negative
        Response isSpriteValid = isValidSprite(newSprite);
        if(isSpriteValid != null){
            return isSpriteValid;
        }
        
        //update the old value with non-null new sprite
        Sprite oldSprite = super.find(id);
        if(oldSprite == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        newSprite.updates(oldSprite);
        
        return Response.status(Response.Status.OK).entity(oldSprite).build();
    }
    
    /**
     * PUT on a specific id should replace the Sprite having that id with the Sprite in the body of the request.
     * @param id
     * @param newSprite
     * @return
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response editWithPut(@PathParam("id") Long id, Sprite newSprite) {
        logger.log(Level.INFO, "Edit with PUT request");
        Response invalidResponse = isValidEditSprite(newSprite, id);
        if(invalidResponse != null){
            return invalidResponse;
        }
        
        //check if x, y is negative
        Response isSpriteValid = isValidSprite(newSprite);
        if(isSpriteValid != null){
            return isSpriteValid;
        }
                    
        //update/overwrite the old values
        super.edit(newSprite);
        return Response.status(Response.Status.OK).entity(newSprite).build();
    }

    /**
     * Remove the sprite based on the id
     * @param id
     * @return
     */
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id) {
        logger.log(Level.INFO, "Remove a sprite");
        Sprite sprite = super.find(id);
        if(sprite != null){
            super.remove(sprite);
            return Response.status(Response.Status.OK).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        } 
    }

    /**
     * Find a specific sprite with id
     * @param id
     * @return
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id) { 
        logger.log(Level.INFO, "Find a sprite");
        Sprite sprite = super.find(id);
        if(sprite != null){
            return Response.status(Response.Status.OK)
                           .entity(sprite).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                           .build(); 
    }

    /**
     * Find sprites within a specific range
     * @param from
     * @param to
     * @return 
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        List<Sprite> sprites = super.findRange(new int[]{from, to});
        if(!sprites.isEmpty()){
            return Response.status(Response.Status.OK)
                           .entity(sprites).build();
        }
        return Response.status(Response.Status.NO_CONTENT)
                           .build();
    }

    /**
     * Used to count the sprite list
     * @return 
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    
    /**
     * HELPER METHOD
     */
    
    /**
     * Check if the sprite is valid for editing
     * @param newSprite
     * @param id
     * @return
     */
    private Response isValidEditSprite(Sprite newSprite, Long id){
        //no sprite or no id
        if(newSprite == null || id == null || !id.equals(newSprite.getId())){
            return Response.status(Response.Status.BAD_REQUEST).build();
        } 
        
        Sprite oldSprite = super.find(id);
        if(oldSprite == null){
            //updates fail (no exist sprite)
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        return null;
    }
    /**
     * Validating sprite's x and y
     * @param newSprite
     * @return 
     */
    private Response isValidSprite(Sprite newSprite){
        //Check if sprite is negative x, y
        if(newSprite.getX() != null && newSprite.getX() < 0){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if(newSprite.getY() != null && newSprite.getY() < 0){
            return Response.status(Response.Status.BAD_REQUEST).build();
        } 
        
        return null;
    }
    
    /**
     * Setup a variable with a non value with 0
     * @param sprite 
     */
    private void setupNullProperty(Sprite sprite){
        if(sprite.getX() == null){
            sprite.setX(0);
        }
        if(sprite.getY() == null){
            sprite.setY(0);
        }
        if(sprite.getxSpeed() == null){
            sprite.setxSpeed(0);
        }
        if(sprite.getySpeed() == null){
            sprite.setySpeed(0);
        }
    }
    
}
