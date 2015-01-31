# Parking_System
Author: Krzysztof Gilewski
This is unfinished project!
This is my proposition for an automated parking system.
This system suppose to automatically collect information about the users of the parking facility, calculate the appropriate
charge, update the balance, store the traffic information as well as subscribed users in the data base, ensure the 
charge is paid before allowing to leave the facility, allow to implement subscribing option.   


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
messages sent to it. I used observer pattern to monitor the changes to the freeSpaces variable in parkingSystem object 
(subject) and automatically update the diplay to reflect current number of freeSpaces at the parking lot.
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



