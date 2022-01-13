/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author christianrosenbaek
 */
@Entity
@Table(name = "cars")
@XmlRootElement
@NamedQuery(name = "Car.deleteAllRows", query = "DELETE from Car")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "registration", length = 25)
    private String registration;
    
    @Size(max = 45)
    @Column(name = "brand")
    private String brand;
    
    @Size(max = 45)
    @Column(name = "make")
    private String make;
    
    @Column(name = "year")
    private Integer year;
    
    @JoinColumn(name = "users_user_name", referencedColumnName = "user_name")
    @ManyToOne
    private User user;
    
    @OneToMany(mappedBy = "car")
    private List<Bookings> bookingsList = new ArrayList<>();

    public Car() {
    }

    public Car(String registration, String brand, String make, Integer year) {
        this.registration = registration;
        this.brand = brand;
        this.make = make;
        this.year = year;
    }
    
    

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @XmlTransient
    public List<Bookings> getBookingsList() {
        return bookingsList;
    }

    public void setBookingsList(List<Bookings> bookingsList) {
        this.bookingsList = bookingsList;
    }
    
    public void addBooking(Bookings booking) {
        this.bookingsList.add(booking);
        booking.setCar(this);
    }
   

}
