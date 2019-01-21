package at.htl.cinemamanagement.rest;

import at.htl.cinemamanagement.model.Cinema;
import at.htl.cinemamanagement.model.Customer;
import at.htl.cinemamanagement.model.Employee;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("customer")

public class CustomerEndpoint {
    @PersistenceContext
    EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getAll(){
        TypedQuery<Customer> query = entityManager.createNamedQuery("Customer.findAll", Customer.class);
        return query.getResultList();
    }

    @POST
    @Path("/insert")
    @Transactional
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Customer customer){
        entityManager.persist(customer);
        return Response.ok().entity(customer).build();
    }

    @PUT
    @Path("/update/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long id, Customer updatedCustomer) {
        if (updatedCustomer == null || entityManager.find(Customer.class, id) == null){
            return Response.serverError().build();
        }
        updatedCustomer.setId(id);
        entityManager.merge(updatedCustomer);
        return Response.ok().entity(entityManager.find(Customer.class, id)).build();
    }

    @GET
    @Path("/{id}")
    public Customer find(@PathParam("id") Long id){
        return entityManager.find(Customer.class, id);
    }

    @DELETE
    @Transactional
    @Path("/delete/{id}")
    public void delete(@PathParam("id") long id) {
        Customer customer = entityManager.find(Customer.class, id);
        if(customer != null) {
            entityManager.remove(entityManager.contains(customer) ? customer : entityManager.merge(customer));
        }
    }
}
