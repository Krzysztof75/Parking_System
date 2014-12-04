/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkit_flow;
///////////////////////////
/**
 *
 * @author Kris
 */
public class Dkit_flowTest {

    /**
     * @param args the command line arguments
     */
    /* 
    Testing class 
    */
    // String arrays incoming and outgoing traffic simulate cars coming and leaving the car
    static String [] incomingTrafficEntry1 = new String[]{"11DP212","12HA254","02YL456","04YK456"};
    static String [] incomingTrafficEntry2 = new String[]{"11DD214","12HG125","02BA156","01JI527"};
    static String [] outgoingTraffic1 = new String[]{"11DP212","12HA254","02YL456","04YK456"};
    static String [] outgoingTraffic2 = new String[]{"11DD214","12HG125","02BA156","01JI527"};
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        /*
        Setting up the Parking system
        Forming all the system elements
        */
        ParkingSystem parkingSystem = new ParkingSystem();
        
        EntryCamera camEntry = new EntryCamera(parkingSystem);
        ExitCamera camExit = new ExitCamera(parkingSystem);
        
        //parkingDB db = new parkingDB();
        
       
        // assigning names for the gates and panels, names will be used in database to record the movement of particular users
        // which gate did the user enter through? which gate did he/she used to leave?
        Gate GateEntry = new Gate(parkingSystem);
        GateEntry.setGateID("Entrance nr1 ");
        Gate GateExit = new Gate(parkingSystem);
        GateExit.setGateID("Exit nr1 ");
        
        DisplayPanel panel = new DisplayPanel(parkingSystem);
        panel.setPanelID("Display Panel nr1 ");
        
        
        
        parkingSystem.registerDisplayPanel(panel);    
        parkingSystem.registerGate(GateEntry);
        parkingSystem.registerGate(GateExit);
        parkingSystem.registerCamera(camEntry);
        parkingSystem.registerCamera(camExit);
        
        //Registering Subscriber
        Subscriber sub = new Subscriber("Krzysztof", "Gilewski","04LK298","090786876567");
        parkingDB.registerSubscriber(sub);
    
        
        camEntry.read(incomingTrafficEntry1);
        System.out.println();
        camExit.read(outgoingTraffic1);
        System.out.println();
        camEntry.read(incomingTrafficEntry2);
        System.out.println();
        camExit.read(outgoingTraffic2);
        System.out.println();
        
        
        
        parkingDB.disconnect();
        
      
    }
    
}
