/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 * reads registration plates of vehicles leaving the parking lot
 */
public class ExitCamera extends Camera{    
    
 /* parkingSystem is a reference to Parking System object, it is inherited from camera class
 *  it is used to send carID to parkingSystem object   
 */ 
    
    /**
     * 
     * @param dkit - reference to the ParkingSystem object
     */
    public ExitCamera(ParkingSystem dkit){
        this.parkingSystem = dkit;                 
    }
    
    /**
     * passes carID to the ParkingSystem object
     * @param carID - string representing registration plate
     */
    @Override
    public void send(String carID){  
        System.out.println("Send carID from exit camera " + carID);
        parkingSystem.setCarID(this);
        
        
    }
}
