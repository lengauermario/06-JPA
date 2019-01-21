package at.htl.cinemamanagement.rest;

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

@Path("employee")
public class EmployeeEndpoint {
    @PersistenceContext
    EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAll(){
        TypedQuery<Employee> query = entityManager.createNamedQuery("Employee.findAll", Employee.class);
        return query.getResultList();
    }

    @POST
    @Path("/insert")
    @Transactional
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Employee employee){
        entityManager.persist(employee);
        System.err.println(employee.toString());
        return Response.ok().entity(employee).build();
    }

    @PUT
    @Path("/update/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long id, Employee updatedEmployee) {
        if (updatedEmployee == null || entityManager.find(Employee.class, id) == null){
            return Response.serverError().build();
        }
        updatedEmployee.setId(id);
        entityManager.merge(updatedEmployee);
        return Response.ok().entity(entityManager.find(Employee.class, id)).build();
    }

    @GET
    @Path("/{id}")
    public Employee find(@PathParam("id") Long id){
        return entityManager.find(Employee.class, id);
    }

    @DELETE
    @Transactional
    @Path("/delete/{id}")
    public void delete(@PathParam("id") long id) {
        Employee employee = entityManager.find(Employee.class, id);
        if(employee != null) {
            entityManager.remove(entityManager.contains(employee) ? employee : entityManager.merge(employee));
        }
    }
}
