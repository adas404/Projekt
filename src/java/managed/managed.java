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
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        String string = "MAXDATE,8GZ8564";
        String[] part = string.split(",");
        Car car = new Car();
            car.setId(null);
            System.out.println(part[1]);
            car.setVin(part[1]);
            em.persist(car);
            em.getTransaction().commit();
            em.close();
            
    }
    
}
