/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author christianrosenbaek
 */
@Entity
@Table(name = "washing_assistants")
@XmlRootElement
@NamedQuery(name = "WashingAssistants.deleteAllRows", query = "DELETE from WashingAssistants")
public class WashingAssistants implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 45)
    @Column(name = "primary_language")
    private String primaryLanguage;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "experience")
    private Float experience;
    @Column(name = "rate")
    private Float rate;
    @ManyToMany(mappedBy = "washingAssistantsList")
    private List<Bookings> bookingsList;

    public WashingAssistants() {
    }

    public WashingAssistants(String name, String primaryLanguage, Float experience, Float rate) {
        this.name = name;
        this.primaryLanguage = primaryLanguage;
        this.experience = experience;
        this.rate = rate;
    }
    
    

    public WashingAssistants(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrimaryLanguage() {
        return primaryLanguage;
    }

    public void setPrimaryLanguage(String primaryLanguage) {
        this.primaryLanguage = primaryLanguage;
    }

    public Float getExperience() {
        return experience;
    }

    public void setExperience(Float experience) {
        this.experience = experience;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    @XmlTransient
    public List<Bookings> getBookingsList() {
        return bookingsList;
    }

    public void setBookingsList(List<Bookings> bookingsList) {
        this.bookingsList = bookingsList;
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
        if (!(object instanceof WashingAssistants)) {
            return false;
        }
        WashingAssistants other = (WashingAssistants) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.WashingAssistants[ id=" + id + " ]";
    }
    
}
