package at.htl.cinemamanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@Entity
@NamedQuery(name = "Hall.findAll", query = "select h from Hall h")
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int seating;

    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @XmlTransient
    private Cinema cinema;

    @OneToMany (cascade = CascadeType.ALL,
            mappedBy = "hall",
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    @XmlTransient
    private List<Presentation> presentations;

    public Hall() {
    }

    public Hall(String name, int seating, Cinema cinema) {
        this.name = name;
        this.seating = seating;
        this.cinema = cinema;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeating() {
        return seating;
    }

    public void setSeating(int seating) {
        this.seating = seating;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public List<Presentation> getPresentations() {
        return presentations;
    }

    public void setPresentations(List<Presentation> presentations) {
        this.presentations = presentations;
    }
}
