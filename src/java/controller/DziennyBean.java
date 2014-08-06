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
        try{
            EntityManager em = DBManager.getManager().createEntityManager();
            em.getTransaction().begin();
            long tmpdate = this.data.getTime()+86400000;
            List<Obd2odczyt> odczyt = em.createQuery("SELECT o FROM Obd2odczyt o JOIN o.car car WHERE car.vin=:vin AND o.data>=:data AND o.data<:dt").setParameter("vin", this.car.getVin()).setParameter("data", this.data).setParameter("dt", new Date(tmpdate)).getResultList();
            em.getTransaction().commit();
            em.close();
            pred.setLabel("Prędkość");
            for(Obd2odczyt x: odczyt){
                pred.set(x.getData(), x.getPredkosc());
                obroty.set(x.getData(), x.getObroty());
            }
            model.addSeries(pred);
            model.addSeries(obroty);
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
