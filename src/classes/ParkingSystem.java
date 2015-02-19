 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import autParkSys.interfaces.iSensor;
import autParkSys.interfaces.iDataBase;
import autParkSys.interfaces.Parkable;
import autParkSys.interfaces.Displayable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * The controlling class of the automated parking system
 * @author Kris
 */


public class ParkingSystem implements Parkable, Serializable {

   
    /* when registering objects such as cameras, gates, displayPanels
     the references to these objects are stored in the arrays specified below
     */
    private final ArrayList<Displayable> panels;      // array holding references to the display panels
    private final ArrayList<Gate> gates;                  // array holding references to the gates
    private final ArrayList<iSensor> entryCameras;     // array holding references to the EntryCameras
    private final ArrayList<iSensor> exitCameras;       // array holding references to the ExitCameras
    private static int freeSpace = 928;         // number of ramaining free spaces static means variable is shared among other objects
    //private User user;                          // this object will store information of each car entering parking
    private final iDataBase dataBase;                        // reference to database

     private static volatile ParkingSystem instance = new ParkingSystem();
    /**
     *
     */
    private ParkingSystem() {
        panels = new ArrayList<>();
        gates = new ArrayList<>();
        entryCameras = new ArrayList<>();
        exitCameras = new ArrayList<>();
        dataBase = parkingDB.getInstance();                      // dataBase object invoked in the constructor of ParkingSystem object
    }

    public static ParkingSystem getInstance(){
       return instance; 
    }
    /**
     *
     * @param p - object of class implementing Displayable
     */
    @Override
    public void registerDisplayPanel(Displayable p) {
        panels.add(p);

    }

    /**
     *
     * @param p
     */
    @Override
    public void removeDisplayPanel(Displayable p) {
        panels.remove(p);
    }

    /**
     *
     * @param freeSpace
     */
    @Override
    public void updateDisplayable(int freeSpace) {
        panels.stream().forEach((panel) -> {
            panel.update(freeSpace);
        });
    }

    /**
     *
     * @param g
     */
    @Override
    public void registerGate(Gate g) {
        gates.add(g);
    }

    /**
     *
     * @param g
     */
    @Override
    public void removeGate(Gate g) {
        gates.remove(g);
    }

    /**
     *
     * @param sensor
     */
    @Override
    public void registerCamera(iSensor sensor) {
        if (sensor instanceof EntryCamera) {
            entryCameras.add((EntryCamera) sensor);
        } else if (sensor instanceof ExitCamera) {
            exitCameras.add((ExitCamera) sensor);
            // else throw exception can't register that object as a camera     
        }
    }

    /**
     *
     * @param sensor
     */
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
    /**
     * initiates set of events related to the vehicle entry 
     * @param camera - object of EntryCamera 
     */
    public void setCarID(EntryCamera camera) {

        User user = new User(camera.getCarID());
        if (this.getDataBase().isSubscriber(user)) {                            // checks if subscribed 
             System.out.println(camera.getCarID() + " is subscriber");
        } else {                                                                // if not subscribed displays warning message
           System.out.println("This is paid parking if you don't want to use it leave within 30min");
        }
        System.out.println("User entering the parking " + user);
        parkingDB.getTraffic().add(user);
        this.getDataBase().insertTraffic(user);
        openGate(gates.get(0));
        setFreeSpaces(-1);

    }
    /**
     * initiates set of events related to the vehicle exit
     * @param camera - object of ExitCamera 
     */
    public void setCarID(ExitCamera camera) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String currentDate = dateFormat.format(cal.getTime());

        User user = new User(camera.getCarID());
        //find the right user object in the array list 
        for (int i = 0; i < parkingDB.getTraffic().size(); i++) {
            if (parkingDB.getTraffic().get(i).getCarID().equals(camera.getCarID())) {
                // set the timeOut = time of leaving
                parkingDB.getTraffic().get(i).setTimeOut(currentDate);
                user = parkingDB.getTraffic().get(i);
                break;
            }
        }
      //  user.setTimeOut(currentDate);
        double balance = getDataBase().calculateCharge(user);
        if(balance == 0){
            user.setHasPaid(1);
        }
        Subscriber subscriber;
        if(this.getDataBase().isSubscriber(user)){
            for (Subscriber sub : parkingDB.getSubscribers()) {
                if (user.getCarID().equals(sub.getCarID())) {
                    subscriber = sub;
                    subscriber.setHasPaid(1);
                    this.getDataBase().updateBalance(subscriber, balance);
                    openGate(gates.get(1));
                    setFreeSpaces(1);
                  //  parkingDB.exitTraffic(user);
                    break;
                }
            }  
        } else if(user.getHasPaid() == 1){
           openGate(gates.get(1));
           setFreeSpaces(-1);
            getDataBase().exitTraffic(user);
        }else{
            
            System.out.println("You need to pay before you can leave the parking");
        }

            for (int i = 0; i < parkingDB.getTraffic().size(); i++) {
                if (parkingDB.getTraffic().get(i).getCarID().equals(user.getCarID())) {
                    parkingDB.getTraffic().remove(i);
                }
            }
        
    }
    /**
     * writes the information from traffic table to the file the name of the file is the current date+time+.txt
     * @throws IOException 
     */
    public void backUpTraffic() throws IOException {
        
        ArrayList traffic = parkingDB.getBackUpTraffic();
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String currentDate = dateFormat.format(cal.getTime());
        
        String date = currentDate.replace(':', '.').replace(' ', '_');
  
        String tr;
        try {
            File file = new File( date + ".txt");
            try (FileWriter fileWriter = new FileWriter(file)) {
                for (Object traffic1 : traffic) {
                    tr = traffic1.toString();
                    fileWriter.write(tr);
                    fileWriter.write(System.lineSeparator()); //new line
                    fileWriter.flush();
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("There is a problem writing to a file");
        }
    }

    /**
     *
     * @param a
     */
    public void setFreeSpaces(int a) {
        freeSpace += a;

        updateDisplayable(freeSpace);

    }

    /**
     *
     * @return
     */
    public int getFreeSpaces() {
        return freeSpace;
    }

    /**
     *
     * @param u
     * @return
     */
    public boolean verifySubscriber(User u) {
        boolean subscriber = false;
        // querry DB if subscribed
        return subscriber;
    }

    /**
     *
     * @param g
     */
    public void openGate(Gate g) {
        g.open();
    }

    /**
     * @return the dataBase object
     */
    public iDataBase getDataBase() {
        return dataBase;
    }

}
