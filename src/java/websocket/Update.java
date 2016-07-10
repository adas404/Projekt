/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package websocket;

import config.DBManager;
import entity.Car;
import entity.Obd2odczyt;
import entity.Pozycja;
import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Adam
 */
@ServerEndpoint("/update")
public class Update {

    @OnMessage
    public void onMessage(String message, Session session) throws InterruptedException, IOException, ParseException {
        // Print the client message for testing purposes
    String[] part = message.split(",");
    if (part[0].equals("MAXDATE")){
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        //int id = (Integer)em.createQuery("select max(c.id) from Car c").getSingleResult();
        Date date = (Date)em.createQuery("Select max(pozycja.data) from Pozycja pozycja JOIN pozycja.car car where car.vin=:vin ").setParameter("vin", part[1]).getSingleResult();
        if (date == null){
            Car car = new Car();
            car.setId(null);
            car.setVin(part[1]);
            em.persist(car);
            em.getTransaction().commit();
            em.close();
            session.getBasicRemote().sendText("0000-00-00 00:00:00");
            return;
        }
        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String string_date = format.format(date);
        //System.out.println("Data"+string_date);
        em.getTransaction().commit();
        em.close();
        session.getBasicRemote().sendText(string_date);
       }
    if (part[0].equals("DB")){
        if (part[1].equals("Pozycja")){
            Pozycja pozycja = new Pozycja();
            pozycja.setIdpozycja(null);
            pozycja.setLat(Double.parseDouble(part[3]));
            pozycja.setLng(Double.parseDouble(part[4]));
            pozycja.setWysokosc(Double.parseDouble(part[5]));
            pozycja.setNsind(part[6].charAt(0));
            pozycja.setEwind(part[7].charAt(0));
            pozycja.setData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(part[8]));
            EntityManager em = DBManager.getManager().createEntityManager();
            em.getTransaction().begin();
            Car car = (Car)em.createNamedQuery("Car.findByVin").setParameter("vin", part[2]).getSingleResult();
            pozycja.setCar(car);
            em.persist(pozycja);
            em.getTransaction().commit();
            em.close();
        }
        if (part[1].equals("Obd2odczyt")){
            Obd2odczyt obd2 = new Obd2odczyt();
            obd2.setId(null);
            obd2.setObciazenieSilnika(Integer.parseInt(part[3]));
            obd2.setTempChlodzacego(Integer.parseInt(part[4]));
            obd2.setCisnienieKolektora(Integer.parseInt(part[5]));
            obd2.setObroty(Double.parseDouble(part[6]));
            obd2.setPredkosc(Integer.parseInt(part[7]));
            obd2.setTempDolotu(Integer.parseInt(part[8]));
            obd2.setPolozeniePrzepustnicy(Integer.parseInt(part[9]));
            obd2.setData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(part[10]));
            EntityManager em = DBManager.getManager().createEntityManager();
            em.getTransaction().begin();
            Car car = (Car)em.createNamedQuery("Car.findByVin").setParameter("vin", part[2]).getSingleResult();
            obd2.setCar(car);
            em.persist(obd2);
            em.getTransaction().commit();
            em.close();
        }
    }
    // Send a final message to the client
        
    }
    @OnOpen
  public void onOpen() {
    System.out.println("Client connected");
  }

  @OnClose
  public void onClose() {
    System.out.println("Connection closed");
  }
    
}
