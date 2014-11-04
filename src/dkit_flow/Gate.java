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
    private ParkingSystem dkit;
    private String gateName;
    
    public Gate(ParkingSystem dkit){
        this.dkit = dkit;
        
    }
    
    public void open(){
        if(dkit.getFreeSpaces()<400)
        System.out.println("Gate " + gateName + " opened");
        else
            System.out.println("I'm sorry the parking is full at the moment try again later");
    }
    public String getGateName(){
        return gateName;
    }
    public void setGateName(String gateName){
        this.gateName = gateName;
    }
}
