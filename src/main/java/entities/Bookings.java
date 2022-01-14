/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author christianrosenbaek
 */
@Entity
@Table(name = "bookings")
@XmlRootElement
@NamedQuery(name = "Bookings.deleteAllRows", query = "DELETE from Bookings")
public class Bookings implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
    
    @Column(name = "duration")
    private Integer duration;
    
    @JoinTable(name = "booking_assistants", joinColumns = {
        @JoinColumn(name = "bookings_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "washing_assistants_id", referencedColumnName = "id")})
    @ManyToMany
    private List<WashingAssistants> washingAssistantsList = new ArrayList<>();
    
    @JoinColumn(name = "cars_id", referencedColumnName = "registration")
    @ManyToOne
    private Car car;

    public Bookings() {
    }

    public Bookings(Date dateTime, Integer duration) {
        this.dateTime = dateTime;
        this.duration = duration;
    }
    
    

    public Bookings(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<WashingAssistants> getWashingAssistantsList() {
        return washingAssistantsList;
    }

    public void setWashingAssistantsList(List<WashingAssistants> washingAssistantsList) {
        this.washingAssistantsList = washingAssistantsList;
    }

    public void addWashingAssistant(WashingAssistants washingAssistant) {
        this.washingAssistantsList.add(washingAssistant);
    }
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void removeCar() {
        this.car.getBookingsList().remove(this);
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bookings)) {
            return false;
        }
        Bookings other = (Bookings) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Bookings[ id=" + id + " ]";
    }
    
}
