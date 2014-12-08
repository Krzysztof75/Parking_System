 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkit_flow;

import java.util.ArrayList;

/**
 *
 * @author Kris
 */
public class ParkingSystem implements Parkable {

    /* when registering objects such as cameras, gates, displayPanels
    the references to these objects are stored in the arrays specified below
    */
    private final ArrayList<Displayable> panels;      // array holding references to the display panels
    private final ArrayList<Gate> gates;                  // array holding references to the gates
    private final ArrayList<EntryCamera> entryCameras;     // array holding references to the EntryCameras
    private final ArrayList<ExitCamera> exitCameras;       // array holding references to the ExitCameras
    private ArrayList<User> traffic;                          // we can store here all info from the table traffic
    private static int freeSpace = 928;         // number of ramaining free spaces static means variable is schared among other objects
    private User user;                          // this object will store information of each car entering parking
    public parkingDB dataBase;                        // reference to database

    public ParkingSystem() {
        panels = new ArrayList<>();
        gates = new ArrayList<>();
        traffic = new ArrayList<>();
        entryCameras = new ArrayList<>();
        exitCameras = new ArrayList<>();
        dataBase = new parkingDB();                      // dataBase object invoked in the constructor of ParkingSystem object
    }


    @Override
    public void registerDisplayPanel(Displayable p) {
        panels.add(p);

    }

    @Override
    public void removeDisplayPanel(Displayable p) {
        panels.remove(p);
    }

    @Override
    public void updateDisplayable(int freeSpace) {
        for (Displayable panel : panels) {
            panel.update(freeSpace);
        }
    }

    @Override
    public void registerGate(Gate g) {
        gates.add(g);
    }

    @Override
    public void removeGate(Gate g) {
        gates.remove(g);
    }

    @Override
    public void registerCamera(iSensor sensor) {
        if (sensor instanceof EntryCamera) {
            entryCameras.add((EntryCamera) sensor);
        } else if (sensor instanceof ExitCamera) {
            exitCameras.add((ExitCamera) sensor);
            // else throw exception can't register that object as a camera     
        }
    }

    @Override
    public void removeCamera(iSensor sensor) {
        if (sensor instanceof EntryCamera) {
            entryCameras.remove((EntryCamera) sensor);
        } else if (sensor instanceof ExitCamera) {
            exitCameras.remove((ExitCamera) sensor);
            // else throw exception can't register that object as a camera    
        }
    }

    /*
    Camera object holds car ID, this value is than passed on to the User constructor
    */
    public void setCarID(EntryCamera camera) {
        user = new User(camera.getCarID());
        traffic.add(user);
        user.setBalance(2);
        if(parkingDB.isSubscriber(user)){
            Subscriber subscriber = new Subscriber(user);
            dataBase.updateBalance(subscriber,2.0);
            openGate(gates.get(0));
        } else 
            System.out.println("This is paid parking if you don't want to use it leave within 30min");
        
        dataBase.insertTraffic(camera);
        System.out.println("User entering the parking " + user);
        openGate(gates.get(0));
        
        setFreeSpaces(-1);

    }

    public void setCarID(ExitCamera c) {
        user = new User(c.getCarID());
        //invoking user method setCarID passing the value of carID from camera to user object
        /* verify method isMember(boolean) if yes(true) open the gate immediately ("chance to display registered user's name")
        //if not (false) verify hasPaid(boolean) run appriopriate querry's against data base if (true) open the gate 
        // if not (false) display message that the user needs to pay
        */
        if(dataBase.isSubscriber(user)){                   // check if user is subscribed
           dataBase.exitVehicle(user);
           openGate(gates.get(1));
           setFreeSpaces(1);
        }else if(dataBase.verifyHasPaid(user)){             // if not subscriber but hasPaid
        openGate(gates.get(1));
        dataBase.exitVehicle(user);                   
        setFreeSpaces(1);
        
           for(int i = 0; i < traffic.size(); i++){
            if(traffic.get(i).getCarID().equals(user.getCarID())){
                traffic.remove(i);  
            }
           }
        
        } else 
         System.out.println("Your balance is not paid, you need to pay before you can leave the parking");
        }
    
    
    public void setFreeSpaces(int a) {
        freeSpace += a;
    
        updateDisplayable(freeSpace);

    }

    public int getFreeSpaces() {
        return freeSpace;
    }

    public boolean verifySubscriber(User u) {
      boolean subscriber = false; 
        // querry DB if subscribed
      return subscriber;
    }

    

    public void openGate(Gate g) {
        g.open();
    }

}
