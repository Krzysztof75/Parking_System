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
*/
public class EntryCamera extends Camera{
    String carID;       
    ParkingSystem dkit;                  // pointer to the ParkingSystem Object
    
    public EntryCamera(ParkingSystem dkit){
        this.dkit = dkit;
    }
    
    @Override
    public void send(String carID){
        dkit.setCarID(this);
        
    }
}
