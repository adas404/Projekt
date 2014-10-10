/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import config.DBManager;
import entity.Obd2odczyt;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import sun.jdbc.odbc.OdbcDef;

/**
 *
 * @author Adaś
 */
public class PorownawczyBean extends Raport{

    /**
     * Creates a new instance of PorownawczyBean
     */
    public PorownawczyBean() {
    }
    public String dalej(){
        try{
            EntityManager em = DBManager.getManager().createEntityManager();
            em.getTransaction().begin();
            List<Obd2odczyt> list = em.createQuery("SELECT o FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", dataPoczatkowa).setParameter("datak", dataKoncowa).getResultList();
            for (Obd2odczyt x: list){
                System.out.println(x);
            }
            em.getTransaction().commit();
        }catch(NoResultException | ArrayIndexOutOfBoundsException | NullPointerException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Przykro nam!", "Nie znaleźliśmy przebytych tras w podanym terminie!"));
            return null;
        }
        return null;
    }
    
}
