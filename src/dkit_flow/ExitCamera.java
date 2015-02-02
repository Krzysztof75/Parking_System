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
/* class representing camera type reader
   with two read methods:
- read(String); reads String as an input
-read(String Array) reads String elements in the array
*/
public class ExitCamera extends Camera{    
    
 /* parkingSystem is a reference to Parking System object, it is inherited from camera class
 *  it is used to send carID to parkingSystem object   
 */ 
    
    // constructor 
    public ExitCamera(ParkingSystem dkit){
        this.parkingSystem = dkit;                 
    }
    
    @Override
     // passing CarID to the ParkingSystem object
    public void send(String carID){  
        System.out.println("Send carID from exit camera " + carID);
        parkingSystem.setCarID(this);
        
        
    }
}
