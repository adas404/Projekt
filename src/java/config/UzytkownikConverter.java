/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package config;

import entity.Uzytkownik;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;



/**
 *
 * @author Adam
 */
public class UzytkownikConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Integer i = Integer.valueOf(string);
        EntityManager em = DBManager.getManager().createEntityManager();
        Uzytkownik p = em.find(Uzytkownik.class, i);
        em.close();
        return p;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (! (o instanceof Uzytkownik))
            throw new ConverterException(new FacesMessage("błąd"));
        Uzytkownik p = (Uzytkownik)o;
        return p.getId().toString();
    }

}
