package at.htl.cinemamanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NamedQuery(name = "Presentation.findAll", query = "select p from Presentation p")
public class Presentation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

 /*   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;
*/
    private String startTime;
    private String endTime;
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Hall hall;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Movie movie;

    @ManyToMany(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER)
    @JoinTable(name = "ticket")
    @JsonIgnore
    @XmlTransient
    private List<Customer> customers;

    public Presentation() {
    }

    public Presentation(String startTime, String endTime, Hall hall, Movie movie, List<Customer> customers) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.hall = hall;
        this.movie = movie;
        this.customers = customers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) { this.endTime = endTime; }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
