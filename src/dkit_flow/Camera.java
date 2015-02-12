/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkit_flow;

/**
 * abstract class implements iSensor
 * 
 */
public abstract class Camera implements iSensor{
    
    private String carID;                         // this variable will be used to pass the carID value inside Camera object 
                                                  //the Camera object will pass itself as argument to the ParkingSystem method setCarID
    ParkingSystem parkingSystem;                  // pointer to the ParkingSystem Object so that the object camera can register with it
    
    /**
     *
     * @param carID
     */
    @Override
    /**
     * reads String representing registration number and passes it to the ParkingSystem object
     * @param: String
     */
    public void read(String carID){           
        this.setCarID(carID);                 // assign carID value read by camera to the carID instatnce variable
        this.send(carID);                     // look for the implementation of send method in Entry and Exit cameras  
    }
    /**
     * reads array of Strings
     * @param reg 
     */
    public void read(String [] reg){           // read method taking String array as an argument it loops than through the array and invoke send()
        // method on each String value
        for (String reg1 : reg) {
            setCarID(reg1);
            System.out.println("invoking read method from abstract Camera class");
            this.send(getCarID());
        }  
    }
  
    /**
     *
     * @param carID
     */
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
