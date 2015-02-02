# Parking_System
Author: Krzysztof Gilewski
This is unfinished project!
This is my proposition for an automated parking system.
This system automatically collects information about the users of the parking facility, calculates the appropriate charge, updates the balance, stores the traffic information as well as subscribed users in the data base, ensures the 
charge is paid before allowing the user to leave the facility, allows to implement subscribing option.   

The system consists of following classes and interfaces:

interface iSensor   : provides the contract consisting of two methods:
- read (String s)
- send (String s)

abstract camera class - implements iSensor
instance variables:
ParkingSystem parkingSystem;
String carID;
methods:
- void read (String carID) accepts a string as an argument
- void read (String [] carID) accepts array of Strings
- void setCarID(String carID)
- String getCarID();
- abstract void send (String carID)

EntryCamera and ExitCamera classes - extend camera class, the object of either class passes the received string value 
to the parkingSystem object by invoking its overloaded method setCarID(EntryCamera) or setCarID(ExitCamera).
constructor:
public EntryCamera(ParkingSystem parkingSystem);
methods:
-void read (String carID);
-void read (String [] carID);
-void send(String CarID);

interface Displayable - provide contract consisting of 2 methods:
- display()
- displayMessage (String s)
- update(int i);

DisplayPanel class - implements Displayable, displays the current number of free spaces at the parking lot as well as 
messages sent to it. I used observer pattern to monitor the changes to the freeSpaces variable in parkingSystem object (subject) and automatically update the diplay to reflect current number of freeSpaces at the parking lot.
instance variables:
ParkingSystem parkingSystem;
int freeSpaces;
String panelID;
constructor:
public DisplayPanel(ParkingSystem parking system);
methods:
- void display();
- void displayMessage(String s);
- void update();
- void setPanelID(String s);
- String getPanelID();

Gate class - simulates gate with the assumption that there is an automated mechanism of closing the gate
instance variables:
- ParkingSystem parkingSystem;
- String gateID;
constructor:
public gate(Parkable pS)
methods:
- void open();
- void setGateID(String s);
- String getGateID();

User class - Objects of this class are used to store the user information
instance variables:
- String carID;                 
- double balance;
- String timeIn;
- String timeOut;
- int hasPaid;
Constructors:
- public User();
- public User(String carID)
- public User(String carID, String TimeIN)
- public User(String carID,String timeIN, String TimeOut, double balance, int hasPaid)
methods:
- int getHasPaid();
- void setHasPaid(int i);
- String getCarID();
- void setCarID(String s);
- String getTimeOou();
- void setTimeOut(String s);
- String getTimeIN();
- void setTimeIN(String s);
- double getBalance();
- void setBalance(double d);

Subscriber class - extends User , objects of this class allow to store information regarding subscribed users
instance variables:
- all inherited from User class
- String FirstName;
- String LastName();
- String adress();
- String accountNumber

interface Parkable - ensure constract for the following methods:
- void registerDisplayPanel(Displayable d);
- void removeDisplayPanel(Displayable d);
- void updateDisplayable(int freeSpace); 
- void registerGate(Gate g);
- void removeGate(Gate g);
- void registerCamera(iSensor is);
- void removeCamera(iSensor is);

ParkingSystem class : implements Parkable, controls the operation of the parking lot
class variables:
- int freeSpaces;
instatnce variables:
- private final ArrayList<Displayable> panels;
- private final ArrayList<Gate> gates;                 
- private final ArrayList<iSensor> entryCameras;    
- private final ArrayList<iSensor> exitCameras;       
- private static int freeSpace = 928;         
- private User user;                         
- public iDataBase dataBase; 
construcor::
public ParkingSystem();
methods:
- void registerDisplayPanel(Displayable p);
- void removeDisplayPanel(Displayable p);
- void updateDisplayable(int freeSpace);
- void registerGate(Gate g)
- void removeGate(Gate g);
- void registerCamera(iSensor sensor);
- void removeCamera(iSensor sensor);
- void setCarID(EntryCamera camera);
- void setCarID(ExitCamera c);
- void backUpTraffic() throws IOException     - write the information about the parking usage (traffic) in the file
- void setFreeSpaces(int a);
- int getFreeSpaces();
- boolean verifySubscriber(User u);
- void openGate(Gate g);

interface iDabatBase  - ensure contract for the following methods:
- void connect();
- void disconnect();
- void registerSubscriber(Subscriber s);
- boolean isSubscriber(User u);
- void removeSubscriber(Subscriber s);
- double getBalance(User u);
- void updateBalance(User u);
- double updateBalance(Subscriber s, double charge);
- void insertTraffic(User u);
- void exitTraffic(User u);
- double calculateCharge(User u);

class parkingDB - implements iDataBase, takes care of the connection to the data base and communication with it through build in queries
class instance:
- private static ArrayList<Subscriber> subscribers;
- private static ArrayList<User> traffic;
constructor:
public parkingDB();
methods:
- static ArrayList<Subscriber> getSubscribers();
- static ArrayList<User> getTraffic()
- void connect();
- void disconnect();
- void registerSubscriber(Subscriber s);
- void removeSubscriber(Subscriber s);
- boolean isSubscriber(User u);
- double getBalance(User u);
- void updateBalance(User u);
- double updateBalance(Subscriber s, double charge);
- void insertTraffic(User u);
- void exitTraffic(User u);
- double calculateCharge(User u);
- static void initTraffic();
- static void initSubscribers();
- String getUrl();
- void setUrl(String url);
- String getDbName();
- void setDbName(String dbName);
- String getDriver();
- void setDriver(String driver);
- String getUserName();
- void setUserName(String userName);
- String getPassword();
- void setPassword(String password);
- static Connection getConn();
- void setConn(Connection conn);

