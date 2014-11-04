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
public class ParkingSystem {
    
    private ArrayList<Panel> panels;      // array holding references to the display panels
    private ArrayList<Gate> gates;
    private final static int totalCount = 400;         // number of parking spaces
    private static int freeSpace = totalCount;                      // number of ramaining free spaces
    private User user;
    
    
    
    public ParkingSystem(){
        panels = new ArrayList<>();
        gates = new ArrayList<>();
        user = new User();
    }
    
   public void registerPanel(Panel p){
       panels.add(p);
   }
   
   public void registerGate(Gate g){
       gates.add(g);
   }
    
   public void updatePanels(int freeSpace){
       for(Panel panel : panels)
           panel.update(freeSpace);
   }
  
    public void setCarID(EntryCamera c){
        user.setCarID(c.carID);
        // verify
        // send info to DB TRAFFIC
        
        openGate(gates.get(0));
        setFreeSpaces(-1);
        
       
    }
    
    public void setCarID(ExitCamera c){
        user.setCarID(c.carID);
        // verify isMember if not verify hasPaid
        // 
        openGate(gates.get(1));
        setFreeSpaces(1);
        
    }
    
    public void setFreeSpaces(int a){
        freeSpace += a;
        updatePanels(freeSpace);
        
    }
    public int getFreeSpaces(){
        return freeSpace;
    }
    public void verifySubscriber(User u){
        // querry DB if subscribed
        u.setIsSubscriber(true);
    }
    public void verifyHasPaid(User u){
        // querry DB if hasPaid()
    }
    public void openGate(Gate g){
        g.open();
    }
    
}


