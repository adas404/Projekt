/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import config.DBManager;
import entity.Car;
import entity.Obd2odczyt;
import entity.Pozycja;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.xa.XAResource;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import others.LineChartWysokosciowy;
import others.Trasa;

/**
 *
 * @author Adam
 */
public class WysokosciowyBean extends Raport{

    /**
     * Creates a new instance of WysokosciowyBean
     */
    public WysokosciowyBean() {
    }

    public LineChartWysokosciowy getLineChart() {
        return lineChart;
    }

    public void setLineChart(LineChartWysokosciowy lineChart) {
        this.lineChart = lineChart;
    }
    private LineChartWysokosciowy lineChart = new LineChartWysokosciowy();
    
    public String generuj(){
        Date j = new Date();
        Date tmp = new Date();
        Car tmpcar = new Car();
        tmp =  null;
        List<Trasa> trasy = new ArrayList<Trasa>();
        try{
            EntityManager em = DBManager.getManager().createEntityManager();
                List<Obd2odczyt> list = em.createQuery("SELECT o FROM Obd2odczyt o JOIN o.car car WHERE o.data>=:datap AND o.data<=:datak AND car.vin=:vin").setParameter("datap", dataPoczatkowa).setParameter("datak", dataKoncowa).setParameter("vin", this.getCar().getVin()).getResultList();
                if (list.isEmpty()){
                    em.close();
                    throw new NoResultException("Brak wynikow");
                }
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
            trasy.add(new Trasa(tmpcar, tmp, j));
            for (Trasa y: trasy){
                CartesianChartModel tmpModel = new CartesianChartModel();
                ChartSeries pred = new ChartSeries("Prędkość (km/h)");
                ChartSeries obciazenieSilnika = new ChartSeries("Obciążenie silnika (%)");
                ChartSeries wysokosc = new ChartSeries("Wysokość (m n.p.m)");
                ChartSeries obroty = new ChartSeries("Obroty");
                list = new ArrayList<Obd2odczyt>();
                List<Pozycja> pozycja = em.createQuery("SELECT p FROM Pozycja p JOIN p.car car WHERE car.vin=:vin AND p.data>=:datap AND p.data<=:datak").setParameter("vin", y.getCar().getVin()).setParameter("datap", y.getDataPoczatkowa()).setParameter("datak", y.getDataKoncowa()).getResultList();
                list = em.createQuery("SELECT o FROM Obd2odczyt o JOIN o.car car WHERE car.vin=:vin AND o.data>=:datap AND o.data<=:datak").setParameter("vin", y.getCar().getVin()).setParameter("datap", y.getDataPoczatkowa()).setParameter("datak", y.getDataKoncowa()).getResultList();
                for (Obd2odczyt x:list){
                    pred.set(x.getData().getTime(), x.getPredkosc());
                    obciazenieSilnika.set(x.getData().getTime(), x.getObciazenieSilnika());
                    obroty.set(x.getData().getTime(), x.getObroty());
                }
                for (Pozycja z: pozycja){
                    wysokosc.set(z.getData().getTime(), z.getWysokosc());
                }
                tmpModel.setTitle(list.get(0).getData().toString()+" - "+list.get(list.size()-1).getData().toString());
                tmpModel.addSeries(pred);
                tmpModel.addSeries(obciazenieSilnika);
                tmpModel.addSeries(obroty);
                tmpModel.addSeries(wysokosc);
                lineChart.getListModel().add(tmpModel);
            }
        }catch(NoResultException | ArrayIndexOutOfBoundsException | NullPointerException | FacesException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Przykro nam!", "Nie znaleźliśmy przebytych tras w podanym terminie!"));
            return null;
        }
    return "wysokosciowy";
    }
}
