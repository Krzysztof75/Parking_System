/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkit_flow;

import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Kris
 */
public class parkingDB{
    
    ArrayList<User>traffic = new ArrayList();
    ArrayList<Subscriber>subscribers = new ArrayList();
    
    
     // these variables are neccessary to connect to the database
     private String url = "jdbc:mysql://localhost:3306/";
     private String dbName = "parkingsystem";
     private String driver = "com.mysql.jdbc.Driver";
     private String userName = "root"; 
     private String password = "";
     private Connection conn = null;
      
     
   
    public parkingDB(){    
    }
   // Connecting to the database through the driver
   // there is a try - catch block to catch number of Exceptions possible during connection and disconnection
   // Exceptions - objects created when problems, which JRE can't resolve happen while running programs 
   // Exception handling lets developers handle such a problems in gracefull manner    
    public void connect(){
        try{
        Class.forName(getDriver()).newInstance();
            setConn(DriverManager.getConnection(getUrl() + getDbName(), getUserName(), getPassword()));
           
            System.out.println("Connected to the database");              
    }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e){
           System.out.println("There is a problem connecting to the database");
        }
    }
    public void disconnect(){
        try{
            getConn().close();
        System.out.println("Disconnected from database");
    }
    catch(SQLException e){
        System.out.println("There is a problem disconnecting from the database");
    }
    }
    
    public void registerSubscriber(){
        
        this.connect();
        // method body insert record to Subscriber table
        this.disconnect();
           
    }
    
    public boolean isSubscriber(){
        boolean isSubscriber = false;
       
        // run DB querry to check if the user is registered if true set isSubscriber = true
        
        return isSubscriber;
    }
    
    public void removeSubscriber(){
        this.connect();
        // selete the subscriber from the subscriber table
        this.disconnect();
    }
    public void updateBalance(){
        this.connect();
        // update balance in the traffic table or if Subscriber update balance in the subscriber table
        this.disconnect();
    }
    public void enterVehicle(){
        this.connect();
        // insert record into the traffic table 
        this.disconnect();
    }
    public void exitVehicle(){
        this.connect();
        // update record in the traffic table where carID == " "
        this.disconnect();
    }
    public ArrayList<User>getTraffic(){
       
        this.connect();
        // get from DB all cars that are on the parking lot
        this.disconnect();
        
    return traffic;   
    }
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @param dbName the dbName to set
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the conn
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }
}

    

