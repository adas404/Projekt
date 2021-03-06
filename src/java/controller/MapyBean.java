/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import config.DBManager;
import entity.Pozycja;
import entity.Uzytkownik;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;

/**
 *
 * @author Adam
 */
public class MapyBean extends Raport {

    /**
     * Creates a new instance of MapyBean
     * @return 
     */
    public Marker getMarker() {
        return marker;
    }
    public void wyborMarkera(OverlaySelectEvent event){
        marker = (Marker) event.getOverlay();
    }
    private Marker marker;
    private MapModel model;

    public MapModel getModel() {
        return model;
    }
    public Pozycja getPozycja() {
        return pozycja;
    }

    public void setPozycja(Pozycja pozycja) {
        this.pozycja = pozycja;
    }


    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }
    private String center;
    private Pozycja pozycja= new Pozycja();

    public String wyszukaj(){
        List<Polyline> listaPolinii = new ArrayList<Polyline>();
        Polyline polynie = new Polyline();
        Date j = new Date();
        String kolory[] = {"blue","red","orange","green","brown","black"};
        Random generator = new Random();
        try{
            EntityManager em = DBManager.getManager().createEntityManager();
            em.getTransaction().begin();
            List<Pozycja> list = em.createQuery("SELECT p FROM Pozycja p JOIN p.car car WHERE car.vin=:vin AND p.data>=:datap AND p.data<=:datak").setParameter("vin", car.getVin()).setParameter("datap", dataPoczatkowa).setParameter("datak", dataKoncowa).getResultList();
            em.getTransaction().commit();
        
            for (Pozycja x : list){
                if (j != null){
                    long i = abs((j.getTime() - x.getData().getTime())/1000);
                    if (i>12 && polynie.getPaths().size() !=0){
                        listaPolinii.add(polynie);
                        polynie = new Polyline();
                    } 
                }
                j = x.getData();
                polynie.getPaths().add(new LatLng(x.getLat(),x.getLng()));
            }
        
            listaPolinii.add(polynie);
            for (Polyline x : listaPolinii){
 //           model.addOverlay(new Marker(x.getPaths().get(0)));
                List<Date> data = em.createQuery("SELECT p.data FROM Pozycja p JOIN p.car car WHERE car.vin=:vin AND p.lat=:lat AND p.lng=:lng").setParameter("vin", car.getVin()).setParameter("lat", x.getPaths().get(x.getPaths().size()-1).getLat()).setParameter("lng", x.getPaths().get(x.getPaths().size()-1).getLng()).getResultList();
                model.addOverlay(new Marker(x.getPaths().get(x.getPaths().size()-1),"Stop","Stop trasy: "+data.get(data.size()-1).toString(),"/images/stop_marker.png"));               
                data = em.createQuery("SELECT p.data FROM Pozycja p JOIN p.car car WHERE car.vin=:vin AND p.lat=:lat AND p.lng=:lng").setParameter("vin", car.getVin()).setParameter("lat", x.getPaths().get(0).getLat()).setParameter("lng", x.getPaths().get(0).getLng()).getResultList();
                model.addOverlay(new Marker(x.getPaths().get(0),"Start","Start trasy: "+data.get(0).toString(),"/images/start_marker.png"));
                x.setStrokeColor(kolory[generator.nextInt(5)]);
                x.setStrokeWeight(5);
                x.setStrokeOpacity(0.7);
               model.addOverlay(x);
           
            }

            center = list.get(0).getLat()+","+list.get(0).getLng();
            HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            String notatka = "Wygenerowano mapę z okresu "+this.dataPoczatkowa+" do "+this.dataKoncowa+" dla samochodu: "+this.car.getMarka()+" "+this.car.getModel();
            this.log(3, notatka , em.find(Uzytkownik.class, session.getAttribute("id")));
            em.close();
        }   catch(NoResultException e){
                return null;
        }   catch(ArrayIndexOutOfBoundsException a){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Przykro nam!", "Nie znaleźliśmy przebytych tras w podanym terminie!"));
                return null;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Udało się!", "Oto trasy które przebyłeś."));
        return null;
    }
    
    public void przygotuj(){
        dataKoncowa = null;
    }
    public MapyBean() {
        model = new DefaultMapModel();
    }
    
}
