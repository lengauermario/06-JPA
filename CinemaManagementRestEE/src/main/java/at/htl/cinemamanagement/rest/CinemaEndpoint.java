package at.htl.cinemamanagement.rest;

import at.htl.cinemamanagement.model.Cinema;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("cinema")
public class CinemaEndpoint {

    @PersistenceContext
    EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cinema> getAll(){
        TypedQuery<Cinema> query = entityManager.createNamedQuery("Cinema.findAll", Cinema.class);
        return query.getResultList();
    }

    @POST
    @Path("/insert")
    @Transactional
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Cinema cinema){
        entityManager.persist(cinema);
        return Response.ok().entity(cinema).build();
    }

    @PUT
    @Path("/update/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long id, Cinema updatedCinema) {
        if (updatedCinema == null || entityManager.find(Cinema.class, id) == null){
            return Response.serverError().build();
        }
        updatedCinema.setId(id);
        entityManager.merge(updatedCinema);
        return Response.ok().entity(entityManager.find(Cinema.class, id)).build();
    }

    @GET
    @Path("/{id}")
    public Cinema find(@PathParam("id") Long id){
        return entityManager.find(Cinema.class, id);
    }

    @GET
    @Path("/name/{name}")
    public List<Cinema> find(@PathParam("name") String name){
        return entityManager.createQuery("SELECT c FROM Cinema c where c.name like '%" + name + "%'").getResultList();
    }

    @DELETE
    @Transactional
    @Path("/delete/{id}")
    public void delete(@PathParam("id") long id) {
        Cinema cinema = entityManager.find(Cinema.class, id);
        if(cinema != null) {
            entityManager.remove(entityManager.contains(cinema) ? cinema : entityManager.merge(cinema));
        }
    }
}
