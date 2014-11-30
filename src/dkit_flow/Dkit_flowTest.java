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
    static String [] incomingTrafficEntry1 = new String[]{"11D121222","12H12543","02Y34567","04Y34567"};
    static String [] incomingTrafficEntry2 = new String[]{"11D121452","12H12549","02B11567","0134527"};
    static String [] outgoingTraffic1 = new String[]{"11D121222","12H12543","02Y34567","02G13425"};
    static String [] outgoingTraffic2 = new String[]{"11D121452","12H12549","02B11567","02G13425"};
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        /*
        Setting up the Parking system
        Forming all the system elements
        */
        ParkingSystem parkingSystem = new ParkingSystem();
        
        EntryCamera camEntry = new EntryCamera(parkingSystem);
        ExitCamera camExit = new ExitCamera(parkingSystem);
        
        parkingDB db = new parkingDB();
//        db.connect();
//        db.disconnect();
       
        // assigning names for the gates and panels, names will be used in database to record the movement of particular users
        // which gate did the user enter through? which gate did he/she used to leave?
        Gate GateEntry = new Gate(parkingSystem);
        GateEntry.setGateName("Entrance nr1");
        Gate GateExit = new Gate(parkingSystem);
        GateExit.setGateName("Exit nr1");
        
        DisplayPanel panel = new DisplayPanel(parkingSystem);
        panel.setPanelID("Display Panel 1");
        
        
        
        parkingSystem.registerDisplayPanel(panel);    
        parkingSystem.registerGate(GateEntry);
        parkingSystem.registerGate(GateExit);
        parkingSystem.registerCamera(camEntry);
        parkingSystem.registerCamera(camExit);
        
        
        for(String reg : incomingTrafficEntry1){
            camEntry.read(reg);
        }
        for(String reg : outgoingTraffic1){
            camExit.read(reg);
        }
        for(String reg : incomingTrafficEntry2){
            camEntry.read(reg);
        }
        for(String reg : outgoingTraffic2){
            camExit.read(reg);
        }
       /* Have to distinguish between entryCamera and ExitCamera 
        possible solution to overload setCarID to take two different types of cameras as an argument
        1 type for entry scenario
        1 type for exit scenario
        
               */
    }
    
}
