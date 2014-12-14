/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkit_flow;

/**
 *
 * @author Kris
 */
public class Gate {
    private final ParkingSystem parkingSystem;       // we use this to link register gate with ParkingSystem object 
    private String gateID;
    
    public Gate(ParkingSystem parkingSystem){
        this.parkingSystem = parkingSystem;
        
    }
    
    public void open(){
        // check if there is a free space
        if(parkingSystem.getFreeSpaces()>0)
        System.out.println("Gate " + gateID + " opened");
        else
            System.out.println("I'm sorry the parking is full at the moment try again later");
    }
    // getter for getGateID
    public String getGateID(){
        return gateID;
    }
    //setter for GateID
    public void setGateID(String gateID){
        this.gateID = gateID;
    }
}
