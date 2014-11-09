/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adam
 */
@Entity
@Table(name = "pozycja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pozycja.findAll", query = "SELECT p FROM Pozycja p")})
public class Pozycja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpozycja", nullable = false)
    private Integer idpozycja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lat", nullable = false)
    private double lat;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lng", nullable = false)
    private double lng;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wysokosc", nullable = false)
    private double wysokosc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nsind", nullable = false)
    private Character nsind;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ewind", nullable = false)
    private Character ewind;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    @ManyToOne
    private Car car;

    public Pozycja() {
    }

    public Pozycja(Integer idpozycja) {
        this.idpozycja = idpozycja;
    }

    public Pozycja(Integer idpozycja, double lat, double lng, double wysokosc, Character nsind, Character ewind, Date data) {
        this.idpozycja = idpozycja;
        this.lat = lat;
        this.lng = lng;
        this.wysokosc = wysokosc;
        this.nsind = nsind;
        this.ewind = ewind;
        this.data = data;
    }

    public Integer getIdpozycja() {
        return idpozycja;
    }

    public void setIdpozycja(Integer idpozycja) {
        this.idpozycja = idpozycja;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getWysokosc() {
        return wysokosc;
    }

    public void setWysokosc(double wysokosc) {
        this.wysokosc = wysokosc;
    }

    public Character getNsind() {
        return nsind;
    }

    public void setNsind(Character nsind) {
        this.nsind = nsind;
    }

    public Character getEwind() {
        return ewind;
    }

    public void setEwind(Character ewind) {
        this.ewind = ewind;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpozycja != null ? idpozycja.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pozycja)) {
            return false;
        }
        Pozycja other = (Pozycja) object;
        if ((this.idpozycja == null && other.idpozycja != null) || (this.idpozycja != null && !this.idpozycja.equals(other.idpozycja))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Pozycja[ idpozycja=" + idpozycja + " ]";
    }
    
}
