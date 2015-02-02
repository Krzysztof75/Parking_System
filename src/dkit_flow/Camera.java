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
    
    private String carID;                         // this variable will be used to pass the carID value inside Camera object 
                                                  //the Camera object will pass itself as argument to the ParkingSystem method setCarID
    ParkingSystem parkingSystem;                  // pointer to the ParkingSystem Object so that the object camera can register with it
    
    @Override
    public void read(String carID){           // read method taking String as an argument
        this.setCarID(carID);                 // assign carID value read by camera to the carID instatnce variable
        this.send(carID);                     // look for the implementation of send method in Entry and Exit cameras  
    }
    
    public void read(String [] reg){           // read method taking String array as an argument it loops than through the array and invoke send() 
                                               // method on each String value
         for(int i = 0; i < reg.length; i++){
             setCarID(reg[i]);
             System.out.println("invoking read method from abstract Camera class");
             this.send(getCarID());
        }  
    }
  
    @Override
    public abstract void send(String carID);                      

    /**
     * @return the carID
     */
    public String getCarID() {
        return carID;
    }

    /**
     * @param carID the carID to set
     */
    public void setCarID(String carID) {
        this.carID = carID;
    }
        
}
