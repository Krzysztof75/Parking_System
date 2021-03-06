/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autParSys.main;

import interfaces.Displayable;
import classes.Camera;
import static classes.CameraFactory.createCamera;
import classes.DisplayPanel;
import classes.Gate;
import classes.ParkingSystem;
import classes.Subscriber;
import classes.User;
import classes.parkingDB;
import java.io.IOException;

/**
 *
 * @author Kris
 */
public class ParkingSystemTest {

    /**
     * @param args the command line arguments
     */
    /* 
     Testing class 
     */
    // String arrays incoming and outgoing traffic simulate cars coming and leaving the car
    static String[] incomingTrafficEntry1 = new String[]{"11DP214", "12HA254", "02YL456", "04YK456"};
    static String[] incomingTrafficEntry2 = new String[]{"11DD212", "12HG125", "02BA156", "01JI527", "04LK298"};
    static String[] incomingTrafficEntry3 = new String[]{"LKP46789", "05JY667", "00KK555", "01HF444"};
    static String[] outgoingTraffic1 = new String[]{"11DP214", "12HA254", "02YL456", "04YK456"};
    static String[] outgoingTraffic2 = new String[]{"11DD212", "12HG125", "02BA156", "01JI527", "04LK298"};
    static String[] outgoingTraffic3 = new String[]{"LKP46789", "05JY667", "00KK555", "01HF444"};

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        // TODO code application logic here

        /*
         Setting up the Parking system
         Forming all the system elements
         */
        ParkingSystem parkingSystem = ParkingSystem.getInstance();

        Camera camEntry = createCamera("Entry", parkingSystem);
        camEntry.setCamID("camEntry1");
        Camera camExit = createCamera("Exit", parkingSystem);
        camExit.setCamID("camExit1");

        // assigning names for the gates and panels, names of the gates will be used in database to record the movement of particular users
        // which gate did the user enter through? which gate did he/she used to leave? Which gates handle most traffic?
        Gate GateEntry = new Gate(parkingSystem);
        GateEntry.setGateID("Entrance nr1 ");

        Gate GateExit = new Gate(parkingSystem);
        GateExit.setGateID("Exit nr1 ");

        Displayable panel = new DisplayPanel(parkingSystem);
        panel.setPanelID("Display Panel nr1 ");

        // connecting the elements of the system
        parkingSystem.registerDisplayPanel(panel);
        parkingSystem.registerGate(GateEntry);
        parkingSystem.registerGate(GateExit);
        parkingSystem.registerCamera(camEntry);
        parkingSystem.registerCamera(camExit);

        //  This part is just for the simulation purposes only
        //  Registering Subscribers
        Subscriber sub1 = new Subscriber("Krzysztof", "Gilewski", "04LK298", "090786876567", 0.0);
        Subscriber sub2 = new Subscriber("Hardik", "Thakkar", "11DP214", "8764563564554", 0.0);
        Subscriber sub3 = new Subscriber("Dean", "McMahon", "12HG125", "862542545426882", 0.0);
        Subscriber sub4 = new Subscriber("Gerry", "McGrane", "01JI527", "625645158451819", 0.0);
        Subscriber sub5 = new Subscriber("Tony", "McMarron", "LKP46789", "787575576567675", 0.0);
        Subscriber sub6 = new Subscriber("Jimmy", "NcNally", "01HF444", "58585586585668586", 0.0);
        Subscriber sub7 = new Subscriber("Robert", "DeNiro", "02YL456", "7867568758548585", 0.0);

        parkingSystem.getDataBase().registerSubscriber(sub1);
        parkingSystem.getDataBase().registerSubscriber(sub2);
        parkingSystem.getDataBase().registerSubscriber(sub3);
        parkingSystem.getDataBase().registerSubscriber(sub4);
        parkingSystem.getDataBase().registerSubscriber(sub5);
        parkingSystem.getDataBase().registerSubscriber(sub6);
        parkingSystem.getDataBase().registerSubscriber(sub7);

        //  vehicles enter the parking
//        camEntry.read(incomingTrafficEntry1);
//        System.out.println();
//        camEntry.read(incomingTrafficEntry2);
//        System.out.println();
//        camEntry.read(incomingTrafficEntry3);
//        System.out.println();
//        remove subscriber sub3 
//        parkingSystem.getDataBase().removeSubscriber(sub3);
//        vehicles exiting the traffic
//        camExit.read(outgoingTraffic1);
//        System.out.println();
//      
//        camExit.read(outgoingTraffic2);
//        System.out.println();
//        
//        camExit.read(outgoingTraffic3);
//        System.out.println();

// first 30 min free of charge - wait at least this much to see the charge being calculated  
//    Paying for single user if not subscriber      
      User car = new User("05JY667");
//     find the rigt user in the arraylist tarffic       
        for(int i = 0; i < parkingDB.getTraffic().size(); i++){
            if(parkingDB.getTraffic().get(i).getCarID().equals(car.getCarID())){
            car = parkingDB.getTraffic().get(i);
        }
        }
        double charge = parkingSystem.getDataBase().calculateCharge(car);
        car.setBalance(charge);
        parkingSystem.getDataBase().updateBalance(car);
// run it to exit single vehicle (make sure the vehicle is still at the parking lot
//      Single vehicle leaves the parking (must pay before)
          camExit.read("05JY667");
//     printing out the users from the traffic arraylist
//        for(User u : parkingDB.getTraffic())
//            System.out.println(u);       
//        System.out.println();
// run this piece of code to write the content of the traffic table to the file
//            ParkingSystem.backUpTraffic();
        // ParkingSystem.log.info("Problem writing backup to the file" + ex.getMessage()); 
        parkingSystem.getDataBase().disconnect();

    }

}
