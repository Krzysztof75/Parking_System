/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.Objects;

/**
 * instance variables of this class store information about subscribed users
 * @author Kris
 */
// Take note that Subscriber inherits String carID and double balance from User
public final class Subscriber extends User {
    private String FirstName;
    private String LastName;
    private String adress;
    private String accountNumber;
    private String phone;
    
    //default user spec constructor

    /**
     *
     */
        public Subscriber(){
        super();
    }
    /**
     * 
     * @param u 
     */
    public Subscriber(User u){
        super(u.getCarID());
    }
    
   
    /**
     * 
     * @param FirstName
     * @param LastName
     * @param carID
     * @param account
     * @param balance 
     */
    public Subscriber(String FirstName, String LastName, String carID, String account, double balance){
        // invoking super class User constructor to initiate various instance variables
        super(carID);
        this.setFirstName(FirstName);
        this.setLastName(LastName);
        this.setCarID(carID);
        this.setAccountNumber(account);
        this.setHasPaid(1);
     
          
    }

     @Override
    // we specified this equal method in case we need to compare Subscribers
    public boolean equals(Object o){
   
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Subscriber other = (Subscriber) o;
        return (other.getCarID().equals(this.getCarID()) && other.getFirstName().equals(this.getFirstName())
                && other.getLastName().equals(this.getLastName()) && other.getAccountNumber().equals(other.getAccountNumber()));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.FirstName);
        hash = 37 * hash + Objects.hashCode(this.LastName);
        hash = 37 * hash + Objects.hashCode(this.adress);
        hash = 37 * hash + Objects.hashCode(this.accountNumber);
        hash = 37 * hash + Objects.hashCode(this.phone);
        return hash;
    }
    
      @Override
    public String toString(){
        
        return super.toString() + " First Name: " + this.FirstName + " Last Name: " + this.LastName;
    }
    
    /**
     * @return 
     */
    public String getFirstName() {
        return FirstName;
    }

    /**
     * @param FirstName
     */
    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    /**
     * @return
     */
    public String getLastName() {
        return LastName;
    }

    /**
     * @param LastName
     */
    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    /**
     * @return
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * @param AccountNumber
     */
    public void setAccountNumber(String AccountNumber) {
        this.accountNumber = AccountNumber;
    }

    /**
     * @return
     */
    public String getAdress() {
        return adress;
    }

    /**
     * @return
     */
    public String getAccount() {
        return accountNumber;
    }

    /**
     * @param account
     */
    public void setAccount(String account) {
        this.accountNumber = account;
    }

    /**
     * @return
     */
    public String getPhone() {
        return phone;
    }
   
}
