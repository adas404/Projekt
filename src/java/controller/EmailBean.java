/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import config.DBManager;
import config.UstawieniaMaila;
import entity.Log;
import entity.Uzytkownik;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Adaś
 */
public class EmailBean {

    /**
     * Creates a new instance of EmailBean
     */
    public EmailBean() {
    }
        @EJB
        UstawieniaMaila mail;

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    
    private Uzytkownik uzytkownik;
    
        
    public void wyslij(){
        mail.wyslijMail(uzytkownik.getEmail(), temat, body);
        EntityManager em = DBManager.getManager().createEntityManager();
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        em.getTransaction().begin();
        Log log = new Log();
        log.setTyp(5);
        log.setData(new Date());
        log.setNotatka("Wysłano email do "+this.uzytkownik.getImie()+" "+this.uzytkownik.getNazwisko()+" o temacie "+temat);
        log.setUzytkownik(em.find(Uzytkownik.class, session.getAttribute("id")));
        em.persist(log);
        em.getTransaction().commit();
        em.close();
    }

    public List<Uzytkownik> completeUzytkownik(String query){
        EntityManager em = DBManager.getManager().createEntityManager();
        List<Uzytkownik> listaUzytkownik = em.createNamedQuery("Uzytkownik.findAll").getResultList();
        em.close();
        List<Uzytkownik> odfiltrowana = new ArrayList<Uzytkownik>();
        for (int i=0;i < listaUzytkownik.size();i++){
            Uzytkownik user = listaUzytkownik.get(i);
            if(user.getImie().toLowerCase().startsWith(query)){
                odfiltrowana.add(user);
            }
        }
        return odfiltrowana;
    }
    public String getTemat() {
        return temat;
    }

    public void setTemat(String temat) {
        this.temat = temat;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
        private String temat;
        private String body;
        
    
}
