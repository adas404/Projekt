/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adam
 */
@Entity
@Table(name = "wiadomosc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wiadomosc.findAll", query = "SELECT w FROM Wiadomosc w"),
    @NamedQuery(name = "Wiadomosc.findById", query = "SELECT w FROM Wiadomosc w WHERE w.id = :id"),
    @NamedQuery(name = "Wiadomosc.findByTemat", query = "SELECT w FROM Wiadomosc w WHERE w.temat = :temat")})
public class Wiadomosc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "temat", nullable = false, length = 50)
    private String temat;
    @Lob
    @Size(max = 65535)
    @Column(name = "tresc", length = 65535)
    private String tresc;
    @JoinColumn(name = "id_uzytkownik_to", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Uzytkownik uzytkownik;
    @JoinColumn(name = "id_uzytkownik_from", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Uzytkownik uzytkownik1;

    public Wiadomosc() {
    }

    public Wiadomosc(Integer id) {
        this.id = id;
    }

    public Wiadomosc(Integer id, String temat) {
        this.id = id;
        this.temat = temat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemat() {
        return temat;
    }

    public void setTemat(String temat) {
        this.temat = temat;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public Uzytkownik getUzytkownik1() {
        return uzytkownik1;
    }

    public void setUzytkownik1(Uzytkownik uzytkownik1) {
        this.uzytkownik1 = uzytkownik1;
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
        if (!(object instanceof Wiadomosc)) {
            return false;
        }
        Wiadomosc other = (Wiadomosc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Wiadomosc[ id=" + id + " ]";
    }
    
}
