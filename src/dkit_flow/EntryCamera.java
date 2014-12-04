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
-read(String Array) reads all elements in the array
and a send() method acivating CarId in the ParkingSystem
parkingSystem is a reference to Parking System object, it is inherited from Camera class
 it will be used to in the constructor of user object in the controlling ParkingSystem class
*/
public class EntryCamera extends Camera{   
    
    public EntryCamera(ParkingSystem dkit){
        this.parkingSystem = dkit;                         
    }
    
//    public void read(String carID){
//        send(carID);
//    }
    
    @Override
    public void send(String carID){
        parkingSystem.setCarID(this);
        System.out.println("Send carID from entry camera: " + carID);
        
    }
}
