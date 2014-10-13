/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import config.DBManager;
import entity.Car;
import entity.Obd2odczyt;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import others.Trasa;

/**
 *
 * @author Adaś
 */
public class PorownawczyBean extends Raport{

    /**
     * Creates a new instance of PorownawczyBean
     */
    public PorownawczyBean() {
    }
    private List<Trasa> trasy = new ArrayList<Trasa>();

    public List<Trasa> getTrasy() {
        return trasy;
    }

    public void setTrasy(List<Trasa> trasy) {
        this.trasy = trasy;
    }
    
    public String dalej(){
        Date j = new Date();
        Date tmp = new Date();
        Car tmpcar = new Car();
        tmp =  null;
        trasy = new ArrayList<Trasa>();
        try{
            EntityManager em = DBManager.getManager().createEntityManager();
            em.getTransaction().begin();
            List<Obd2odczyt> list = em.createQuery("SELECT o FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", dataPoczatkowa).setParameter("datak", dataKoncowa).getResultList();
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
            em.getTransaction().commit();
            em.close();
        }catch(NoResultException | ArrayIndexOutOfBoundsException | NullPointerException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Przykro nam!", "Nie znaleźliśmy przebytych tras w podanym terminie!"));
            return null;
        }
        return "porownawczy_1";
    }
    private PieChartModel pieModel;

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }
    private CartesianChartModel model = new CartesianChartModel();

    public CartesianChartModel getModel() {
        return model;
    }

    public void setModel(CartesianChartModel model) {
        this.model = model;
    }
    public String generuj(){
        int miasto = 0;
        int pozaMiastem = 0;
        pieModel =  new PieChartModel();
        ChartSeries miastoSeria = new ChartSeries("Miasto");
        ChartSeries pozaMiastemSeria = new ChartSeries("Poza miastem");
        EntityManager em = DBManager.getManager().createEntityManager();
        for (Trasa x: trasy){
            try{
                em.getTransaction().begin();
                List<Obd2odczyt> list = em.createQuery("SELECT o FROM Obd2odczyt o JOIN o.car car WHERE car.vin=:vin AND o.data>=:datap AND o.data<=:datak").setParameter("vin", x.getCar().getVin()).setParameter("datap", x.getDataPoczatkowa()).setParameter("datak", x.getDataKoncowa()).getResultList();
                for (Obd2odczyt y: list){
                 //   System.out.println(y.getPredkosc());
                    if (y.getPredkosc()<=50){
                        miasto++;}
                    else pozaMiastem++;
                }
                miastoSeria.set(x.getDataPoczatkowa(), miasto);
                pozaMiastemSeria.set(x.getDataPoczatkowa(), pozaMiastem);
            }catch(NoResultException e){
                return null;
            }
            miasto =0;
            pozaMiastem= 0;
        }
        em.close();
        model.addSeries(miastoSeria);
        model.addSeries(pozaMiastemSeria);
        return "porownawczy_2";
    }
    
}
