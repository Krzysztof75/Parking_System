/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkit_flow;

/**
 * @author Kris
 */
public interface iSensor {

    /**
     *
     * @param carID
     */
    public void read(String carID);

    /**
     *
     * @param carID
     */
    public void send(String carID);
}
