/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package others;

import entity.Car;
import java.util.List;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Adam
 */
public class PieChartCar {
    public PieChartCar(){
        
    }

    public PieChartModel getPieCar() {
        return pieCar;
    }

    public void setPieCar(PieChartModel pieCar) {
        this.pieCar = pieCar;
    }
    private PieChartModel pieCar =  new PieChartModel();
    
    
}
