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
public class Dkit_flowTest {

    /**
     * @param args the command line arguments
     */
    /* 
    Testing class 
    */
    // String arrays incoming and outgoing traffic simulate cars coming and leaving the car
    static String [] incomingTrafficEntry1 = new String[]{"11DP214","12HA254","02YL456","04YK456"};
    static String [] incomingTrafficEntry2 = new String[]{"11DD212","12HG125","02BA156","01JI527","04LK298"};
    static String [] incomingTrafficEntry3 = new String[]{"LKP46789","05JY667","00KK555","01HF444"};
    static String [] outgoingTraffic1 = new String[]{"11DP214","12HA254","02YL456","04YK456"};
    static String [] outgoingTraffic2 = new String[]{"11DD212","12HG125","02BA156","01JI527","04LK298"};
    static String [] outgoingTraffic3 = new String[]{"LKP46789","05JY667","00KK555","01HF444"};
    
    public static void main(String[] args) {
        // TODO code application logic here
  
        /*
        Setting up the Parking system
        Forming all the system elements
        */
        ParkingSystem parkingSystem = new ParkingSystem();
        EntryCamera camEntry = new EntryCamera(parkingSystem);
        ExitCamera camExit = new ExitCamera(parkingSystem);
        
       
        // assigning names for the gates and panels, names of the gates will be used in database to record the movement of particular users
        // which gate did the user enter through? which gate did he/she used to leave? Which gates handle most traffic?
        Gate GateEntry = new Gate(parkingSystem);
        GateEntry.setGateID("Entrance nr1 ");
        Gate GateExit = new Gate(parkingSystem);
        GateExit.setGateID("Exit nr1 ");
        
        DisplayPanel panel = new DisplayPanel(parkingSystem);
        panel.setPanelID("Display Panel nr1 ");
        
        
        // connecting the elements of the system
        parkingSystem.registerDisplayPanel(panel);    
        parkingSystem.registerGate(GateEntry);
        parkingSystem.registerGate(GateExit);
        parkingSystem.registerCamera(camEntry);
        parkingSystem.registerCamera(camExit);
        
      //  Registering Subscriber
//        Subscriber sub = new Subscriber("Krzysztof", "Gilewski","04LK298","090786876567", 0.0);
//        Subscriber sub2 = new Subscriber("Hardik", "Thakkar","11DP214","8764563564554", 0.0);
//        Subscriber sub3 = new Subscriber("Dean", "McMahon", "12HG125","862542545426882", 0.0);
//        Subscriber sub4 = new Subscriber("Gerry", "McGrane", "01JI527", "625645158451819", 0.0);
//        Subscriber sub5 = new Subscriber("Tony", "McMarron", "LKP46789", "787575576567675", 0.0);
//        Subscriber sub6 = new Subscriber("Jimmy", "NcNally", "01HF444","58585586585668586", 0.0);
//        Subscriber sub7 = new Subscriber("Robert", "DeNiro", "02YL456", "7867568758548585", 0.0);
//        parkingDB.registerSubscriber(sub);
//        parkingDB.registerSubscriber(sub2);
//        parkingDB.registerSubscriber(sub3);
//        parkingDB.registerSubscriber(sub4);
//        parkingDB.registerSubscriber(sub5);
//        parkingDB.registerSubscriber(sub6);
//        parkingDB.registerSubscriber(sub7);
//        
//        
//       //  vehicle enters the parking
//        camEntry.read(incomingTrafficEntry1);
//        System.out.println();
//          camEntry.read(incomingTrafficEntry2);
//        System.out.println();
//        camEntry.read(incomingTrafficEntry3);
//        System.out.println();

//        
//        camExit.read(outgoingTraffic1);
//        System.out.println();
//      
//        camExit.read(outgoingTraffic2);
//        System.out.println();
//       
//        camExit.read(outgoingTraffic3);
//        System.out.println();
////        
        User car = new User("04YK456");
        
        for(int i = 0; i < parkingDB.getTraffic().size(); i++){
            if(parkingDB.getTraffic().get(i).getCarID().equals(car.getCarID())){
            car = parkingDB.getTraffic().get(i);
            System.out.println("Car: " + car);
        }
        }
        double charge = parkingDB.calculateCharge(car);
        car.setBalance(charge);
        System.out.println(charge);
        parkingDB.updateBalance(car);
        
        camExit.read("04YK456");
//           camExit.read("04YK456");
        for(User u : parkingDB.getTraffic())
            System.out.println(u);
        
        System.out.println();
        
        for(Subscriber s : parkingDB.getSubscribers())
            System.out.println(s);
        
        parkingDB.disconnect();
//        
      
    }
    
}
