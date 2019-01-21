package at.htl.cinemamanagement.rest;

import at.htl.cinemamanagement.model.Cinema;
import at.htl.cinemamanagement.model.Hall;
import at.htl.cinemamanagement.model.Hall;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("hall")
public class HallEndpoint {
    @PersistenceContext
    EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Hall> getAll(){
        TypedQuery<Hall> query = entityManager.createNamedQuery("Hall.findAll", Hall.class);
        return query.getResultList();
    }

    @POST
    @Path("/insert")
    @Transactional
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Hall hall){
        entityManager.persist(hall);
        return Response.ok().entity(hall).build();
    }

    @PUT
    @Path("/update/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long id, Hall updatedHall) {
        if (updatedHall == null || entityManager.find(Hall.class, id) == null){
            return Response.serverError().build();
        }
        updatedHall.setId(id);
        entityManager.merge(updatedHall);
        return Response.ok().entity(entityManager.find(Hall.class, id)).build();
    }

    @GET
    @Path("/{id}")
    public Hall find(@PathParam("id") Long id){
        return entityManager.find(Hall.class, id);
    }

    @DELETE
    @Transactional
    @Path("/delete/{id}")
    public void delete(@PathParam("id") long id) {
        Hall hall = entityManager.find(Hall.class, id);
        if(hall != null) {
            entityManager.remove(entityManager.contains(hall) ? hall : entityManager.merge(hall));
        }
    }
}
