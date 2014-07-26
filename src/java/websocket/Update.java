/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package websocket;

import config.DBManager;
import entity.Car;
import entity.Pozycja;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
    public void onMessage(String message, Session session) throws InterruptedException, IOException {
        // Print the client message for testing purposes
    System.out.println("Received: " + message);
    String[] part = message.split(",");
    if (part[0].equals("MAXDATE")){
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        Car car = new Car();
        car.setMarka("costam");
        car.setModel("jakis");
        car.setVin("fdsfs");
       // em.persist(car);
      //  em.getTransaction().commit();
        int id = (Integer)em.createQuery("select max(c.id) from Car c").getSingleResult();
        Date date = (Date)em.createQuery("Select max(pozycja.data) from Pozycja pozycja JOIN pozycja.car car where car.vin=:vin ").setParameter("vin", part[1]).getSingleResult();
        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String string_date = format.format(date);
        //System.out.println("Data"+string_date);
        em.close();
        System.out.println(string_date);
        session.getBasicRemote().sendText(string_date);
       }
    if (part[0].equals("DB")){
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        
        em.getTransaction().commit();
        em.close();
    }
    // Send a final message to the client
    session.getBasicRemote().sendText("STOP");
        
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
