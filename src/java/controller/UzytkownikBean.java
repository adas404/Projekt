/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import config.DBManager;
import entity.Uzytkownik;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Adam
 */
public class UzytkownikBean {

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    Uzytkownik uzytkownik = new Uzytkownik();
    
    public List<Uzytkownik> getListaUzytkownik() {
        EntityManager em = DBManager.getManager().createEntityManager();
        listaUzytkownik = em.createNamedQuery("Uzytkownik.findAll").getResultList();
        em.close();
        return listaUzytkownik;
    }

    public void setListaUzytkownik(List<Uzytkownik> listaUzytkownik) {
        this.listaUzytkownik = listaUzytkownik;
    }
    private List<Uzytkownik> listaUzytkownik;
    /**
     * Creates a new instance of CarBean
     */
    public void usun(){
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        this.uzytkownik = em.find(Uzytkownik.class, uzytkownik.getId());
        em.remove(this.uzytkownik);
        this.uzytkownik = new Uzytkownik();
        em.getTransaction().commit();
        em.close();
    }
    public void edytuj(){
        System.out.println(uzytkownik.getImie());
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        em.merge(this.uzytkownik);
        em.getTransaction().commit();
        em.close();
        this.uzytkownik = new Uzytkownik();
    }
    public UzytkownikBean() {
    }
    
}
