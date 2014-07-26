/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managed;

import entity.Car;

/**
 *
 * @author Adam
 */
public class managed {

    /**
     * Creates a new instance of managed
     */
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    private Car car = new Car();
    public managed() {
    }
    
}
