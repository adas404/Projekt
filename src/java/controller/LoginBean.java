/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import config.DBManager;
import entity.Uzytkownik;
import java.awt.event.ActionEvent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
        try{
            EntityManager em = DBManager.getManager().createEntityManager();
            em.getTransaction().begin();
            Uzytkownik checkUser = (Uzytkownik) em.createQuery("SELECT u FROM Uzytkownik u WHERE u.login=:log AND u.haslo=:pass").setParameter("log", this.uzytkownik.getLogin()).setParameter("pass", this.uzytkownik.getHaslo()).getSingleResult();
            em.getTransaction().commit();
            em.close();
            HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("id", checkUser.getId());
            session.setAttribute("uzytkownik", checkUser.getLogin());
            session.setAttribute("imie", checkUser.getImie());
            session.setAttribute("nazwisko", checkUser.getNazwisko());
            session.setAttribute("typ", checkUser.getTyp());
            this.setNieZalogowany(false);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Zostałeś zalogowany!", "Witamy "+checkUser.getImie()+" "+checkUser.getNazwisko()));
        } catch(NoResultException e){
            checkUser = null;
            this.setNieZalogowany(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niepowodzenie logowania", "Błędne dane"));
        }
       return "home";
    }
    public String wyloguj(){
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        this.setNieZalogowany(true);
        return "home";
    }
    public LoginBean() {
    }
    
}
