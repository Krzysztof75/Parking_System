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
public class ParkingSystem implements Parkable{
    
    private ArrayList<Displayable> panels;      // array holding references to the display panels
    private ArrayList<Gate> gates;
    private final static int totalCount = 400;         // number of parking spaces
    private static int freeSpace = totalCount;                      // number of ramaining free spaces
    private User user;
    
    
    
    public ParkingSystem() {
        panels = new ArrayList<>();
        gates = new ArrayList<>();
        user = new User();
    }
    

   public void registerDisplayPanel(Displayable p){
       panels.add(p);
   }
   
   public void removeDisplayPanel(Displayable p){
       panels.remove(p);
   }
   
   public void updateDisplayable(int freeSpace){
       for(Displayable panel : panels)
           panel.update(freeSpace);
   }
   
   public void registerGate(Gate g){
       gates.add(g);
   }
   public void removeGate(Gate g){
       gates.remove(g);
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
        updateDisplayable(freeSpace);
        
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


