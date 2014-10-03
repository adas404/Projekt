/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import config.DBManager;
import entity.Car;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Adaś
 */
public class Raport {
    public Raport(){
        
    }
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
        } 
        return listaCar;
    }

    public void setListaCar(List<Car> listaCar) {
        this.listaCar = listaCar;
    }
    private List<Car> listaCar;
}
