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
// Take note that Subscriber inherits String carID, double balance and boolean isSubscriber from User
public class Subscriber extends User {
    private String FirstName;
    private String LastName;
    private String adress;
    private String accountNumber;
    private String phone;
    
    public Subscriber(){
        super();
    }
    // 
    public Subscriber(String CarID, String FirstName, String LastName, String adress, String phone, String account){
        super(CarID);
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.adress = adress;
        this.phone = phone;
        this.accountNumber = account;
        this.setIsSubscriber(true);
          
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
