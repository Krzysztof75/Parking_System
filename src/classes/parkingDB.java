/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import autParkSys.interfaces.iDataBase;
import java.io.Serializable;
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
 * parkingDB takes care of connection to the database and queries
 *
 */
public class parkingDB implements iDataBase, Serializable {

    // this array will hold The most recent information from traffic table
    // this array will hold the most recent information from subscriber table
    private static ArrayList<Subscriber> subscribers = new ArrayList<>();
    private static ArrayList<User> traffic = new ArrayList<>();                          // we can store here all info from the table traffic
    private static volatile parkingDB instance = new parkingDB();
    
    public static parkingDB getInstance() {
        return instance;
    }

    /**
     * @return
     */
    public static ArrayList<Subscriber> getSubscribers() {
        return subscribers;
    }

    /**
     * @return
     */
    public static ArrayList<User> getTraffic() {
        return traffic;
    }
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

    /**
     *
     */
    public parkingDB() {

        // these variables are neccessary to connect to the database
        url = "jdbc:mysql://localhost:3306/";
        dbName = "parkingsystem";
        driver = "com.mysql.jdbc.Driver";
        userName = "root";
        password = "";
        conn = null;
        connect();      // invoking connect() method of ParkingDB object in the constructor of ParkingDB
        initSubscribers();     // initialize subscribers arrayList with the values from database
        initTraffic();         // initialize traffic List with the data from database (where hasPaid = 0)
    }
    // Connecting to the database through the driver
    // there is a try - catch block to catch number of Exceptions possible during connection and disconnection
    // Exceptions - objects created when problems, which JRE can't resolve happen while running programs 
    // Exception handling lets developers handle such a problems in gracefull manner 

    /**
     * this method establishes connection with the database
     *
     */
    @Override
    public final void connect() {
        try {
            Class.forName(getDriver()).newInstance();
            //passing the privare variables declared above to connect to the database
            setConn(DriverManager.getConnection(getUrl() + getDbName(), getUserName(), getPassword()));
            //if successful this message will display
            ParkingSystem.log.info("Connected to the database");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            //if connection unseccessful catch exception and display this message
            ParkingSystem.log.error("There is a problem connecting to the database", e);
        }
    }

    /**
     * method disconnecting from the database
     */
    @Override
    public void disconnect() {
        try {
            getConn().close();
            //if disconnection successful display
            ParkingSystem.log.info("Disconnected from database");
        } catch (SQLException e) {
            //if not catch exception and display this message
            ParkingSystem.log.error("There is a problem disconnecting from the database", e);
        }
    }

    /**
     * register subscriber in the database
     *
     * @param s
     */
    @Override
    public void registerSubscriber(Subscriber s) {

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
            
            ParkingSystem.log.info("Subscribing " + s.getFirstName() + " " + s.getLastName());
            
        } catch (SQLException e) {
            if(e.getMessage().contains("Duplicate entry")){
                ParkingSystem.log.info(s.getFirstName() + " " + s.getLastName() + " is already subscribed");
            } else
            ParkingSystem.log.error("There is a problem with querry insert Subsriber");
        }
        
    }

    /**
     * sends query to the database to confirm if the user is present is
     * subscribers table
     *
     * @param u
     * @return
    *
     */
    @Override
    public boolean isSubscriber(User u) {
        boolean isSubscriber = false;
        
        String sql = "SELECT firstName FROM subscribers where carID='" + u.getCarID() + "'";
        
        try {
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ParkingSystem.log.info("Executing isSubscriber method");
            resultSet = statement.executeQuery(sql);
            
            if (resultSet.first() == true) {
                isSubscriber = true;
            }
            ParkingSystem.log.info(u.getCarID() + " is subscriber: " + isSubscriber);
            return isSubscriber;
            
        } catch (SQLException e) {
            ParkingSystem.log.error("There is a problem with isSubscriber querry", e);
        }
        return isSubscriber;
    }

    /**
     * removes subscriber from the Subscribers table
     *
     * @param s - subscriber object
     */
    
    @Override
    public void removeSubscriber(Subscriber s) {
        
        String delete = "DELETE FROM subscribers where carID='" + s.getCarID() + "'";
        
        try {
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ParkingSystem.log.info("Executing remove subscriber  method");
            statement.executeUpdate(delete);

            // remove the subscriber from the subscriber arrayList
            for (int i = 0; i < getSubscribers().size(); i++) {
                if (s.getCarID().equals(getSubscribers().get(i).getCarID())) {
                    getSubscribers().remove(i);
                }
            }
            ParkingSystem.log.info("Removing subscriber " + s.getFirstName() + " " + s.getLastName() + " from subscribers table");
            
        } catch (SQLException e) {
            ParkingSystem.log.error("There is a problem with isSubscriber querry", e);
        }
    }

    /**
     * returns current balance of the user passed to the method as an argument
     *
     * @param u
     * @return
     */
    @Override
    public double getBalance(User u) {
        double balance = 0;
        try {
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            
            String sql = "SELECT balance FROM traffic WHERE CarID='" + u.getCarID() + "'";
            resultSet = statement.executeQuery(sql);
            
            if (resultSet.first() == false) {
                ParkingSystem.log.warn("There is no matching record");
            }
            
            while (resultSet.next()) {
                //Retrieve by column name
                balance = resultSet.getDouble("balance");
                u.setBalance(balance);
            }
        } catch (SQLException e) {
            ParkingSystem.log.error("There is a problem with check balance querry", e);
        }
        return balance;
    }

    /**
     * updates the balance of the user in the User table
     *
     * @param u
     */
    @Override
    public void updateBalance(User u) {
        // get user balance
        // search for that user in the traffic list
        for (User traffic1 : traffic) {
            if (traffic1.getCarID().equals(u.getCarID())) {
                u = traffic1;
                u.setHasPaid(1);
                break;
            }
            ParkingSystem.log.info("Updating balance of the owner of " + u.getCarID());
        }
        try {
            
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            
            String sql = "UPDATE traffic SET DateOut = '" + u.getTimeOut() + "', balance = '" + u.getBalance() + "', hasPaid = '" + u.getHasPaid() + "' WHERE CarID ='" + u.getCarID() + "'";
            
            statement.executeUpdate(sql);
            ParkingSystem.log.info("updating traffic " + u.getCarID() + " " + u.getTimeOut() + " " + u.getBalance() + " " + u.getHasPaid());
            // update balance in the traffic table or if Subscriber update balance in the subscriber table
            u.setHasPaid(1);
            ParkingSystem.log.info("Paid for " + u.getCarID());
        } catch (SQLException e) {
            ParkingSystem.log.error("There is a problem with balance update querry", e);
        }
    }

    /**
     * updates the balance of the subscriber in the Subscriber table by the
     * amount=charge
     *
     * @param s
     * @param charge
     * @return
     */
    @Override
    public double updateBalance(Subscriber s, double charge) {
        
        double balance = getBalance(s) + charge;
        ParkingSystem.log.info("Present balance: " + getBalance(s) + " charge: " + charge);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String currentDate = dateFormat.format(cal.getTime());
        
        s.setTimeOut(currentDate);
        s.setBalance(balance);
        
        try {
            
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            
            String sql = "UPDATE traffic SET DateOUT = '" + s.getTimeOut() + "', balance = '" + charge + "', hasPaid = '" + s.getHasPaid() + "' WHERE CarID = '" + s.getCarID() + "'";
            
            statement.executeUpdate(sql);
            ParkingSystem.log.info("Updating traffic dateout " + s.getTimeOut() + " balance " + charge + " hasPaid " + s.getHasPaid());
            
            ParkingSystem.log.info("Paid for " + s.getCarID());
        } catch (SQLException e) {
            ParkingSystem.log.error("There is a problem with balance update traffic query", e);
        }
        try {
            String sql = "UPDATE subscribers set balance = '" + balance + "' Where CarID = '" + s.getCarID() + "'";
            statement.executeUpdate(sql);
            ParkingSystem.log.info("Executing update balance in subscribers");
        } catch (SQLException e) {
            ParkingSystem.log.error("There is a problem with balance update subscribers query", e);
        }
        s.setTimeIn(null);
        s.setTimeOut(null);
        return balance;
    }

    /**
     * inserts new record in the traffic table
     *
     * @param u
     */
    @Override
    public void insertTraffic(User u) {
        
        String currentDate = u.getTimeIN();
        
        String[] columnNames = {"CarID", "DateIn", "hasPaid"};
        Object[] inputValues = new Object[columnNames.length];
        inputValues[0] = u.getCarID();
        inputValues[1] = currentDate;
        inputValues[2] = u.getHasPaid();
        
        String insert = "insert into traffic (CarID,DateIn,hasPaid) values(?,?,?)";
        
        try {
            
            preparedStatement = conn.prepareStatement(insert);
            preparedStatement.setObject(1, inputValues[0]);
            preparedStatement.setObject(2, inputValues[1]);
            preparedStatement.setObject(3, inputValues[2]);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            ParkingSystem.log.info(u.getCarID() + " gets inserted into Traffic table");
            
        } catch (SQLException e) {
            ParkingSystem.log.error("There is a problem with querry insert into traffic", e);
        }
    }

    /**
     * updates the record for the given user in the traffic table to reflect the
     * vehicle leaving the parking lot
     *
     * @param u
     */
    @Override
    public void exitTraffic(User u) {
        
        for (User us : getTraffic()) {
            if (us.getCarID().equals(u.getCarID())) {
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
            ParkingSystem.log.info("Updating exit time, balance and boolean hasPaid in traffic table");
            
        } catch (SQLException e) {
            ParkingSystem.log.error("There is a problem with balance update querry", e);
        }
        
    }

    /**
     * calculates the charge based on the amount of time the vehicle spent at
     * the parking lot
     *
     * @param u
     * @return
     */
    @Override
    public double calculateCharge(User u) {
        
        double charge = 0.0;
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String currentDate = dateFormat.format(cal.getTime());
        Date d1;
        Date d2;
        double diff;
        
        double diffSeconds = 0;
        double diffMinutes = 0;
        double diffHours = 0;

         //   System.out.println("Time in in calculate charge " + u.getTimeIN());
        try {
            d1 = dateFormat.parse(u.getTimeIN());
            d2 = dateFormat.parse(currentDate);
            diff = d2.getTime() - d1.getTime();
            
            diffSeconds = (double) diff / 1000;
            diffMinutes = (double) diff / (60 * 1000);
            diffHours = (double) diff / (60 * 60 * 1000);
            
        } catch (ParseException e) {
            ParkingSystem.log.error("Problem with timne formatting in calculateCharge", e);
        }
        ParkingSystem.log.info("Time on the parking calculated in sec: " + diffSeconds + " in Minutes : " + diffMinutes + " in Hours: " + diffHours);
        
        int x = (int) diffMinutes;
        if (x < 30) {
            charge = 0.0;
            u.setHasPaid(1);
        }
        if (x > 30 && x < 61) {            
            charge = 0.30;
        } else if (x > 60 && x < 120) {
            charge = 0.60;
        } else if (x > 120 && x < 180) {
            charge = 0.90;
        } else if (x > 180 && x < 240) {
            charge = 1.20;
        } else if (x > 240 && x < 300) {
            charge = 1.50;
        } else if (x > 300 && x < 360) {
            charge = 1.80;
        } else if (x > 360) {
            charge = 2.0;
        }
        
        ParkingSystem.log.info("Charge for car with registration plate " + u.getCarID() + " equals " + charge);
        return charge;
    }

    /**
     * initialises traffic arrayList with the info from traffic table in the
     * database
     */
    public static void initTraffic() {
        try {
            String query = "SELECT CarID, DateIn, DateOut, balance, hasPaid FROM traffic WHERE hasPaid = 0";
            // create the java statement
            statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String carID = resultSet.getString("carID");
                String DateIn = resultSet.getString("DateIn");
                String DateOut = resultSet.getString("DateOut");
                double balance = resultSet.getDouble("balance");
                int hasPaid = resultSet.getInt("hasPaid");
                User user = new User(carID, DateIn, DateOut, balance, hasPaid);
                getTraffic().add(user);
            }
            ParkingSystem.log.info("intitializing traffic list with the users from traffic table in data base still present at the parking lot");
            
        } catch (SQLException e) {
            ParkingSystem.log.error("There is a problem with getting traffic list", e);
        }
    }

    /**
     * returns arrayList containing records of the vehicles which used the
     * parking lot
     *
     * @return
     */
    public static ArrayList readTrafficDB() {
        ArrayList<User> t = new ArrayList<>();
        try {
            String query = "SELECT CarID, DateIn, DateOut, balance, hasPaid FROM traffic";
            // create the java statement
            statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String carID = resultSet.getString("carID");
                String DateIn = resultSet.getString("DateIn");
                String DateOut = resultSet.getString("DateOut");
                double balance = resultSet.getDouble("balance");
                int hasPaid = resultSet.getInt("hasPaid");
                User user = new User(carID, DateIn, DateOut, balance, hasPaid);
                t.add(user);
                ParkingSystem.log.info("reading values from traffic table");
            }
            
        } catch (SQLException e) {
            ParkingSystem.log.error("There is a problem with reading traffic from data base", e);
        }
        return t;
    }

    /**
     * initialises arrayList subscribers with records contained in the
     * Subscribers table
     */
    public static void initSubscribers() {
        try {
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
                getSubscribers().add(subscriber);
            }
            ParkingSystem.log.info("initializing Subscribers list from data base");
        } catch (SQLException e) {
            ParkingSystem.log.error("There is a problem with getting traffic list", e);
        }
    }

    /**
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        parkingDB.url = url;
    }

    /**
     * @return
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
