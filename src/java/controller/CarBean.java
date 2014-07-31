/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import config.DBManager;
import entity.Car;
import java.util.List;
import javax.persistence.EntityManager;

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
        EntityManager em = DBManager.getManager().createEntityManager();
        listaCar = em.createNamedQuery("Car.findAll").getResultList();
        em.close();
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
        System.out.println(car.getVin());
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        this.car = em.find(Car.class, car.getId());
        em.remove(this.car);
        this.car = new Car();
        em.getTransaction().commit();
        em.close();
    }
    public CarBean() {
    }
    
}
