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
methods (inherited from Camera class):
-read(String s) accepts String 
-read(String[]s) accepts array of Strings
inside both of these methods the send(String carID) is invoked ensuring that the read String value is 

-send(String s) method acivating CarId through setCarID(Entry Camera) method in the ParkingSystem
parkingSystem is a reference to ParkingSystem object, it is inherited from Camera class
it will be used to invoke ParkingSystem method setCarID(EntryCamera)
*/
public class EntryCamera extends Camera{   
    
    // constructor
    public EntryCamera(ParkingSystem dkit){
        this.parkingSystem = dkit;                         
    }
    
    @Override
    // passing carID to the parkingSystem object 
    public void send(String carID){
        System.out.println("Send carID from entry camera: " + carID);
        parkingSystem.setCarID(this);
        
        
    }
}
