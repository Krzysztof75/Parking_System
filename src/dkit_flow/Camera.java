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
    
    private String carID;       
    ParkingSystem parkingSystem;                  // pointer to the ParkingSystem Object so that the object camera can register with it
    
    @Override
    public void read(String carID){           // read method taking String as an argument
        this.setCarID(carID);
        this.send(carID);
    }
    
    public void read(String [] reg){          // read method taking String array as an argument
     
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
