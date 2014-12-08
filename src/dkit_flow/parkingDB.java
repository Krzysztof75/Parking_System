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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;
    
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
    preparedStatement = conn.prepareStatement(insert);

    preparedStatement.setObject(1, inputValues[0]);
    preparedStatement.setObject(2, inputValues[1]);
    preparedStatement.setObject(3, inputValues[2]);
    preparedStatement.setObject(4, inputValues[3]);
    preparedStatement.setObject(5, inputValues[4]);

    preparedStatement.executeUpdate();
    
    preparedStatement.close();
    
    }catch(SQLException e){
        System.out.println("There is a problem with querry insert Subsriber");
    }
           
    }
    
    public static boolean isSubscriber(User u){
        boolean isSubscriber = false;
        
        String sql = "SELECT firstName FROM subscribers where carID='" + u.getCarID() + "'";

        try{
    statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                     ResultSet.CONCUR_UPDATABLE);
    System.out.println("Executing select");
          resultSet = statement.executeQuery(sql);

          if(resultSet.first() == true){
              isSubscriber = true;
          }
          System.out.println(u.getCarID() + " is subscriber: " + isSubscriber);
          return isSubscriber;
  
    }catch(SQLException e){
    System.out.println("There is a problem with isSubscriber querry");
}
        return isSubscriber;
    }  
    public void removeSubscriber(){
       
        // selete the subscriber from the subscriber table
           }
    public double getBalance(User u){
        double balance = -1;
        try{
       statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                     ResultSet.CONCUR_UPDATABLE);
           
          String sql = "SELECT balance FROM traffic WHERE CarID='" + u.getCarID() + "'";
          System.out.println("Executing select");
          resultSet = statement.executeQuery(sql);

          if(resultSet.first() == false){
              System.out.println("There is no matching record");
          }
          
          while(resultSet.next()){
         //Retrieve by column name
         balance = resultSet.getDouble("balance");
         u.setBalance(balance);
          } 
        } catch(SQLException e){
         System.out.println("There is a problem with check balance querry");
    } return balance;
    }
   
    public static void updateBalance(User u, double amount){
        
      try{ 
          
          statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                     ResultSet.CONCUR_UPDATABLE);
         
          String sql = "UPDATE traffic SET balance ='" + amount + "' WHERE CarID ='" + u.getCarID() + "'";
          
          statement.executeUpdate(sql);
          System.out.println("Executing update in updateBalance user method ");
          
      u.setBalance(0.00);
        // update balance in the traffic table or if Subscriber update balance in the subscriber table
      System.out.println("Paid for " + u.getCarID());
      }catch(SQLException e){
          System.out.println("There is a problem with balance update querry");
          e.printStackTrace();
      }
    }
    
     public static void updateBalance(Subscriber s, double amount){
         double balance = s.getBalance();
         System.out.println("current balance: " + balance);
         balance+=amount;
         
      try{ 
          
          statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                     ResultSet.CONCUR_UPDATABLE);
         
          String sql = "UPDATE traffic SET balance ='"+ balance + "'" + " WHERE CarID ='" + s.getCarID() + "'";
          
          statement.executeUpdate(sql);
          System.out.println("Executing update in updateBalance subscriber method");
          
      s.setBalance(0.00);
        // update balance in the traffic table or if Subscriber update balance in the subscriber table
      System.out.println("Paid for " + s.getCarID());
      }catch(SQLException e){
          System.out.println("There is a problem with balance update querry");
          e.printStackTrace();
      }
    }
    
    
    
    public void insertTraffic(Camera c){
        
        System.out.println("enterVehicle method in ParkingDB: " + c.getCarID());
        
    String[] columnNames = { "carID" };
    Object[] inputValues = new Object[columnNames.length];
    inputValues[0] = c.getCarID();
    
    // prepare blob object from an existing binary column
    String insert = "insert into traffic (carID) values(?)";
    
    try{
    
    preparedStatement = conn.prepareStatement(insert);
    preparedStatement.setObject(1, inputValues[0]);
    preparedStatement.executeUpdate();
    preparedStatement.close();
    
    }catch(SQLException e){
        System.out.println("There is a problem with querry insert into traffic");
        e.printStackTrace();
    }
     traffic.add(new User(c.getCarID()));
        // insert record into the traffic table 
        
    }
    public void exitVehicle(User u){
       System.out.println("exitVehicle method in parkingDB: " + u.getCarID());
       
       DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
       Calendar cal = Calendar.getInstance();
       
       String currentDate = dateFormat.format(cal.getTime());
        System.out.println(currentDate); 
          
        try{ 
          
          statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                     ResultSet.CONCUR_UPDATABLE);
         
          String sql = "UPDATE traffic SET DateOut = '" + currentDate + "' WHERE CarID ='" + u.getCarID() + "'";
          statement.executeUpdate(sql);
          System.out.println("Executing update time in exitVehicle method");
          
      }catch(SQLException e){
          System.out.println("There is a problem with balance update querry");
          e.printStackTrace();
      }
        // update record in the traffic table where carID == " "
    }
    
    public ArrayList<User>getTraffic(){ 
       
       
        // get from DB all cars that are on the parking lot
       
        
    return traffic;   
    }
    
    public boolean verifyHasPaid(User u) {
        boolean hasPaid = false;
        
        try{
        String query = "SELECT balance FROM traffic where CarID = '"+ u.getCarID()+ "'";
         // create the java statement
        statement = conn.createStatement();
        resultSet = statement.executeQuery(query);
        while (resultSet.next())
      {
        double balance = resultSet.getDouble("balance");
        if(balance == 0.00){
           hasPaid = true;
           System.out.println(u.getCarID() + " hasPaid");
           break;
        }
      }
        }catch(SQLException e){
            System.out.println("There is a problem with check balance querry");
            e.printStackTrace();
        }
        
        // querry DB if hasPaid()
                return hasPaid;
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

    

