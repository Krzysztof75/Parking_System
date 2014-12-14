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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Kris
 */
public class parkingDB {

    // this array will hold The most recent information from traffic table
    // this array will hold the most recent information from subscriber table
    private static ArrayList<Subscriber> subscribers = new ArrayList<>();
    private static ArrayList<User> traffic = new ArrayList<>();                          // we can store here all info from the table traffic
    boolean startUp = false;
    
    
    // Statement and PreparedStatement will help us build and send queries to the data base
    //ResultSet will help us retrive information from dataBase table
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

    public parkingDB() {

        // these variables are neccessary to connect to the database
        url = "jdbc:mysql://localhost:3306/";
        dbName = "parkingsystem";
        driver = "com.mysql.jdbc.Driver";
        userName = "root";
        password = "";
        conn = null;
        connect();      // invoking connect() method of ParkingDB object in the constructor of ParkingDB
        initSubscribers();
        initTraffic();
    }
    // Connecting to the database through the driver
    // there is a try - catch block to catch number of Exceptions possible during connection and disconnection
    // Exceptions - objects created when problems, which JRE can't resolve happen while running programs 
    // Exception handling lets developers handle such a problems in gracefull manner   
    /*
     this method establishes connection with the database
     */

    public final void connect() {
        try {
            Class.forName(getDriver()).newInstance();
            //passing the privare variables declared above to connect to the database
            setConn(DriverManager.getConnection(getUrl() + getDbName(), getUserName(), getPassword()));
            //if successful this message will display
            System.out.println("Connected to the database");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            //if connection unseccessful catch exception and display this message
            System.out.println("There is a problem connecting to the database");
        }
    }
    /*
     method disconnecting from the database 
     */

    public static void disconnect() {
        try {
            getConn().close();
            //if disconnection successful display
            System.out.println("Disconnected from database");
        } catch (SQLException e) {
            //if not catch exception and display this message
            System.out.println("There is a problem disconnecting from the database");
        }
    }

    public static void registerSubscriber(Subscriber s) {

        System.out.println("Subscribing " + s.getFirstName());

        //identifying the columns in the table
        String[] columnNames = {"FirstName", "LastName", "carID", "accountNumber", "balance"};
        // Prepare the object holding the values we want to pass
        Object[] inputValues = new Object[columnNames.length];
        inputValues[0] = s.getFirstName();
        inputValues[1] = s.getLastName();
        inputValues[2] = s.getCarID();
        inputValues[3] = s.getAccount();
        inputValues[4] = s.getBalance();

        // Prepare sql query pass the values of inputValues object into the querry
        String insert = "insert into subscribers (FirstName, LastName, carID, accountNumber ,balance ) values(?, ?, ?, ?, ?)";
        try {
            preparedStatement = conn.prepareStatement(insert);

            preparedStatement.setObject(1, inputValues[0]);
            preparedStatement.setObject(2, inputValues[1]);
            preparedStatement.setObject(3, inputValues[2]);
            preparedStatement.setObject(4, inputValues[3]);
            preparedStatement.setObject(5, inputValues[4]);

            //Execute the query
            preparedStatement.executeUpdate();

            preparedStatement.close();
            
            // add new Subscriber to the Subscribers List
            getSubscribers().add(s);

        } catch (SQLException e) {
            System.out.println("There is a problem with querry insert Subsriber");
        }
         
    }
    // this method queries the data base if there is such a car registration number in the subscriber table
    // if there is return true
    public static boolean isSubscriber(User u) {
        boolean isSubscriber = false;

        String sql = "SELECT firstName FROM subscribers where carID='" + u.getCarID() + "'";

        try {
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            System.out.println("Executing isSubscriber method");
            resultSet = statement.executeQuery(sql);

            if (resultSet.first() == true) {
                isSubscriber = true;
            }
            System.out.println(u.getCarID() + " is subscriber: " + isSubscriber);
            return isSubscriber;

        } catch (SQLException e) {
            System.out.println("There is a problem with isSubscriber querry");
        }
        return isSubscriber;
    }

    public void removeSubscriber() {

        // selete the subscriber from the subscriber table
    }

    public static double getBalance(User u) {
        double balance = 0;
        try {
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            String sql = "SELECT balance FROM traffic WHERE CarID='" + u.getCarID() + "'";
            resultSet = statement.executeQuery(sql);

            if (resultSet.first() == false) {
                System.out.println("There is no matching record");
            }

            while (resultSet.next()) {
                //Retrieve by column name
                balance = resultSet.getDouble("balance");
                u.setBalance(balance);
            }
        } catch (SQLException e) {
            System.out.println("There is a problem with check balance querry");
        }
        return balance;
    }

    public static void updateBalance(User u) {
        // get user balance
        // search for that user in the traffic list
//        for (int i = 0; i < traffic.size(); i++) {
//            if (traffic.get(i).getCarID().equals(u.getCarID())) {
//                u = traffic.get(i);
//                u.setHasPaid(1);
//                System.out.println("Seting hasPaid to 1 in updateBalance (user)");
//                break;
//            }  
//        }
        try {

            statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            System.out.println("Updating traffic " + u.getCarID() + " time in " + u.getTimeIN() + " time out " + u.getTimeOut() + " balance " + u.getBalance() + " hasPaid " + u.getHasPaid());
            String sql = "UPDATE traffic SET DateOut = '" + u.getTimeOut() + "', balance = '" + u.getBalance() + "', hasPaid = '" + u.getHasPaid() + "' WHERE CarID ='" + u.getCarID() + "'";

            statement.executeUpdate(sql);
            System.out.println("updating traffic " + u.getCarID() + " " + u.getTimeOut() + " " + u.getBalance() + " " + u.getHasPaid());
            // update balance in the traffic table or if Subscriber update balance in the subscriber table
            u.setHasPaid(1);
            System.out.println("Paid for " + u.getCarID());
        } catch (SQLException e) {
            System.out.println("There is a problem with balance update querry");
            e.printStackTrace();
        }
    }

    public static double updateBalance(Subscriber s, double charge) {

        double balance = getBalance(s) + charge;
        System.out.println("Present balance: " + getBalance(s) + " charge: " + charge);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String currentDate = dateFormat.format(cal.getTime());
        
        s.setTimeOut(currentDate);
        s.setBalance(balance);
        System.out.println("before hasPaid in updateBalance " + s.getHasPaid());
        
        try {

            statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            System.out.println("Updating traffic dateout " + s.getTimeOut() + " balance " + charge + " hasPaid " + s.getHasPaid());
            String sql = "UPDATE traffic SET DateOUT = '" + s.getTimeOut() + "', balance = '" + charge + "', hasPaid = '" + s.getHasPaid() + "' WHERE CarID = '" + s.getCarID() + "'";

            statement.executeUpdate(sql);
            System.out.println("Executing update traffic method");

            // update balance in the traffic table or if Subscriber update balance in the subscriber table
            System.out.println("Paid for " + s.getCarID());
        } catch (SQLException e) {
            System.out.println("There is a problem with balance update traffic query");
            e.printStackTrace();
        }
        try{
        String sql = "UPDATE subscribers set balance = '" + balance + "' Where CarID = '" + s.getCarID() + "'";
        statement.executeUpdate(sql);
        System.out.println("Executing update balance in subscribers");
        }catch(SQLException e){
            System.out.println("There is a problem with balance update subscribers query");
            e.printStackTrace();
        }
        s.setTimeIn(null);
        s.setTimeOut(null);
        return balance;
    }

    public static void insertTraffic(User u) {

        System.out.println("insertTraffic method in ParkingDB: " + u);

        String currentDate = u.getTimeIN();

        String[] columnNames = {"CarID", "DateIn","hasPaid"};
        Object[] inputValues = new Object[columnNames.length];
        inputValues[0] = u.getCarID();
        inputValues[1] = currentDate;
        inputValues[2] = u.getHasPaid();

        // prepare blob object from an existing binary column
        String insert = "insert into traffic (CarID,DateIn,hasPaid) values(?,?,?)";

        try {

            preparedStatement = conn.prepareStatement(insert);
            preparedStatement.setObject(1, inputValues[0]);
            preparedStatement.setObject(2, inputValues[1]);
            preparedStatement.setObject(3, inputValues[2]);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            System.out.println("There is a problem with querry insert into traffic");
            e.printStackTrace();
            e.toString();
        }

        // insert record into the traffic table 
    }

    public static void exitTraffic(User u) {
        System.out.println("exitVehicle method in parkingDB: " + u.getCarID());
        
        for(User us: traffic){
            if(us.getCarID().equals(u.getCarID())){
            u = us;
                break;
            }
        }
        try {

            statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

           System.out.println("exitTraffic carID: " + u.getCarID() + " time in: " + u.getTimeIN() + " time out: " + u.getTimeOut() + " balance: " + u.getBalance());  

            String sql = "UPDATE traffic SET DateOut = '" + u.getTimeOut() + "',balance = '" + u.getBalance() + "',hasPaid = '" + u.getHasPaid() + "'WHERE CarID ='" + u.getCarID() + "'";
            statement.executeUpdate(sql);
            System.out.println("Executing update time in exitVehicle method");

        } catch (SQLException e) {
            System.out.println("There is a problem with balance update querry");
            e.printStackTrace();
        }

    }

    public static double calculateCharge(User u) {

        double charge = 0.0;
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String currentDate = dateFormat.format(cal.getTime());
        Date d1;
        Date d2;
        double diff = 0.0;
        
            double diffSeconds = 0;
            double diffMinutes= 0;
            double diffHours = 0;
            
         //   System.out.println("Time in in calculate charge " + u.getTimeIN());

        try {
            d1 = dateFormat.parse(u.getTimeIN());
            d2 = dateFormat.parse(currentDate);
            diff = d2.getTime() - d1.getTime();
            
            diffSeconds = (double)diff / 1000;
            diffMinutes = (double)diff / (60 * 1000);
            diffHours = (double)diff / (60 * 60 * 1000);

        } catch (ParseException e) {
             System.out.println("Problem with timne formatting in calculateCharge");
        }
                    System.out.println("Time on the parking calculated in sec: " + diffSeconds + " in Minutes : " + diffMinutes + " in Hours: " + diffHours);

        int x = (int)diffMinutes;
        if(x < 30){
            charge = 0.0;
            u.setHasPaid(1);
        }
         if(x > 30 && x < 61 ){ 
               charge = 0.30;
         }else if(x > 60 && x < 120){
                   charge = 0.60;
         }else if(x > 120 && x < 180){
                   charge = 0.90;
         }else if(x > 180 && x < 240){
                   charge = 1.20;
         }else if(x > 240 && x < 300){
                   charge = 1.50;
         } else if(x > 300 && x < 360){
                charge = 1.80;
         } else if(x > 360){
             charge = 2.0;
         }
        
        
        return charge;
    }

    public static void initTraffic() {
        try {
            String query = "SELECT CarID, DateIn, DateOut, balance FROM traffic";
            // create the java statement
            statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String carID = resultSet.getString("carID");
                String DateIn = resultSet.getString("DateIn");
                String DateOut = resultSet.getString("DateOut");
                double balance = resultSet.getDouble("balance");
                User user = new User(carID, DateIn, DateOut, balance);
                traffic.add(user);
            }

        } catch (SQLException e) {
            System.out.println("There is a problem with getting traffic list");
            e.printStackTrace();
        }
    }
  public static void initSubscribers(){
      try{
           String query = "SELECT ID, FirstName, LastName, carID, accountNumber, balance FROM subscribers";

           // create the java statement
            statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                String FirstName = resultSet.getString("FirstName");
                String LastName = resultSet.getString("LastName");
                String carID = resultSet.getString("carID");
                String accountNumber = resultSet.getString("accountNumber");
                double balance = resultSet.getDouble("balance");
                Subscriber subscriber = new Subscriber(FirstName, LastName, carID, accountNumber, balance);
                subscribers.add(subscriber);
                System.out.println("ID: " + ID + " FisrtName: " + FirstName + " LastName: " + LastName + " carID " + carID + " balance: " + balance);
            }

        } catch (SQLException e) {
            System.out.println("There is a problem with getting traffic list");
            e.printStackTrace();
      }
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

    /**
     * @return the subscribers
     */
    
    public static ArrayList<User>getTraffic(){
        return traffic;
    }
    
    public static ArrayList<Subscriber> getSubscribers() {
        return subscribers;
    }
}
