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

    /**
     * @return the TOTALCOUNT
     */
    private final ArrayList<Displayable> panels;      // array holding references to the display panels
    private final ArrayList<Gate> gates;
    private final ArrayList<EntryCamera> entryCameras;
    private final ArrayList<ExitCamera> exitCameras;
    private ArrayList<User> traffic;
    private static int freeSpace = 928;         // number of ramaining free spaces change that to reflect value from DB
    private User user;                          // this object will store information of each car entering parking
    public parkingDB db;

    public ParkingSystem() {
        panels = new ArrayList<>();
        gates = new ArrayList<>();
        entryCameras = new ArrayList<>();
        exitCameras = new ArrayList<>();
        db = new parkingDB();
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
        user.setBalance(2);
        System.out.println(user);
        openGate(gates.get(0));
        // verify if subscriber
        // send info to DB TRAFFIC appriopriate querry will insert a record into the databse Traffic table
        //if (this.verifySubscriber(user)) {
            //the program will invoke user.getIsSubscriber if confirmed condition boolean = true than
            //the gate will open, 
            
        //} else {
            //if not boolean = false than appriopriate massage is displayed and than the gate opens
           // panels.get(0).displayMessage("This is paid parking If you don't want to be charged please leave within 30 min");
           // openGate(gates.get(0));
       // }
        //the DataBase object is invoked enterVehicle method which will insert the recordset in the database
        db.enterVehicle(camera);
        //setFreeSpaces method substract 1 from static int freeSpaces ... static means all objects share the same variable
        setFreeSpaces(-1);

    }

    public void setCarID(ExitCamera c) {
        user = new User(c.getCarID());
        //invoking user method setCarID passing the value of carID from camera to user object
        /* verify method isMember(boolean) if yes(true) open the gate immediately ("chance to display registered user's name")
        //if not (false) verify hasPaid(boolean) run appriopriate querry's against data base if (true) open the gate 
        // if not (false) display message that the user needs to pay
        */
        openGate(gates.get(1));
        db.exitVehicle(user);
        setFreeSpaces(1);

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

    public boolean verifyHasPaid(User u) {
        boolean hasPaid = false;
        // querry DB if hasPaid()
                return hasPaid;
    }

    public void openGate(Gate g) {
        g.open();
    }

}
