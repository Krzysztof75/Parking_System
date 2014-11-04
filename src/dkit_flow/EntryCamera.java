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
public class EntryCamera {
    String carID;       
    ParkingSystem dkit;                  // pointer to the ParkingSystem Object
    
    public EntryCamera(ParkingSystem dkit){
        this.dkit = dkit;
    }
    
    public void read(String carID){           // read method taking String as an argument
        this.carID = carID;
        this.send(carID);
    }
    
    public void read(String [] reg){          // read method taking String array as an argument
     
         for(int i = 0; i <= reg.length; i++){
             carID = reg[i];
             this.send(carID);
        }  
    }
    public void send(String carID){
        dkit.setCarID(this);
        
    }
}
