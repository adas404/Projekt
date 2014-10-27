/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package others;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Adam
 */
public class Tygodniowy {
 public Tygodniowy(){
     
 }   
 private double sredniaPredkosc;
 private int maksymalnaPredkosc;
 private int minimalnaPredkosc;
 private double srednieObroty;
 private double maksymalneObroty;
 private double minimalneObroty;
 private double srednieObciazenieSilnika;
 private int maksymalneObciazenieSilnika;
 private Date czasJazdy;
 private double iloscKilometrow;
 private List<Trasa> trasy = new ArrayList<Trasa>();
 private Date data;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
 
    public Date getCzasJazdy() {
        return czasJazdy;
    }

    public List<Trasa> getTrasy() {
        return trasy;
    }

    public void setTrasy(List<Trasa> trasy) {
        this.trasy = trasy;
    }

    public void setCzasJazdy(Date czasJazdy) {
        this.czasJazdy = czasJazdy;
    }

    public double getIloscKilometrow() {
        return iloscKilometrow;
    }

    public void setIloscKilometrow(double iloscKilometrow) {
        this.iloscKilometrow = iloscKilometrow;
    }

    public double getSredniaPredkosc() {
        return sredniaPredkosc;
    }

    public void setSredniaPredkosc(double sredniaPredkosc) {
        this.sredniaPredkosc = sredniaPredkosc;
    }

    public int getMaksymalnaPredkosc() {
        return maksymalnaPredkosc;
    }

    public void setMaksymalnaPredkosc(int maksymalnaPredkosc) {
        this.maksymalnaPredkosc = maksymalnaPredkosc;
    }

    public int getMinimalnaPredkosc() {
        return minimalnaPredkosc;
    }

    public void setMinimalnaPredkosc(int minimalnaPredkosc) {
        this.minimalnaPredkosc = minimalnaPredkosc;
    }

    public double getSrednieObroty() {
        return srednieObroty;
    }

    public void setSrednieObroty(double srednieObroty) {
        this.srednieObroty = srednieObroty;
    }

    public double getMaksymalneObroty() {
        return maksymalneObroty;
    }

    public void setMaksymalneObroty(double maksymalneObroty) {
        this.maksymalneObroty = maksymalneObroty;
    }

    public double getMinimalneObroty() {
        return minimalneObroty;
    }

    public void setMinimalneObroty(double minimalneObroty) {
        this.minimalneObroty = minimalneObroty;
    }

    public double getSrednieObciazenieSilnika() {
        return srednieObciazenieSilnika;
    }

    public void setSrednieObciazenieSilnika(double srednieObciazenieSilnika) {
        this.srednieObciazenieSilnika = srednieObciazenieSilnika;
    }

    public int getMaksymalneObciazenieSilnika() {
        return maksymalneObciazenieSilnika;
    }

    public void setMaksymalneObciazenieSilnika(int maksymalneObciazenieSilnika) {
        this.maksymalneObciazenieSilnika = maksymalneObciazenieSilnika;
    }

    public int getMinimalneObciazenieSilnika() {
        return minimalneObciazenieSilnika;
    }

    public void setMinimalneObciazenieSilnika(int minimalneObciazenieSilnika) {
        this.minimalneObciazenieSilnika = minimalneObciazenieSilnika;
    }

    public double getSredniaTempChlodzacego() {
        return sredniaTempChlodzacego;
    }

    public void setSredniaTempChlodzacego(double sredniaTempChlodzacego) {
        this.sredniaTempChlodzacego = sredniaTempChlodzacego;
    }

    public int getMaksymalnaTempChlodzacego() {
        return maksymalnaTempChlodzacego;
    }

    public void setMaksymalnaTempChlodzacego(int maksymalnaTempChlodzacego) {
        this.maksymalnaTempChlodzacego = maksymalnaTempChlodzacego;
    }

    public int getMinimalnaTempChlodzacego() {
        return minimalnaTempChlodzacego;
    }

    public void setMinimalnaTempChlodzacego(int minimalnaTempChlodzacego) {
        this.minimalnaTempChlodzacego = minimalnaTempChlodzacego;
    }

    public double getSrednieCisnienieKolektora() {
        return srednieCisnienieKolektora;
    }

    public void setSrednieCisnienieKolektora(double srednieCisnienieKolektora) {
        this.srednieCisnienieKolektora = srednieCisnienieKolektora;
    }

    public int getMaksymalneCisnienieKolektora() {
        return maksymalneCisnienieKolektora;
    }

    public void setMaksymalneCisnienieKolektora(int maksymalneCisnienieKolektora) {
        this.maksymalneCisnienieKolektora = maksymalneCisnienieKolektora;
    }

    public int getMinimalneCisnienieKolektora() {
        return minimalneCisnienieKolektora;
    }

    public void setMinimalneCisnienieKolektora(int minimalneCisnienieKolektora) {
        this.minimalneCisnienieKolektora = minimalneCisnienieKolektora;
    }

    public double getSredniatemDolotu() {
        return sredniatemDolotu;
    }

    public void setSredniatemDolotu(double sredniatemDolotu) {
        this.sredniatemDolotu = sredniatemDolotu;
    }

    public int getMaksymalnaTempDolotu() {
        return maksymalnaTempDolotu;
    }

    public void setMaksymalnaTempDolotu(int maksymalnaTempDolotu) {
        this.maksymalnaTempDolotu = maksymalnaTempDolotu;
    }

    public int getMinimalnaTempDolotu() {
        return minimalnaTempDolotu;
    }

    public void setMinimalnaTempDolotu(int minimalnaTempDolotu) {
        this.minimalnaTempDolotu = minimalnaTempDolotu;
    }

    public double getSredniePolozeniePrzepustnicy() {
        return sredniePolozeniePrzepustnicy;
    }

    public void setSredniePolozeniePrzepustnicy(double sredniePolozeniePrzepustnicy) {
        this.sredniePolozeniePrzepustnicy = sredniePolozeniePrzepustnicy;
    }

    public int getMaksymalnePolozeniePrzepustnicy() {
        return maksymalnePolozeniePrzepustnicy;
    }

    public void setMaksymalnePolozeniePrzepustnicy(int maksymalnePolozeniePrzepustnicy) {
        this.maksymalnePolozeniePrzepustnicy = maksymalnePolozeniePrzepustnicy;
    }

    public int getMinimalnePolozeniePrzepustnicy() {
        return minimalnePolozeniePrzepustnicy;
    }

    public void setMinimalnePolozeniePrzepustnicy(int minimalnePolozeniePrzepustnicy) {
        this.minimalnePolozeniePrzepustnicy = minimalnePolozeniePrzepustnicy;
    }
 private int minimalneObciazenieSilnika;
 private double sredniaTempChlodzacego;
 private int maksymalnaTempChlodzacego;
 private int minimalnaTempChlodzacego;
 private double srednieCisnienieKolektora;
 private int maksymalneCisnienieKolektora;
 private int minimalneCisnienieKolektora;
 private double sredniatemDolotu;
 private int maksymalnaTempDolotu;
 private int minimalnaTempDolotu;
 private double sredniePolozeniePrzepustnicy;
 private int maksymalnePolozeniePrzepustnicy;
 private int minimalnePolozeniePrzepustnicy;
}
