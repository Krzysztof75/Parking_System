/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 * reads registration plates of vehicles entering the parking lot
 *
 * @author Kris
 */
public class EntryCamera extends Camera {

    /**
     *
     * @param ps - ParkingSystem Object
     */
    public EntryCamera(ParkingSystem ps) {
        this.parkingSystem = ps;
    }

    /**
     * passes carID to the ParkingSystem object
     *
     * @param carID - string representing registration plate
     */
    @Override
    public void send(String carID) {
        ParkingSystem.log.info("Send carID from entry camera: " + carID);
        parkingSystem.setCarID(this);
    }
}
