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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.MeterGaugeChartModel;


/**
 *
 * @author Adam
 */
public class DziennyBean extends Raport {

    /**
     * Creates a new instance of DziennyBean
     */
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    private Date data;
    
    private double sredniaPredkosc;

    public double getSredniaPredkosc() {
        return sredniaPredkosc;
    }
    private double srednieObroty;
    private double srednieObciazenie;

    

    public double getSrednieObroty() {
        return srednieObroty;
    }

    public double getSrednieObciazenie() {
        return srednieObciazenie;
    }

    public double getSredniaTempChlodzacego() {
        return sredniaTempChlodzacego;
    }

    public double getSrednieCisnienie() {
        return srednieCisnienie;
    }

    public double getSredniaTempDolotu() {
        return sredniaTempDolotu;
    }

    public double getSredniePolozeniePrzepustnicy() {
        return sredniePolozeniePrzepustnicy;
    }

    
    private double sredniaTempChlodzacego;
    private double srednieCisnienie;
    private double sredniaTempDolotu;
    private double sredniePolozeniePrzepustnicy;
    public CartesianChartModel getModel() {
        return model;
    }

    public void setModel(CartesianChartModel model) {
        this.model = model;
    }
    
    private CartesianChartModel model = new CartesianChartModel();
    
    private MeterGaugeChartModel predkosciomierz;
    private MeterGaugeChartModel obrotomierz;

    public MeterGaugeChartModel getPredkosciomierz() {
        return predkosciomierz;
    }

    public MeterGaugeChartModel getObrotomierz() {
        return obrotomierz;
    }
    List<Obd2odczyt> odczyt =  new ArrayList<Obd2odczyt>();

    public List<Obd2odczyt> getOdczyt() {
        return odczyt;
    }
    public String generuj(){
        model = new CartesianChartModel();
        Date j = new Date();
        ChartSeries pred = new ChartSeries();
        ChartSeries obroty = new ChartSeries();
        ChartSeries obciazenieSilnika = new ChartSeries();
        ChartSeries tempChlodzacego = new ChartSeries();
        ChartSeries cisnienieKolektora = new ChartSeries();
        ChartSeries tempDolotu = new ChartSeries();
        ChartSeries polozeniePrzepustnicy = new ChartSeries();
        try{
            EntityManager em = DBManager.getManager().createEntityManager();
            long tmpdate = this.data.getTime()+86400000;
            odczyt = em.createQuery("SELECT o FROM Obd2odczyt o JOIN o.car car WHERE car.vin=:vin AND o.data>=:data AND o.data<:dt").setParameter("vin", this.car.getVin()).setParameter("data", this.data).setParameter("dt", new Date(tmpdate)).getResultList();
            pred.setLabel("Prędkość");
            obciazenieSilnika.setLabel("Obciążenie silnika (%)");
            tempChlodzacego.setLabel("Temperatura płynu chłodzącego");
            cisnienieKolektora.setLabel("Ciśnienie kolektora (kPa)");
            tempDolotu.setLabel("Temperatura dolotu");
            polozeniePrzepustnicy.setLabel("Położenie przepustnicy (%)");
            obroty.setLabel("Obroty (100 x)");
            for(Obd2odczyt x: odczyt){
                pred.set(x.getData().getTime(), x.getPredkosc());
                obciazenieSilnika.set(x.getData().getTime(), x.getObciazenieSilnika());
                tempChlodzacego.set(x.getData().getTime(), x.getTempChlodzacego());
                cisnienieKolektora.set(x.getData().getTime(), x.getCisnienieKolektora());
                tempDolotu.set(x.getData().getTime(), x.getTempDolotu());
                polozeniePrzepustnicy.set(x.getData().getTime(), x.getPolozeniePrzepustnicy());
                obroty.set(x.getData().getTime(), x.getObroty()/100);
            }
            model.addSeries(pred);
            model.addSeries(obciazenieSilnika);
            model.addSeries(tempChlodzacego);
            model.addSeries(cisnienieKolektora);
            model.addSeries(tempDolotu);
            model.addSeries(polozeniePrzepustnicy);
            model.addSeries(obroty);
            sredniaPredkosc = (Double)em.createQuery("SELECT AVG(o.predkosc) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=:vin AND o.data>=:data AND o.data<:dt").setParameter("vin", this.car.getVin()).setParameter("data", this.data).setParameter("dt", new Date(tmpdate)).getSingleResult();
            sredniaTempChlodzacego = (Double)em.createQuery("SELECT AVG(o.tempChlodzacego) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=:vin AND o.data>=:data AND o.data<:dt").setParameter("vin", this.car.getVin()).setParameter("data", this.data).setParameter("dt", new Date(tmpdate)).getSingleResult();
            sredniaTempDolotu = (Double)em.createQuery("SELECT AVG(o.tempDolotu) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=:vin AND o.data>=:data AND o.data<:dt").setParameter("vin", this.car.getVin()).setParameter("data", this.data).setParameter("dt", new Date(tmpdate)).getSingleResult();
            srednieCisnienie = (Double)em.createQuery("SELECT AVG(o.cisnienieKolektora) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=:vin AND o.data>=:data AND o.data<:dt").setParameter("vin", this.car.getVin()).setParameter("data", this.data).setParameter("dt", new Date(tmpdate)).getSingleResult();
            srednieObciazenie = (Double)em.createQuery("SELECT AVG(o.obciazenieSilnika) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=:vin AND o.data>=:data AND o.data<:dt").setParameter("vin", this.car.getVin()).setParameter("data", this.data).setParameter("dt", new Date(tmpdate)).getSingleResult();
            srednieObroty = (Double)em.createQuery("SELECT AVG(o.obroty) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=:vin AND o.data>=:data AND o.data<:dt").setParameter("vin", this.car.getVin()).setParameter("data", this.data).setParameter("dt", new Date(tmpdate)).getSingleResult();
            sredniePolozeniePrzepustnicy = (Double)em.createQuery("SELECT AVG(o.polozeniePrzepustnicy) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=:vin AND o.data>=:data AND o.data<:dt").setParameter("vin", this.car.getVin()).setParameter("data", this.data).setParameter("dt", new Date(tmpdate)).getSingleResult();
            predkosciomierz = new MeterGaugeChartModel(sredniaPredkosc, new ArrayList<Number>(){{add(20);add(50);add(120);add(220);}});
            obrotomierz = new MeterGaugeChartModel(srednieObroty, new ArrayList<Number>(){{add(1000);add(2500);add(5000);add(7000);}});
            HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            String notatka = "Wygenerowano raport dzienny z dnia" + this.dataPoczatkowa;
            this.log(4, notatka, em.find(Uzytkownik.class, session.getAttribute("id")));            
            em.close();
        }catch(NoResultException | ArrayIndexOutOfBoundsException | NullPointerException e){
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Przykro nam!", "Nie znaleźliśmy przebytych tras w podanym terminie!"));
           return null;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Udało się!", "Oto dzienny raport wygenerowany specjalnie dla Ciebie"));
        return "dzienny";
    }
    public DziennyBean() {
    }
    
}
