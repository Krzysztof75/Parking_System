/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Object of class User holds the basic information of the parking lot users
 * @author Kris
 */

//this object lets us operate all the data about the Users of the parking system

public class User {
    private String carID;                 // car ID and balance are the only think we need from the user
    private double balance;
    private String timeIn;
    private String timeOut;
    private int hasPaid;
       DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
       Calendar cal = Calendar.getInstance();
       String currentDate = dateFormat.format(cal.getTime());

    /**
     * parameterless constructor
     */
    public User(){
        carID = null;
        balance = 0;
        hasPaid = 0;
    }
    /**
     * @param carID - String value representing car registration
     */
    public User(String carID){
         this();
         this.carID = carID;
         timeIn = currentDate;
    }
    /**
     * @param carID
     * @param timeIN 
     */
    public User(String carID, String timeIN){
         this(carID);
         this.carID = carID;
         this.timeIn = timeIN;
    }
    /**
     * @param carID
     * @param timeIN
     * @param TimeOut
     * @param balance
     * @param hasPaid 
     */
    public User(String carID,String timeIN, String TimeOut, double balance, int hasPaid){
        this(carID,timeIN);
        this.timeOut = TimeOut;
        this.balance = balance;
        this.hasPaid = hasPaid;
    }

    /**
     *
     * @return
     */
    public int getHasPaid(){
        return hasPaid;
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
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
    @Override
    // Users equal method compares only carID as it is unique for each user
    public boolean equals(Object o){
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        User other = (User) o;
        return (other.carID.equals(this.carID));
    }
    @Override
    public String toString(){
        // Return description of an object
        return "CarID: " + this.carID + " Time in " + this.timeIn + " Time out: " + this.timeOut + " balance: " + balance + " hasPaid: " + hasPaid;
    }

    /**
     * @return the timeIN
     */
    public String getTimeIN() {
        return timeIn;
    }

    /**
     * @param timeIN the timeIN to set
     */
    public void setTimeIn(String timeIN) {
        this.timeIn = timeIN;
    }

    /**
     * @return the timeOUT
     */
    public String getTimeOut() {
        return timeOut;
    }

    /**
     * @param timeOUT the timeOUT to set
     */
    public void setTimeOut(String timeOUT) {
        this.timeOut = timeOUT;
    }

    /**
     * @param hasPaid the hasPaid to set
     */
    public void setHasPaid(int hasPaid) {
        this.hasPaid = hasPaid;
    }

}
