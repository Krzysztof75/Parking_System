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
    private ArrayList<Displayable> panels;      // array holding references to the display panels
    private ArrayList<Gate> gates;
    private ArrayList<EntryCamera> entryCameras;
    private ArrayList<ExitCamera> exitCameras;
    private ArrayList<User> traffic;
    private static int freeSpace = 928;         // number of ramaining free spaces change that to reflect value from DB
    private User user;
    public parkingDB db;

    public ParkingSystem() {
        panels = new ArrayList<>();
        gates = new ArrayList<>();
        entryCameras = new ArrayList<>();
        exitCameras = new ArrayList<>();
        User user;                      // this object will store information of each car entering parking
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

    // Camera object holds car ID, this value is than passed on to the User constructor

    public void setCarID(EntryCamera c) {
        user = new User(c.carID);
        // verify if subscriber
        // send info to DB TRAFFIC
        if (user.getIsSubscriber()) {
            openGate(gates.get(0));
        } else {
            panels.get(0).displayMessage("This is paid parking If you don't want to be charged please leave within 30 min");
            openGate(gates.get(0));
        }
        db.enterVehicle();
        setFreeSpaces(-1);

    }

    public void setCarID(ExitCamera c) {
        user.setCarID(c.carID);
        // verify isMember if not verify hasPaid
        // 
        openGate(gates.get(1));
        db.exitVehicle();
        setFreeSpaces(1);

    }

    public void setFreeSpaces(int a) {
        freeSpace += a;
        updateDisplayable(freeSpace);

    }

    public int getFreeSpaces() {
        return freeSpace;
    }

    public void verifySubscriber(User u) {
        // querry DB if subscribed
        u.setIsSubscriber(true);
    }

    public void verifyHasPaid(User u) {
        // querry DB if hasPaid()
    }

    public void openGate(Gate g) {
        g.open();
    }

}
