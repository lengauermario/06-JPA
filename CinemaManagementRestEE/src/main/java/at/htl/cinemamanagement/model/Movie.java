package at.htl.cinemamanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@NamedQuery(name = "Movie.findAll", query = "select m from Movie m")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String dateOfAppearance;

    public Movie() {
    }

    public Movie(String title, String dateOfAppearance) {
        this.title = title;
        this.dateOfAppearance = dateOfAppearance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateOfAppearance() {
        return dateOfAppearance;
    }

    public void setDateOfAppearance(String dateOfAppearance) {
        this.dateOfAppearance = dateOfAppearance;
    }

}
