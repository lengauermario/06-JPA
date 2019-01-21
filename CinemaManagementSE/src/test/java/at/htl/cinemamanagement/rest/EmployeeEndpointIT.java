package at.htl.cinemamanagement.rest;

import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EmployeeEndpointIT {
    private Client client;
    private WebTarget target;

    @Before
    public void initClient() {
        this.client = ClientBuilder.newClient();
        this.target = client.target("http://localhost:8080/cinemamanagement/API/employee");
    }

    @Test
    public void crud() {
        JsonObject employeeJson = Json.createObjectBuilder()
                .add("firstName", "Max")
                .add("lastName", "Mustermann")
                .add("address", "Musterstraße 12, 5050 Musterstadt")
                .add("birthday", "1973-12-16")
                .add("email", "max.mustermann@muster.de")
                .add("phoneNumber", "+43 660665558808")
                .add("cinema", Json.createObjectBuilder()
                        .add("name", "Cineplex Linz")
                        .add("address", "Prinz-Eugen-Straße 22, 4020 Linz")
                        .add("founded", "1999-12-08")
                )
                .add("salary", 5000)
                .add("personalNumber", 200)
                .add("employedSince", "2010-08-15")
                .build();

        this.target = client.target("http://localhost:8080/cinemamanagement/API/employee/insert");
        Response response = this.target
                .request()
                .post(Entity.json(employeeJson));

        System.err.println(response);

        JsonObject entity = response.readEntity(JsonObject.class);

        assertThat(response.getStatus(), is(200));
        int id = entity.getInt("id");
        System.out.println(id);
        assertThat(entity.getString("firstName"), is("Max"));
        assertThat(entity.getString("lastName"), is("Mustermann"));

        //Get
        this.target = client.target("http://localhost:8080/cinemamanagement/API/employee/"+id);
        JsonObject cinema = this.target
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);
        assertThat(cinema.getString("firstName"), is("Max"));
        assertThat(cinema.getString("lastName"), is("Mustermann"));

        //Update
        this.target = client.target("http://localhost:8080/cinemamanagement/API/employee/update/" + id);
        employeeJson = Json.createObjectBuilder()
                .add("firstName", "Maximilian")
                .add("lastName", "Mustermann")
                .add("address", "Musterstraße 12, 5050 Musterstadt")
                .add("birthday", "1973-12-16")
                .add("email", "max.mustermann@muster.de")
                .add("phoneNumber", "+43 660665558808")
                .add("cinema", Json.createObjectBuilder()
                        .add("name", "Cineplex Linz")
                        .add("address", "Prinz-Eugen-Straße 22, 4020 Linz")
                        .add("founded", "1999-12-08")
                )
                .add("salary", 5000)
                .add("personalNumber", 200)
                .add("employedSince", "2010-08-15")
                .build();

        response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.json(employeeJson));
        entity = response.readEntity(JsonObject.class);
        assertThat(entity.getString("firstName"), is("Maximilian"));

        //Delete
        this.target = client.target("http://localhost:8080/cinemamanagement/API/employee/delete/"+id);
        this.target.request().delete();
    }

}
