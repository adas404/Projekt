/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import config.DBManager;
import entity.Log;
import entity.Uzytkownik;
import java.awt.event.ActionEvent;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Adam
 */
public class LoginBean {
    private Uzytkownik checkUser;

    /**
     * Creates a new instance of LoginBean
     */
    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    private Uzytkownik uzytkownik = new Uzytkownik();

    private boolean nieZalogowany = true;

    public boolean isNieZalogowany() {
        return nieZalogowany;
    }

    public void setNieZalogowany(boolean nieZalogowany) {
        this.nieZalogowany = nieZalogowany;
    }
    
    public String sprawdz(){
        EntityManager em = DBManager.getManager().createEntityManager();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try{
            em.setFlushMode(FlushModeType.COMMIT);
            Uzytkownik checkUser = (Uzytkownik) em.createQuery("SELECT u FROM Uzytkownik u WHERE u.login=:log AND u.haslo=:pass").setParameter("log", this.uzytkownik.getLogin()).setParameter("pass", this.uzytkownik.getHaslo()).getSingleResult();
            HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);  
            session.setAttribute("id", checkUser.getId());
            session.setAttribute("uzytkownik", checkUser.getLogin());
            session.setAttribute("imie", checkUser.getImie());
            session.setAttribute("nazwisko", checkUser.getNazwisko());
            session.setAttribute("typ", checkUser.getTyp());
            em.getTransaction().begin();
            Log log = new Log();
            log.setTyp(1);
            log.setUzytkownik(checkUser);
            log.setData(new Date());
            log.setNotatka("Użytkownik "+checkUser.getImie()+" "+ checkUser.getNazwisko()+" zalogował się z IP: "+request.getRemoteAddr());
            em.persist(log);
            em.getTransaction().commit();
            this.setNieZalogowany(false);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Zostałeś zalogowany!", "Witamy "+checkUser.getImie()+" "+checkUser.getNazwisko()));
            em.close();
        } catch(NoResultException e){
            checkUser = null;
            this.setNieZalogowany(true);
            em.getTransaction().begin();
            Log log = new Log();
            log.setTyp(1);
            log.setData(new Date());
            log.setNotatka("Nie udana próba logowania z adresu: "+ request.getRemoteAddr());
            em.persist(log);
            em.getTransaction().commit();
            em.close();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niepowodzenie logowania", "Błędne dane"));
        }
       return "home";
    }
    public String wyloguj(){
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        Log log = new Log();
        log.setTyp(2);
        log.setData(new Date());
        log.setNotatka("Nastąpiło wylogowanie z systemu");
        log.setUzytkownik(em.find(Uzytkownik.class, session.getAttribute("id")));
        em.persist(log);
        em.getTransaction().commit();
        em.close();
        session.invalidate();
        this.setNieZalogowany(true);
        return "home";
    }
    public LoginBean() {
    }
    
}
