/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package others;

import entity.Car;
import java.util.Date;

/**
 *
 * @author Adam
 */
public class Trasa {
    public Trasa(Car c, Date dP, Date dK){
        car = c;
        dataPoczatkowa = dP;
        dataKoncowa = dK;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Date getDataPoczatkowa() {
        return dataPoczatkowa;
    }

    public void setDataPoczatkowa(Date dataPoczatkowa) {
        this.dataPoczatkowa = dataPoczatkowa;
    }

    public Date getDataKoncowa() {
        return dataKoncowa;
    }

    public void setDataKoncowa(Date dataKoncowa) {
        this.dataKoncowa = dataKoncowa;
    }
    private Car car;
    private Date dataPoczatkowa;
    private Date dataKoncowa;
}
