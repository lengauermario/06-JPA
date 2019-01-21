package at.htl.cinemamanagement.rest;

import at.htl.cinemamanagement.model.Cinema;
import at.htl.cinemamanagement.model.Presentation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("presentation")
public class PresentationEndpoint {
    @PersistenceContext
    EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Presentation> getAll(){
        TypedQuery<Presentation> query = entityManager.createNamedQuery("Presentation.findAll", Presentation.class);
        return query.getResultList();
    }

    @POST
    @Path("/insert")
    @Transactional
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Presentation Presentation){
        entityManager.persist(Presentation);
        return Response.ok().entity(Presentation).build();
    }

    @PUT
    @Path("/update/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long id, Presentation updatedPresentation) {
        if (updatedPresentation == null || entityManager.find(Presentation.class, id) == null){
            return Response.serverError().build();
        }
        updatedPresentation.setId(id);
        entityManager.merge(updatedPresentation);
        return Response.ok().entity(entityManager.find(Presentation.class, id)).build();
    }

    @GET
    @Path("/{id}")
    public Presentation find(@PathParam("id") Long id){
        return entityManager.find(Presentation.class, id);
    }

    @GET
    @Path("/date/{date}")
    public List<Presentation> find(@PathParam("date") String date){
        return entityManager.createQuery("SELECT p FROM Presentation p where p.startTime < :date ").getResultList();
    }

    @DELETE
    @Transactional
    @Path("/delete/{id}")
    public void delete(@PathParam("id") long id) {
        Presentation presentation = entityManager.find(Presentation.class, id);
        if(presentation != null) {
            entityManager.remove(entityManager.contains(presentation) ? presentation : entityManager.merge(presentation));
        }
    }
}
