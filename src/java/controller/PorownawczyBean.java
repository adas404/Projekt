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
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import others.PieChart;
import others.Trasa;

/**
 *
 * @author Adaś
 */
public class PorownawczyBean extends Raport{

    /**
     * Creates a new instance of PorownawczyBean
     */

    private PieChart pieChart = new PieChart();

    public PieChart getPieChart() {
        return pieChart;
    }

    public void setPieChart(PieChart pieChart) {
        this.pieChart = pieChart;
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
        pieChart = new PieChart();
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
        double tmpSredniaPredkosc = 0.0;
        double tmpDlugoscTrasy = 0.0;
        double sredniaPredkosc = 0.0;
        EntityManager em = DBManager.getManager().createEntityManager();
        for (Trasa x: trasy){
            try{
                PieChartModel tmpPie =  new PieChartModel();
                List<Obd2odczyt> list = em.createQuery("SELECT o FROM Obd2odczyt o JOIN o.car car WHERE car.vin=:vin AND o.data>=:datap AND o.data<=:datak").setParameter("vin", x.getCar().getVin()).setParameter("datap", x.getDataPoczatkowa()).setParameter("datak", x.getDataKoncowa()).getResultList();
                tmpSredniaPredkosc += (Double)em.createQuery("SELECT AVG(o.predkosc) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=:vin AND o.data>=:datap AND o.data<=:datak").setParameter("vin", x.getCar().getVin()).setParameter("datap", x.getDataPoczatkowa()).setParameter("datak", x.getDataKoncowa()).getSingleResult();
                sredniaPredkosc += tmpSredniaPredkosc;
                for (Obd2odczyt y: list){
                 //   System.out.println(y.getPredkosc());
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
                pieChart.getListaPie().add(tmpPie);
                tmpTime += x.getDataKoncowa().getTime() - x.getDataPoczatkowa().getTime();
                time += tmpTime;
                tmpDlugoscTrasy += (tmpSredniaPredkosc) * ((double)(new Date(tmpTime).getHours()-1) + (((double)(new Date(tmpTime).getMinutes()))/60) + (((double)(new Date(tmpTime).getSeconds()))/3600));
                tmpTime =0;
                tmpSredniaPredkosc = 0;
            }catch(NoResultException e){
                return null;
            }
            miasto =0;
            pozaMiastem= 0;
        }
        sredniaPredkosc /= trasy.size();
        pieChart.setSredniaPredkosc(sredniaPredkosc);
        pieChart.setGodziny(new Date(time).getHours()-1);
        pieChart.setMinuty(new Date(time).getMinutes());
        pieChart.setSekundy(new Date(time).getSeconds());
        pieChart.setDlugoscTrasy(tmpDlugoscTrasy);
  //      dlugoscTrasy = sredniaPredkosc * (double)((double)godziny + (double)((double)minuty/60) + (double)((double)sekundy/3600));
        pieChart.getCaloscPie().set("Miasto", miastoCalosc);
        pieChart.getCaloscPie().set("Poza miastem", pozaMiastemCalosc);
        pieChart.getCaloscPie().setTitle("Łączny czas ze wszystkich tras");
        em.close();
        return "porownawczy_2";
    }
    
}
