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
@Table(name = "obd2odczyt")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Obd2odczyt.findAll", query = "SELECT o FROM Obd2odczyt o")})
public class Obd2odczyt implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "obciazenie_silnika", nullable = false)
    private int obciazenieSilnika;
    @Basic(optional = false)
    @NotNull
    @Column(name = "temp_chlodzacego", nullable = false)
    private int tempChlodzacego;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cisnienie_kolektora", nullable = false)
    private int cisnienieKolektora;
    @Basic(optional = false)
    @NotNull
    @Column(name = "obroty", nullable = false)
    private double obroty;
    @Basic(optional = false)
    @NotNull
    @Column(name = "predkosc", nullable = false)
    private int predkosc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "temp_dolotu", nullable = false)
    private int tempDolotu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "polozenie_przepustnicy", nullable = false)
    private int polozeniePrzepustnicy;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    @ManyToOne
    private Car car;

    public Obd2odczyt() {
    }

    public Obd2odczyt(Integer id) {
        this.id = id;
    }

    public Obd2odczyt(Integer id, int obciazenieSilnika, int tempChlodzacego, int cisnienieKolektora, double obroty, int predkosc, int tempDolotu, int polozeniePrzepustnicy) {
        this.id = id;
        this.obciazenieSilnika = obciazenieSilnika;
        this.tempChlodzacego = tempChlodzacego;
        this.cisnienieKolektora = cisnienieKolektora;
        this.obroty = obroty;
        this.predkosc = predkosc;
        this.tempDolotu = tempDolotu;
        this.polozeniePrzepustnicy = polozeniePrzepustnicy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getObciazenieSilnika() {
        return obciazenieSilnika;
    }

    public void setObciazenieSilnika(int obciazenieSilnika) {
        this.obciazenieSilnika = obciazenieSilnika;
    }

    public int getTempChlodzacego() {
        return tempChlodzacego;
    }

    public void setTempChlodzacego(int tempChlodzacego) {
        this.tempChlodzacego = tempChlodzacego;
    }

    public int getCisnienieKolektora() {
        return cisnienieKolektora;
    }

    public void setCisnienieKolektora(int cisnienieKolektora) {
        this.cisnienieKolektora = cisnienieKolektora;
    }

    public double getObroty() {
        return obroty;
    }

    public void setObroty(double obroty) {
        this.obroty = obroty;
    }

    public int getPredkosc() {
        return predkosc;
    }

    public void setPredkosc(int predkosc) {
        this.predkosc = predkosc;
    }

    public int getTempDolotu() {
        return tempDolotu;
    }

    public void setTempDolotu(int tempDolotu) {
        this.tempDolotu = tempDolotu;
    }

    public int getPolozeniePrzepustnicy() {
        return polozeniePrzepustnicy;
    }

    public void setPolozeniePrzepustnicy(int polozeniePrzepustnicy) {
        this.polozeniePrzepustnicy = polozeniePrzepustnicy;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Obd2odczyt)) {
            return false;
        }
        Obd2odczyt other = (Obd2odczyt) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Obd2odczyt[ id=" + id + " ]";
    }
    
}
