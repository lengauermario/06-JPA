package at.htl.cinemamanagement.rest;

import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.fail;

public class HallEndpointIT {
    private Client client;
    private WebTarget target;

    @Before
    public void initClient() {
        this.client = ClientBuilder.newClient();
        this.target = client.target("http://localhost:8080/cinemamanagement/API/hall");
    }

    @Test
    public void crud() {
        JsonObject hallJson = Json.createObjectBuilder()
                .add("name", "Saal Test")
                .add("seating", 256)
                .add("cinema", Json.createObjectBuilder()
                        .add("name", "Cineplex Linz")
                        .add("address", "Prinz-Eugen-Weg 22, 4020 Linz")
                        .add("inventionDate", "1999-12-08")
                ).build();

        this.target = client.target("http://localhost:8080/cinemamanagement/API/hall/insert");
        Response response = this.target
                .request()
                .post(Entity.json(hallJson));

        assertThat(response.getStatus(), is(200));
        JsonObject entity = response.readEntity(JsonObject.class);

        int id = entity.getInt("id");
        System.out.println(id);
        assertThat(entity.getString("name"), is("Saal Test"));
        assertThat(entity.getInt("seating"), is(256));

        //Get
        this.target = client.target("http://localhost:8080/cinemamanagement/API/hall/"+id);
        JsonObject cinema = this.target.request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        assertThat(cinema.getString("name"), is("Saal Test"));

        //Update
        this.target = client.target("http://localhost:8080/cinemamanagement/API/hall/update/" + id);
        hallJson = Json.createObjectBuilder()
                .add("name", "Saal Test 2")
                .add("seating", 300)
                .add("cinema", Json.createObjectBuilder()
                        .add("name", "Cineplex Linz")
                        .add("address", "Prinz-Eugen-Weg 22, 4020 Linz")
                        .add("inventionDate", "1999-12-08")
                ).build();

        response = target.request()
                .put(Entity.json(hallJson));
        entity = response.readEntity(JsonObject.class);
        assertThat(entity.getInt("seating"), is(300));

        //Delete
        this.target = client.target("http://localhost:8080/cinemamanagement/API/hall/delete/"+id);
        this.target.request().delete();
    }

}
