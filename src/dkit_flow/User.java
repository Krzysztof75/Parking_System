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
/* this object lets us operate all the data about the Users of the parking system


*/
public class User {
    private String carID;                 // car ID
    private boolean isSubscriber;         // true - sets to Subscriber
    private double balance;
    
    public User(){
        carID = null;
        isSubscriber = false;
        balance = 0;
    }
    
    public User(String carID){
         this();
         this.setCarID(carID);
    }
    
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

    /**
     * @return the isSubscriber
     */
    public boolean getIsSubscriber() {
        return isSubscriber;
    }

    /**
     * @param isSubscriber the isSubscriber to set
     */
    public void setIsSubscriber(boolean isSubscriber) {
        this.isSubscriber = isSubscriber;
    }
}
