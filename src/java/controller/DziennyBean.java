/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import config.DBManager;
import entity.Car;
import entity.Obd2odczyt;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;


/**
 *
 * @author Adam
 */
public class DziennyBean {

    /**
     * Creates a new instance of DziennyBean
     */
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    private Date data;
    
    private Car car;
    private double sredniaPredkosc;

    public double getSredniaPredkosc() {
        return sredniaPredkosc;
    }
    private double srednieObroty;
    private double srednieObciazenie;

    

    public double getSrednieObroty() {
        return srednieObroty;
    }

    public double getSrednieObciazenie() {
        return srednieObciazenie;
    }

    public double getSredniaTempChlodzacego() {
        return sredniaTempChlodzacego;
    }

    public double getSrednieCisnienie() {
        return srednieCisnienie;
    }

    public double getSredniaTempDolotu() {
        return sredniaTempDolotu;
    }

    public double getSredniePolozeniePrzepustnicy() {
        return sredniePolozeniePrzepustnicy;
    }

    
    private double sredniaTempChlodzacego;
    private double srednieCisnienie;
    private double sredniaTempDolotu;
    private double sredniePolozeniePrzepustnicy;
    public CartesianChartModel getModel() {
        return model;
    }

    public void setModel(CartesianChartModel model) {
        this.model = model;
    }
    
    private CartesianChartModel model;
    
    public List<Car> getListaCar() {
        EntityManager em = DBManager.getManager().createEntityManager();
        listaCar = em.createNamedQuery("Car.findAll").getResultList();
        em.close();
        return listaCar;
    }

    public void setListaCar(List<Car> listaCar) {
        this.listaCar = listaCar;
    }
    private List<Car> listaCar;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
    
    private List<List<Obd2odczyt>> listaOdczytow;

    public List<List<Obd2odczyt>> getListaOdczytow() {
        return listaOdczytow;
    }

    public void setListaOdczytow(List<List<Obd2odczyt>> listaOdczytow) {
        this.listaOdczytow = listaOdczytow;
    }
    public String generuj(){
        model = new CartesianChartModel();
        Date j = new Date();
        ChartSeries pred = new ChartSeries();
        ChartSeries obroty = new ChartSeries();
        ChartSeries obciazenieSilnika = new ChartSeries();
        ChartSeries tempChlodzacego = new ChartSeries();
        ChartSeries cisnienieKolektora = new ChartSeries();
        ChartSeries tempDolotu = new ChartSeries();
        ChartSeries polozeniePrzepustnicy = new ChartSeries();
        try{
            EntityManager em = DBManager.getManager().createEntityManager();
            em.getTransaction().begin();
            long tmpdate = this.data.getTime()+86400000;
            List<Obd2odczyt> odczyt = em.createQuery("SELECT o FROM Obd2odczyt o JOIN o.car car WHERE car.vin=:vin AND o.data>=:data AND o.data<:dt").setParameter("vin", this.car.getVin()).setParameter("data", this.data).setParameter("dt", new Date(tmpdate)).getResultList();
            em.getTransaction().commit();
            pred.setLabel("Prędkość");
            obciazenieSilnika.setLabel("Obciążenie silnika (%)");
            tempChlodzacego.setLabel("Temperatura płynu chłodzącego");
            cisnienieKolektora.setLabel("Ciśnienie kolektora (KPa)");
            tempDolotu.setLabel("Temperatura dolotu");
            polozeniePrzepustnicy.setLabel("Położenie przepustnicy (%)");
            obroty.setLabel("Obroty");
            for(Obd2odczyt x: odczyt){
                pred.set(x.getData().getTime(), x.getPredkosc());
                obroty.set(x.getData().getTime(), x.getObroty());
                obciazenieSilnika.set(x.getData().getTime(), x.getObciazenieSilnika());
                tempChlodzacego.set(x.getData().getTime(), x.getTempChlodzacego());
                cisnienieKolektora.set(x.getData().getTime(), x.getCisnienieKolektora());
                tempDolotu.set(x.getData().getTime(), x.getTempDolotu());
                polozeniePrzepustnicy.set(x.getData().getTime(), x.getPolozeniePrzepustnicy());
                obroty.set(x.getData().getTime(), x.getObroty());
            }
            model.addSeries(pred);
            model.addSeries(obciazenieSilnika);
            model.addSeries(tempChlodzacego);
            model.addSeries(cisnienieKolektora);
            model.addSeries(tempDolotu);
            model.addSeries(polozeniePrzepustnicy);
            em.getTransaction().begin();
            sredniaPredkosc = (Double)em.createQuery("SELECT AVG(o.predkosc) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=:vin AND o.data>=:data AND o.data<:dt").setParameter("vin", this.car.getVin()).setParameter("data", this.data).setParameter("dt", new Date(tmpdate)).getSingleResult();
            System.out.println(sredniaPredkosc);
            em.getTransaction().commit();
            em.close();
        }catch(NoResultException e){
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Przykro nam!", "Nie znaleźliśmy przebytych tras w podanym terminie!"));
           return null;
        }catch(ArrayIndexOutOfBoundsException a){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Przykro nam!", "Nie znaleźliśmy przebytych tras w podanym terminie!"));
            return null;
        }
        return "dzienny";
    }
    public DziennyBean() {
    }
    
}
