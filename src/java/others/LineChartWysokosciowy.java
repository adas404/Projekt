/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package others;

import java.util.ArrayList;
import java.util.List;
import org.primefaces.model.chart.CartesianChartModel;

/**
 *
 * @author Adam
 */
public class LineChartWysokosciowy {
    public LineChartWysokosciowy(){
        
    }
    private List<CartesianChartModel> listModel = new ArrayList<CartesianChartModel>();

    public List<CartesianChartModel> getListModel() {
        return listModel;
    }

    public void setListModel(List<CartesianChartModel> listModel) {
        this.listModel = listModel;
    }
    
}
