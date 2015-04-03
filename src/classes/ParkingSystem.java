 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import autParSys.main.ParkingSystemTest;
import interfaces.iSensor;
import interfaces.iDataBase;
import interfaces.Parkable;
import interfaces.Displayable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * The controlling class of the automated parking system
 *
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
    public static int saveRecordFrequency = 1000;
    private final iDataBase dataBase;                        // reference to database
    public static Logger log = Logger.getLogger(ParkingSystemTest.class);

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
         BasicConfigurator.configure();
        log.info("Creating ParkingSystem object");
    }

    public static ParkingSystem getInstance() {
        return instance;
    }

    /**
     *
     * @param p - object of class implementing Displayable
     */
    @Override
    public void registerDisplayPanel(Displayable p) {
        panels.add(p);
        log.info("Registering panel " + p.getPanelID());

    }

    /**
     *
     * @param p
     */
    @Override
    public void removeDisplayPanel(Displayable p) {
        panels.remove(p);
        log.info("Unregistering panel " + p.getPanelID());
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
        log.info("Registering " + g.getGateID());
    }

    /**
     *
     * @param g
     */
    @Override
    public void removeGate(Gate g) {
        gates.remove(g);
        log.info("Unregistering " + g.getGateID());
    }

    /**
     *
     * @param sensor
     */
    @Override
    public void registerCamera(iSensor sensor) {
        if (sensor instanceof EntryCamera) {
            entryCameras.add((EntryCamera) sensor);
            log.info("Registering " + ((Camera) sensor).getCamID());
        } else if (sensor instanceof ExitCamera) {
            exitCameras.add((ExitCamera) sensor);
            log.info("Registering " + ((Camera) sensor).getCamID());
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
            log.info("Unregistering " + ((Camera) sensor).getCamID());
        } else if (sensor instanceof ExitCamera) {
            exitCameras.remove((ExitCamera) sensor);
            log.info("Unregistering " + ((Camera) sensor).getCamID());
            // else throw exception can't register that object as a camera    
        }
    }

    /*
     Camera object holds car ID, this value is than passed on to the User constructor
     */
    /**
     * initiates set of events related to the vehicle entry
     *
     * @param camera - object of EntryCamera
     */
    public void setCarID(EntryCamera camera) {

        User user = new User(camera.getCarID());
        if (!this.getDataBase().isSubscriber(user)) {                            // checks if subscribed if not display warning message                                                                // if not subscribed displays warning message
            System.out.println("***This is paid parking if you don't want to use it leave within 30min***");
        }
        log.info("User entering the parking " + user);
        parkingDB.getTraffic().add(user);
        // add the user to the traffic array of the ParkingDB so that it contains users currently present at the parking lot
        this.getDataBase().insertTraffic(user);
        openGate(gates.get(0));
        setFreeSpaces(-1);

    }

    /**
     * initiates set of events related to the vehicle exit
     *
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
        if (balance == 0) {
            user.setHasPaid(1);
        }
        Subscriber subscriber;
        if (this.getDataBase().isSubscriber(user)) {
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
        } else if (user.getHasPaid() == 1) {
            openGate(gates.get(1));
            setFreeSpaces(-1);
            getDataBase().exitTraffic(user);
        } else {

            System.out.println("***You need to pay before you can leave the parking***");
        }

        // remove the user from the traffic array in ParkingDB so that it only stores the users currently present at the parking lot
        for (int i = 0; i < parkingDB.getTraffic().size(); i++) {
            if (parkingDB.getTraffic().get(i).getCarID().equals(user.getCarID())) {
                parkingDB.getTraffic().remove(i);
            }
        }

    }

    /**
     * writes the information from traffic table to the file the name of the
     * file is the current date+time+.txt
     *
     * @throws IOException
     */
    public static void backUpTraffic() {

        ArrayList traffic = parkingDB.readTrafficDB(); 

        DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String currentDate = dateFormat.format(cal.getTime());

        // format current date so that it can be used as a file name
        String date = currentDate.replace(':', '.').replace(' ', '_');

        String tr;
        File file = new File(date + ".txt");
        try (FileWriter fileWriter = new FileWriter(file)) {
            for (Object traffic1 : traffic) {
                User user = (User)traffic1;
                // only save records of the vehicles which already left the parking
                if(user.getTimeOut()!= null){
                tr = traffic1.toString();
                fileWriter.write(tr);
                fileWriter.write(System.lineSeparator()); //new line
                fileWriter.flush();
                }
            }
        } catch (IOException ex) {
            log.error("There is a problem writing to a file", ex);
        }
        log.info("backing up data");
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
