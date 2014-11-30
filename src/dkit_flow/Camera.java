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
public abstract class Camera implements iSensor{
    
    String carID;       
    ParkingSystem parkingSystem;                  // pointer to the ParkingSystem Object
    
    @Override
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
  
    @Override
    public abstract void send(String carID);                // acivating the CarID in the ParkingSystem
        
}
