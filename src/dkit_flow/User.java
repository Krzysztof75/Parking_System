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
    private String carID;                 // car ID and balance are the only think we need from the user
    private double balance;
    
    public User(){
        carID = null;
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
        return "CarID: " + carID;
    }

}
