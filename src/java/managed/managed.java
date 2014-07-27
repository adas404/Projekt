/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managed;

import config.DBManager;
import entity.Car;
import entity.Pozycja;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Adam
 */
public class managed {

    /**
     * Creates a new instance of managed
     */
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    private Car car = new Car();
    public managed() {
    }
    public void wypisz() throws ParseException{
        String string = "DB,Pozycja,1K80441,50.883578,20.650737,333.9,N,E,2014-07-24 12:17:50";
        String[] part = string.split(",");
       Pozycja pozycja = new Pozycja();
            pozycja.setIdpozycja(null);
            pozycja.setLat(Double.parseDouble(part[3]));
            pozycja.setLng(Double.parseDouble(part[4]));
            pozycja.setWysokosc(Double.parseDouble(part[5]));
            pozycja.setNsind(part[6].charAt(0));
            pozycja.setWysokosc(part[7].charAt(0));
            pozycja.setData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(part[8]));
            EntityManager em = DBManager.getManager().createEntityManager();
            em.getTransaction().begin();
            System.out.println(part[2]);
            Car car = (Car)em.createNamedQuery("Car.findByVin").setParameter("vin", part[2]).getSingleResult();
            System.out.println(car);
            pozycja.setCar(car);
           // pozycja.setCar(car);
            em.persist(pozycja);
            em.getTransaction().commit();
            em.close();
    }
    
}
