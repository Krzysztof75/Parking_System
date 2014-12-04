/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkit_flow;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Kris
 */
public class parkingDB{
    // this array will hold The most recent information from traffic table
    ArrayList<User>traffic = new ArrayList();
    // this array will hold the most recent information from subscriber table
    ArrayList<Subscriber>subscribers = new ArrayList();
    static Statement statement = null;
    static PreparedStatement pstmt = null;
    static PreparedStatement pstmt2 = null;
    ResultSet rs = null;
    
     // these variables are neccessary to connect to the database
     private static String url;
     private static String dbName;
     private static String driver;
     private static String userName; 
     private static String password;
     private static Connection conn;
     
   
    public parkingDB(){ 
        
     // these variables are neccessary to connect to the database
     url = "jdbc:mysql://localhost:3306/";
     dbName = "parkingsystem";
     driver = "com.mysql.jdbc.Driver";
     userName = "root"; 
     password = "";
     conn = null;
     connect();      // invoking connect() method of ParkingDB object in the constructor of ParkingDB
        
    }
   // Connecting to the database through the driver
   // there is a try - catch block to catch number of Exceptions possible during connection and disconnection
   // Exceptions - objects created when problems, which JRE can't resolve happen while running programs 
   // Exception handling lets developers handle such a problems in gracefull manner   
    /*
    this method establishes connection with the database
    */
    public final void connect(){
        try{
        Class.forName(getDriver()).newInstance();
        //passing the privare variables declared above to connect to the database
            setConn(DriverManager.getConnection(getUrl() + getDbName(), getUserName(), getPassword()));
           //if successful this message will display
            System.out.println("Connected to the database");              
    }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e){
            //if connection unseccessful catch exception and display this message
           System.out.println("There is a problem connecting to the database");
        }
    }
    /*
    method disconnecting from the database 
    */
    public static void disconnect(){
        try{
            getConn().close();
            //if disconnection successful display
        System.out.println("Disconnected from database");
    }
    catch(SQLException e){
        //if not catch exception and display this message
        System.out.println("There is a problem disconnecting from the database");
    }
    }
    
    public void updateFreeSpace(int freeSpace){
     
    System.out.println("saving current count of free spaces");
    
//    Object[] inputValues = new Object[columnNames.length];
//    inputValues[0] = freeSpace;
//    try{
//    Statement stmt = conn.createStatement();
//    String sql = "update counter set count =" + freeSpace + " where count=" + (freeSpace -1);
//    stmt.executeUpdate(sql);
//        
//    }catch(SQLException e){
//        System.out.println("Problems updating counter in DB");
//    }
    }
    public static void registerSubscriber(Subscriber s){
     
    System.out.println("Subscribing " + s.getFirstName());  
    
    String[] columnNames = { "FirstName", "LastName", "carID", "accountNumber", "balance" };
    Object[] inputValues = new Object[columnNames.length];
    inputValues[0] = s.getFirstName();
    inputValues[1] = s.getLastName();
    inputValues[2] = s.getCarID();
    inputValues[3] = s.getAccount();
    inputValues[4] = s.getBalance();

    // prepare blob object from an existing binary column
    String insert = "insert into subscribers (FirstName, LastName, carID, accountNumber ,balance ) values(?, ?, ?, ?, ?)";
    try{
    pstmt = conn.prepareStatement(insert);

    pstmt.setObject(1, inputValues[0]);
    pstmt.setObject(2, inputValues[1]);
    pstmt.setObject(3, inputValues[2]);
    pstmt.setObject(4, inputValues[3]);
    pstmt.setObject(5, inputValues[4]);

    pstmt.executeUpdate();
    
    pstmt.close();
    
    }catch(SQLException e){
        System.out.println("There is a problem with querry insert Subsriber");
    }
           
    }
    
    public boolean isSubscriber(User u){
        boolean isSubscriber = false;
        
        String query = "select FirstName, LastName, carID, accountNumber, balance from resume where id=?";

//    pstmt2 = conn.prepareStatement(query);
//    pstmt2.setObject(1, inputValues[0]);
//    rs = pstmt2.executeQuery();
//    Object[] outputValues = new Object[columnNames.length];
//    if (rs.next()) {
//      for (int i = 0; i < columnNames.length; i++) {
//        outputValues[i] = rs.getObject(i + 1);
//      }
//    }
//    System.out.println("FistName " + ((String) outputValues[0]));
//    System.out.println("LastName " + ((String) outputValues[1]));
//    System.out.println("carID " + ((String) outputValues[2]));
//    System.out.println("accountNumber " + ((String) outputValues[3]));
//    System.out.println("balance ") + ((String) outputValues[4]);

       
        // run DB querry to check if the user is registered if true set isSubscriber = true
        
        return isSubscriber;
    }
    
    public void removeSubscriber(){
       
        // selete the subscriber from the subscriber table
           }
    public void updateBalance(){
      
        // update balance in the traffic table or if Subscriber update balance in the subscriber table
       
    }
    public void enterVehicle(Camera c){
        
        System.out.println("enterVehicle method in ParkingDB: " + c.getCarID());
        
    String[] columnNames = { "carID" };
    Object[] inputValues = new Object[columnNames.length];
    inputValues[0] = c.getCarID();
    
    // prepare blob object from an existing binary column
    String insert = "insert into traffic (carID) values(?)";
    
    try{
    
    pstmt = conn.prepareStatement(insert);
    pstmt.setObject(1, inputValues[0]);
    pstmt.executeUpdate();
    pstmt.close();
    
    }catch(SQLException e){
        System.out.println("There is a problem with querry insert into traffic");
        e.printStackTrace();
    }
        
        // insert record into the traffic table 
        
    }
    public void exitVehicle(User u){
       System.out.println("exitVehicle method in parkingDB: " + u.getCarID());
        // update record in the traffic table where carID == " "
       
    }
    public ArrayList<User>getTraffic(){ 
       
       
        // get from DB all cars that are on the parking lot
       
        
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
        parkingDB.url = url;
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
        parkingDB.dbName = dbName;
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
        parkingDB.driver = driver;
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
        parkingDB.userName = userName;
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
        parkingDB.password = password;
    }

    /**
     * @return the conn
     */
    public static Connection getConn() {
        return conn;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        parkingDB.conn = conn;
    }
}

    

