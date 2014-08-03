/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package config;

import entity.Car;
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
public class CarConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Integer i = Integer.valueOf(string);
        EntityManager em = DBManager.getManager().createEntityManager();
        Car p = em.find(Car.class, i);
        em.close();
        return p;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (! (o instanceof Car))
            throw new ConverterException(new FacesMessage("błąd"));
        Car p = (Car)o;
        //System.out.println(p.getId().toString());
        return (o != null) ? p.getId().toString() : null;
    }

}
