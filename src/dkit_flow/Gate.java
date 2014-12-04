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
    private final ParkingSystem parkingSystem;
    private String gateID;
    
    public Gate(ParkingSystem dkit){
        this.parkingSystem = dkit;
        
    }
    
    public void open(){
        if(parkingSystem.getFreeSpaces()>0)
        System.out.println("Gate " + gateID + " opened");
        else
            System.out.println("I'm sorry the parking is full at the moment try again later");
    }
    public String getGateID(){
        return gateID;
    }
    public void setGateID(String gateID){
        this.gateID = gateID;
    }
}
