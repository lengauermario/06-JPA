package at.htl.cinemamanagement.rest;

import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.print.attribute.standard.Media;
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

public class CinemaEnpointIT {
    private Client client;
    private WebTarget target;

    @Before
    public void initClient() {
        this.client = ClientBuilder.newClient();
        this.target = client.target("http://localhost:8080/cinemamanagement/API/cinema");
    }

    @Test
    public void crud() {
        JsonObject cinemaJson = Json.createObjectBuilder()
                .add("name", "Cineplex Klagenfurt")
                .add("address", "Sonnsteinweg 12, 9063 Klagenfurt")
                .add("founded", "2001-12-12")
                .build();

        System.err.println(cinemaJson.toString());

        this.target = client.target("http://localhost:8080/cinemamanagement/API/cinema/insert");
        Response response = this.target
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(cinemaJson));

        JsonObject entity = response.readEntity(JsonObject.class);

        assertThat(response.getStatus(), is(200));
        int id = entity.getInt("id");
        System.out.println(id);
        assertThat(entity.getString("name"), is("Cineplex Klagenfurt"));
        assertThat(entity.getString("founded"), is("2001-12-12"));

        //Get
        this.target = client.target("http://localhost:8080/cinemamanagement/API/cinema/"+id);
        JsonObject cinema = this.target.request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        assertThat(cinema.getString("name"), is("Cineplex Klagenfurt"));

        //Update
        this.target = client.target("http://localhost:8080/cinemamanagement/API/cinema/update/" + id);
        cinemaJson = Json.createObjectBuilder()
                .add("name", "Cineplex Klagenfurt")
                .add("address", "Sonnsteinweg 12-16, 9063 Klagenfurt")
                .add("founded", "2001-12-12")
                .build();

        response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.json(cinemaJson));
        entity = response.readEntity(JsonObject.class);
        assertThat(entity.getString("address"), is("Sonnsteinweg 12-16, 9063 Klagenfurt"));

        //Delete
        this.target = client.target("http://localhost:8080/cinemamanagement/API/cinema/delete/"+id);
        this.target.request().delete();
    }

}
