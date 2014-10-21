/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package others;

import java.util.ArrayList;
import java.util.List;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Adam
 */
public class PieChart {
    public PieChart(){
        
    }
    private PieChartModel caloscPie = new PieChartModel();

    public PieChartModel getCaloscPie() {
        return caloscPie;
    }

    public void setCaloscPie(PieChartModel caloscPie) {
        this.caloscPie = caloscPie;
    }
        private List<PieChartModel> listaPie = new ArrayList<PieChartModel>();

    public List<PieChartModel> getListaPie() {
        return listaPie;
    }

    public void setGodziny(int godziny) {
        this.godziny = godziny;
    }

    public void setMinuty(int minuty) {
        this.minuty = minuty;
    }

    public void setSekundy(int sekundy) {
        this.sekundy = sekundy;
    }

    public void setListaPie(List<PieChartModel> lista) {
        this.listaPie = listaPie;
    }
    private int godziny;

    public int getGodziny() {
        return godziny;
    }

    public int getMinuty() {
        return minuty;
    }

    public int getSekundy() {
        return sekundy;
    }
    private int minuty;
    private int sekundy;
    public double getSredniaPredkosc() {
        return sredniaPredkosc;
    }

    public void setSredniaPredkosc(double sredniaPredkosc) {
        this.sredniaPredkosc = sredniaPredkosc;
    }

    public void setDlugoscTrasy(double dlugoscTrasy) {
        this.dlugoscTrasy = dlugoscTrasy;
    }
    private double sredniaPredkosc;
    private double dlugoscTrasy;

    public double getDlugoscTrasy() {
        return dlugoscTrasy;
    }
}
