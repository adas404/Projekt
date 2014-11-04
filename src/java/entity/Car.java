/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Adam
 */
@Entity
@Table(name = "car", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"VIN"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Car.findAll", query = "SELECT c FROM Car c"),
    @NamedQuery(name = "Car.findById", query = "SELECT c FROM Car c WHERE c.id = :id"),
    @NamedQuery(name = "Car.findByVin", query = "SELECT c FROM Car c WHERE c.vin = :vin"),
    @NamedQuery(name = "Car.findByMarka", query = "SELECT c FROM Car c WHERE c.marka = :marka"),
    @NamedQuery(name = "Car.findByModel", query = "SELECT c FROM Car c WHERE c.model = :model"),
    @NamedQuery(name = "Car.findByPojemnosc", query = "SELECT c FROM Car c WHERE c.pojemnosc = :pojemnosc")})
public class Car implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "VIN", nullable = false, length = 7)
    private String vin;
    @Size(max = 20)
    @Column(name = "marka", length = 20)
    private String marka;
    @Size(max = 20)
    @Column(name = "model", length = 20)
    private String model;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pojemnosc", precision = 22)
    private Double pojemnosc;
    @JoinColumn(name = "id_uzytkownik", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Uzytkownik uzytkownik;
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    private Set<Pozycja> pozycjaSet;
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    private Set<Obd2odczyt> obd2odczytSet;

    public Car() {
    }

    public Car(Integer id) {
        this.id = id;
    }

    public Car(Integer id, String vin) {
        this.id = id;
        this.vin = vin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPojemnosc() {
        return pojemnosc;
    }

    public void setPojemnosc(Double pojemnosc) {
        this.pojemnosc = pojemnosc;
    }


    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    @XmlTransient
    public Set<Pozycja> getPozycjaSet() {
        return pozycjaSet;
    }

    public void setPozycjaSet(Set<Pozycja> pozycjaSet) {
        this.pozycjaSet = pozycjaSet;
    }

    @XmlTransient
    public Set<Obd2odczyt> getObd2odczytSet() {
        return obd2odczytSet;
    }

    public void setObd2odczytSet(Set<Obd2odczyt> obd2odczytSet) {
        this.obd2odczytSet = obd2odczytSet;
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
        if (!(object instanceof Car)) {
            return false;
        }
        Car other = (Car) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Car[ id=" + id + " ]";
    }

    
}
