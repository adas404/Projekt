/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import config.DBManager;
import entity.Car;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Adam
 */
public class CarBean {

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
    private Car car = new Car();
    
    public List<Car> getListaCar() {
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        int typ = (Integer)session.getAttribute("typ");
        int id =  (Integer)session.getAttribute("id");
        Car tmp = new Car();
        tmp.setId(9999);
        if (typ == 2){
            EntityManager em = DBManager.getManager().createEntityManager();
            listaCar = em.createNamedQuery("Car.findAll").getResultList();
            em.close();
            }
        else if(typ == 1){
            EntityManager em = DBManager.getManager().createEntityManager();
            em.getTransaction().begin();
            listaCar = em.createQuery("SELECT c FROM Car c JOIN c.uzytkownik uzytkownik WHERE uzytkownik.id=:uz").setParameter("uz", id).getResultList();
            em.getTransaction().commit();
            em.close();
            System.out.println(listaCar);
        } if(listaCar.size() == 2)           
            listaCar.add(tmp);
        return listaCar;
    }

    public void setListaCar(List<Car> listaCar) {
        this.listaCar = listaCar;
    }
    private List<Car> listaCar;
    /**
     * Creates a new instance of CarBean
     */
    public void usun(){
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        this.car = em.find(Car.class, car.getId());
        em.remove(this.car);
        this.car = new Car();
        em.getTransaction().commit();
        em.close();
    }
    public void edytuj(){
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        em.merge(this.car);
        em.getTransaction().commit();
        em.close();
        this.car = new Car();
    }
    public void dodaj(){
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        car.setId(null);
        em.persist(this.car);
        em.getTransaction().commit();
        em.close();
        this.car = new Car();
    }
    public Car przygotuj(){
        car = new Car();
        return car;
    }
    public CarBean() {
    }
    
}
