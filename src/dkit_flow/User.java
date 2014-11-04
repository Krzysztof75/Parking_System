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
public class User {
    private String carID;
    private boolean isSubscriber;

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
