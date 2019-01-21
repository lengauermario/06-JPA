package at.htl.cinemamanagement.model;


import at.htl.cinemamanagement.LocalDateConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Entity
@NamedQuery(name = "Employee.findAll", query = "select e from Employee e")
@XmlRootElement
public class Employee extends Person{

    private double salary;
    private double personalNumber;
    @XmlJavaTypeAdapter(LocalDateConverter.class)
    private LocalDate employedSince;

    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    @XmlTransient
    private Cinema cinema;

    public Employee() {
    }


    public Employee(String firstName, String lastName, String address, LocalDate birthday, String email, String phoneNumber, Cinema cinema, double salary, double personalNumber, LocalDate employedSince) {
        super(firstName, lastName, address, birthday, email, phoneNumber);
        this.salary = salary;
        this.personalNumber = personalNumber;
        this.employedSince = employedSince;
        this.cinema = cinema;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(double personalNumber) {
        this.personalNumber = personalNumber;
    }

    public LocalDate getEmployedSince() {
        return employedSince;
    }

    public void setEmployedSince(LocalDate employedSince) {
        this.employedSince = employedSince;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema){this.cinema = cinema;}

    @Override
    public String toString() {
        return "Employee{" +
                "salary=" + salary +
                ", personalNumber=" + personalNumber +
                ", employedSince=" + employedSince +
                ", cinema=" + cinema +
                '}';
    }
}
