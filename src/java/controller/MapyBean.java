/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import config.DBManager;
import entity.Car;
import entity.Pozycja;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;

/**
 *
 * @author Adam
 */
public class MapyBean {

    /**
     * Creates a new instance of MapyBean
     * @return 
     */
    public Pozycja getPozycja() {
        return pozycja;
    }

    public void setPozycja(Pozycja pozycja) {
        this.pozycja = pozycja;
    }

    public Date getDataPoczatkowa() {
        return dataPoczatkowa;
    }

    public void setDataPoczatkowa(Date dataPoczatkowa) {
        this.dataPoczatkowa = dataPoczatkowa;
    }

    public Date getDataKoncowa() {
        return dataKoncowa;
    }

    public void setDataKoncowa(Date dataKoncowa) {
        this.dataKoncowa = dataKoncowa;
    }
    private Date dataPoczatkowa;
    private Date dataKoncowa;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    private Car car;
    private Pozycja pozycja= new Pozycja();
    public List<Car> getListaCar() {
        EntityManager em = DBManager.getManager().createEntityManager();
        listaCar = em.createNamedQuery("Car.findAll").getResultList();
        em.close();
        System.out.println(listaCar);
        return listaCar;
    }

    public void setListaCar(List<Car> listaCar) {
        this.listaCar = listaCar;
    }
    private List<Car> listaCar;
    public String wyszukaj(){
        EntityManager em = DBManager.getManager().createEntityManager();
        System.out.println(dataPoczatkowa);
        System.out.println(car.getId());
        em.getTransaction().begin();
        List<Pozycja> list = em.createQuery("SELECT p FROM Pozycja p JOIN p.car car WHERE car.vin=:vin AND p.data>=:datap AND p.data<=:datak").setParameter("vin", car.getVin()).setParameter("datap", dataPoczatkowa).setParameter("datak", dataKoncowa).getResultList();
        System.out.println(list);
        return null;
    }
    public void wypisz(){
        System.out.println("coś");
    }
    public MapyBean() {
    }
    
}
