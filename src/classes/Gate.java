/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import autParkSys.interfaces.Parkable;

/**
 * object of this class represent a simple gate mechanism which lifts up when
 * the signal is sent and than lowers itself down
 *
 * @author Kris
 */
public class Gate {

    private final ParkingSystem parkingSystem;       // we use this to link register gate with ParkingSystem object 
    private String gateID;

    /**
     *
     * @param pS - object of class implementing Parkable
     */
    public Gate(Parkable pS) {
        this.parkingSystem = (ParkingSystem) pS;
        ParkingSystem.log.info("Creating Gate object");
    }

    /**
     * lifts the gate if number of free spaces above 0
     */
    public void open() {
        // check if there is a free space
        if (parkingSystem.getFreeSpaces() > 0) {
            ParkingSystem.log.info("Gate " + gateID + " opened");
        } else {
            System.out.println("***I'm sorry the parking is full at the moment try again later***");
        }
    }

    /**
     *
     * @return gateID
     */
    public String getGateID() {
        return gateID;
    }

    /**
     *
     * @param gateID
     */
    public void setGateID(String gateID) {
        this.gateID = gateID;
        ParkingSystem.log.info("Setting gateID to " + gateID);
    }
}
