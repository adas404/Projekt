/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import config.DBManager;
import entity.Car;
import entity.Obd2odczyt;
import entity.Uzytkownik;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import org.primefaces.model.chart.PieChartModel;
import others.PieChartCar;
import others.PieChartMiasto;
import others.Trasa;

/**
 *
 * @author Adaś
 */
public class PorownawczyBean extends Raport{

    /**
     * Creates a new instance of PorownawczyBean
     */
    private PieChartCar pieChartCar = new PieChartCar();
    private PieChartMiasto pieChartMiasto = new PieChartMiasto();

    public PieChartMiasto getPieChartMiasto() {
        return pieChartMiasto;
    }

    public void setPieChartMiasto(PieChartMiasto pieChartMiasto) {
        this.pieChartMiasto = pieChartMiasto;
    }
    public PorownawczyBean() {
    }
    private List<Trasa> trasy = new ArrayList<Trasa>();

    public List<Trasa> getTrasy() {
        return trasy;
    }

    public void setTrasy(List<Trasa> trasy) {
        this.trasy = trasy;
    }
    
    public void przygotuj(){
        pieChartMiasto = new PieChartMiasto();
        pieChartCar = new PieChartCar();
        trasy = new ArrayList<Trasa>();
        dataKoncowa = null;
        dataPoczatkowa =  null;
    }
    public String dalej(){
        Date j = new Date();
        Date tmp = new Date();
        Car tmpcar = new Car();
        tmp =  null;
        trasy = new ArrayList<Trasa>();
        try{
            EntityManager em = DBManager.getManager().createEntityManager();
            List<Car> cars = this.getListaCar();
            for (Car z: cars){
                List<Obd2odczyt> list = em.createQuery("SELECT o FROM Obd2odczyt o JOIN o.car car WHERE o.data>=:datap AND o.data<=:datak AND car.vin=:vin").setParameter("datap", dataPoczatkowa).setParameter("datak", dataKoncowa).setParameter("vin", z.getVin()).getResultList();
                for (Obd2odczyt x: list){
                    if (j != null){
                       long i = abs((j.getTime() - x.getData().getTime())/1000);
                       if (i>12 && tmp != null){
                           trasy.add(new Trasa(tmpcar,tmp,j));
                            tmp = null;
                        } 
                    }
                    if (tmp == null)
                       tmp = x.getData();
                    j = x.getData();
                    tmpcar = x.getCar();            
                }
            }
            trasy.add(new Trasa(tmpcar, tmp, j));
            em.close();
        }catch(NoResultException | ArrayIndexOutOfBoundsException | NullPointerException | FacesException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Przykro nam!", "Nie znaleźliśmy przebytych tras w podanym terminie!"));
            return null;
        }
        return "porownawczy_1";
    }
    public String generuj(){
        int miasto = 0;
        int pozaMiastem = 0;
        int miastoCalosc = 0;
        int pozaMiastemCalosc = 0;
        long tmpTime = 0 ;
        long time = 0;
        double tmpDlugoscOdcinka = 0.0;
        double tmpSredniaPredkosc = 0.0;
        double tmpDlugoscTrasy = 0.0;
        double sredniaPredkosc = 0.0;
        EntityManager em = DBManager.getManager().createEntityManager();
        for (Trasa x: trasy){
            try{
                if (trasy.isEmpty())
                    throw new NoResultException();
                PieChartModel tmpPie =  new PieChartModel();
                List<Obd2odczyt> list = em.createQuery("SELECT o FROM Obd2odczyt o JOIN o.car car WHERE car.vin=:vin AND o.data>=:datap AND o.data<=:datak").setParameter("vin", x.getCar().getVin()).setParameter("datap", x.getDataPoczatkowa()).setParameter("datak", x.getDataKoncowa()).getResultList();
                tmpSredniaPredkosc += (Double)em.createQuery("SELECT AVG(o.predkosc) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=:vin AND o.data>=:datap AND o.data<=:datak").setParameter("vin", x.getCar().getVin()).setParameter("datap", x.getDataPoczatkowa()).setParameter("datak", x.getDataKoncowa()).getSingleResult();
                sredniaPredkosc += tmpSredniaPredkosc;
                for (Obd2odczyt y: list){
                    if (y.getPredkosc()<=50){
                        miasto++;
                        miastoCalosc++;
                    }
                    else{ 
                        pozaMiastem++;
                        pozaMiastemCalosc++;  
                    };
                }
                tmpPie.set("Miasto", miasto);
                tmpPie.set("Poza miastem", pozaMiastem);
                tmpPie.setTitle(x.getDataPoczatkowa().toString());
                pieChartMiasto.getListaPie().add(tmpPie);
                tmpTime += x.getDataKoncowa().getTime() - x.getDataPoczatkowa().getTime();
                time += tmpTime;
                tmpDlugoscOdcinka = (tmpSredniaPredkosc) * ((double)(new Date(tmpTime).getHours()-1) + (((double)(new Date(tmpTime).getMinutes()))/60) + (((double)(new Date(tmpTime).getSeconds()))/3600));
                tmpDlugoscTrasy += tmpDlugoscOdcinka;
                if (pieChartCar.getPieCar().getData().containsKey(x.getCar().getModel())){
                    pieChartCar.getPieCar().getData().put(x.getCar().getModel(), pieChartCar.getPieCar().getData().get(x.getCar().getModel()).intValue()+tmpDlugoscOdcinka);
                } else{
                    pieChartCar.getPieCar().getData().put(x.getCar().getModel(), tmpDlugoscOdcinka);
                }
                tmpTime =0;
                tmpSredniaPredkosc = 0;
                tmpDlugoscOdcinka = 0.0;
            }catch(NoResultException e){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Przykro nam!", "Musisz zaznaczyć jakieś trasy!"));
                return "porownawczy_1";
            }
            miasto =0;
            pozaMiastem= 0;
        }
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        int typ = (Integer)session.getAttribute("typ");
        int id =  (Integer)session.getAttribute("id");
        if(typ==2)
            sredniaPredkosc = (Double) em.createQuery("SELECT AVG(o.predkosc) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", dataPoczatkowa).setParameter("datak", dataKoncowa).getSingleResult();
        else
            sredniaPredkosc = (Double) em.createQuery("SELECT AVG(o.predkosc) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", dataPoczatkowa).setParameter("datak", dataKoncowa).setParameter("id", id).getSingleResult();
        pieChartMiasto.setSredniaPredkosc(sredniaPredkosc);
        pieChartMiasto.setGodziny(new Date(time).getHours()-1);
        pieChartMiasto.setMinuty(new Date(time).getMinutes());
        pieChartMiasto.setSekundy(new Date(time).getSeconds());
        pieChartMiasto.setDlugoscTrasy(tmpDlugoscTrasy);
        pieChartMiasto.getCaloscPie().set("Miasto", miastoCalosc);
        pieChartMiasto.getCaloscPie().set("Poza miastem", pozaMiastemCalosc);
        pieChartMiasto.getCaloscPie().setTitle("Łączny czas ze wszystkich tras");
        String notatka = "Wygenerowano raport porównawczy";
        this.log(4, notatka , em.find(Uzytkownik.class, session.getAttribute("id")));
        em.close();
        return "porownawczy_2";
    }

    public PieChartCar getPieChartCar() {
        return pieChartCar;
    }

    public void setPieChartCar(PieChartCar pieChartCar) {
        this.pieChartCar = pieChartCar;
    }
    
}
