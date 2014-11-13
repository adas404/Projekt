/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import config.DBManager;
import entity.Log;
import entity.Uzytkownik;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Adam
 */
public class LogowBean extends Raport {

    /**
     * Creates a new instance of LogowBean
     */
    public LogowBean() {
    }

    public List<Log> getLogList() {
        return logList;
    }

    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }
    private List<Log> logList = new ArrayList<Log>();
    public String generuj(){
        try{
            HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            EntityManager em = DBManager.getManager().createEntityManager();
            logList = em.createQuery("SELECT l FROM Log l WHERE l.data>=:datap AND l.data<=:datak").setParameter("datap", this.dataPoczatkowa).setParameter("datak", this.dataKoncowa).getResultList();
            this.log(4,"Administrator wygenerował raport logów" , em.find(Uzytkownik.class, session.getAttribute("id")));
            em.close();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Udało się!", "Oto dzienny raport wygenerowany specjalnie dla Ciebie"));
        } catch(NoResultException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Przykro nam!", "Nie znaleźliśmy przebytych tras w podanym terminie!"));
            return null;
        }
        return "logow";
    }
    
}
