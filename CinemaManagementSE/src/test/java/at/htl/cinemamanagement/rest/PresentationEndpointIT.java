package at.htl.cinemamanagement.rest;

import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PresentationEndpointIT {
    private Client client;
    private WebTarget target;

    @Before
    public void initClient() {
        this.client = ClientBuilder.newClient();
        this.target = client.target("http://localhost:8080/cinemamanagement/API/presentation");
    }

    @Test
    public void crud() {
        JsonObject jsonPresentation = Json.createObjectBuilder()
                .add("startTime", "2019-01-07 13:30")
                .add("endTime", "2019-01-07 15:45")
                .add("hall",Json.createObjectBuilder()
                    .add("name", "Saal Test")
                    .add("seating", 256)
                    .add("cinema", Json.createObjectBuilder()
                            .add("name", "Cineplex Linz")
                            .add("address", "Prinz-Eugen-Weg 22, 4020 Linz")
                            .add("founded", "1999-12-08")
                    )
                )
                .add("movie", Json.createObjectBuilder()
                    .add("title", "Hobbit")
                    .add("dateOfAppearance", "2001-12-12")
                )
                .add("customers",Json.createArrayBuilder()
                )
                .build();

        this.target = client.target("http://localhost:8080/cinemamanagement/API/presentation/insert");
        Response response = this.target
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(jsonPresentation));

        System.out.println(Entity.json(jsonPresentation));
        JsonObject entity = response.readEntity(JsonObject.class);

        assertThat(response.getStatus(), is(200));
        int id = entity.getInt("id");
        System.out.println(id);
        assertThat(entity.getString("startTime"), is("2019-01-07 13:30"));

        //Get
        this.target = client.target("http://localhost:8080/cinemamanagement/API/presentation/"+id);
        JsonObject presentation = this.target.request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        assertThat(presentation.getString("startTime"), is("2019-01-07 13:30"));

        //Update
        this.target = client.target("http://localhost:8080/cinemamanagement/API/presentation/update/" + id);
        jsonPresentation = Json.createObjectBuilder()
                .add("startTime", "2019-01-07 14:30")
                .add("endTime", "2019-01-07 16:45")
                .build();

        response = target
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(jsonPresentation));
        entity = response.readEntity(JsonObject.class);
        assertThat(entity.getString("startTime"), is("2019-01-07 14:30"));

        //Delete
        this.target = client.target("http://localhost:8080/cinemamanagement/API/presentation/delete/"+id);
        this.target.request().delete();
    }
}
