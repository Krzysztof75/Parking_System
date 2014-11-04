/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkit_flow;

/**
 *
 * @author Kris
 */
public class Dkit_flowTest {

    /**
     * @param args the command line arguments
     */
    static String [] incomingTrafficEntry1 = new String[]{"11D121222","12H12543","02Y34567","04Y34567"};
    static String [] incomingTrafficEntry2 = new String[]{"11D121452","12H12549","02B11567","0134527"};
    static String [] outgoingTraffic1 = new String[]{"11D121222","12H12543","02Y34567"};
    static String [] outgoingTraffic2 = new String[]{"11D121452","12H12549","02B11567"};
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        ParkingSystem dkit = new ParkingSystem();
        EntryCamera camEntry1 = new EntryCamera(dkit);
        EntryCamera camEntry2 = new EntryCamera(dkit);
        ExitCamera camExit1 = new ExitCamera(dkit);
        ExitCamera camExit2 = new ExitCamera(dkit);
        Panel p1 = new Panel(dkit);
        p1.setPanelName("panel nr1");
        Panel p2 = new Panel(dkit);
        p2.setPanelName("panel nr2");
        Panel p3 = new Panel(dkit);
        p3.setPanelName("panel nr3");
        Panel p4 = new Panel(dkit);
        p4.setPanelName("panel nr4");
        Gate gEntry1 = new Gate(dkit);
        gEntry1.setGateName("Entrance nr1");
        Gate gEntry2 = new Gate(dkit);
        gEntry2.setGateName("Entrance nr2");
        Gate gExit1 = new Gate(dkit);
        gExit1.setGateName("Exit nr1");
        Gate gExit2 = new Gate(dkit);
        gExit2.setGateName("Exit nr2");
        
        
        dkit.registerPanel(p1);
        dkit.registerPanel(p2);
        dkit.registerPanel(p3);
        dkit.registerPanel(p4);
        dkit.registerGate(gEntry1);
        dkit.registerGate(gEntry2);
        dkit.registerGate(gExit1);
        dkit.registerGate(gExit2);
        
        
        for(String reg : incomingTrafficEntry1){
            camEntry1.read(reg);
        }
        for(String reg : outgoingTraffic1){
            camExit1.read(reg);
        }
        for(String reg : incomingTrafficEntry2){
            camEntry2.read(reg);
        }
        for(String reg : outgoingTraffic2){
            camExit2.read(reg);
        }
       /* Have to distinguish between entryCamera and ExitCamera 
        possible solution to overload setCarID to take two different types of cameras as an argument
        1 type for entry scenario
        1 type for exit scenario
        
               */
    }
    
}
