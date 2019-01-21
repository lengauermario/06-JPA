package at.htl.cinemamanagement.model;


import at.htl.cinemamanagement.LocalDateConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NamedQuery(name = "Cinema.findAll", query = "select c from Cinema c")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    @XmlJavaTypeAdapter(LocalDateConverter.class)
    private LocalDate founded;


    @OneToMany(mappedBy = "cinema", fetch = FetchType.LAZY)
    @JsonIgnore
    @XmlTransient
    private List<Hall> halls;

    @OneToMany(mappedBy = "cinema",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JsonIgnore
    @XmlTransient
    private List<Employee> employees;

    public Cinema() {
    }

    public Cinema(String name, String address, LocalDate founded) {
        this.name = name;
        this.address = address;
        this.founded = founded;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getFounded() {
        return founded;
    }

    public void setFounded(LocalDate founded) {
        this.founded = founded;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public void setHalls(List<Hall> halls) {
        this.halls = halls;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
