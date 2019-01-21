package at.htl.cinemamanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@NamedQuery(name = "Customer.findAll", query = "select c from Customer c")
public class Customer extends Person {
    private double customerNumber;
    private int bonuspoints;

    @ManyToMany(mappedBy = "customers", fetch = FetchType.EAGER)
    @JsonbTransient
    private List<Presentation> presentations;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String address, LocalDate birthday, String email, String phoneNumber, double customerNumber, int bonuspoints) {
        super(firstName, lastName, address, birthday, email, phoneNumber);
        this.customerNumber = customerNumber;
        this.bonuspoints = bonuspoints;
    }

    public double getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(double customerNumber) {
        this.customerNumber = customerNumber;
    }

    public int getBonuspoints() {
        return bonuspoints;
    }

    public void setBonuspoints(int bonuspoints) {
        this.bonuspoints = bonuspoints;
    }

    public List<Presentation> getPresentations() {
        return presentations;
    }

    public void setPresentations(List<Presentation> presentations) {
        this.presentations = presentations;
    }
}
