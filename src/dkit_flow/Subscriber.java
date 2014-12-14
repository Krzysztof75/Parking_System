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
// Take note that Subscriber inherits String carID and double balance from User
public class Subscriber extends User {
    private String FirstName;
    private String LastName;
    private String adress;
    private String accountNumber;
    private String phone;
    
    //default user spec constructor
    public Subscriber(){
        super();
    }
    
    public Subscriber(User u){
        super(u.getCarID());
    }
    
    //user spec constructor taking several arguments 
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
    public String toString(){
        
        return super.toString() + " First Name: " + this.FirstName + " Last Name: " + this.LastName;
    }
    
    /**
     * @return the FirstName
     */
    public String getFirstName() {
        return FirstName;
    }

    /**
     * @param FirstName the FirstName to set
     */
    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    /**
     * @return the LastName
     */
    public String getLastName() {
        return LastName;
    }

    /**
     * @param LastName the LastName to set
     */
    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    /**
     * @return the AccountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * @param AccountNumber the AccountNumber to set
     */
    public void setAccountNumber(String AccountNumber) {
        this.accountNumber = AccountNumber;
    }

    /**
     * @return the adress
     */
    public String getAdress() {
        return adress;
    }

    /**
     * @return the account
     */
    public String getAccount() {
        return accountNumber;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(String account) {
        this.accountNumber = account;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }
   
}
