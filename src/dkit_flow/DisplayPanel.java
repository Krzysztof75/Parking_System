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
public class DisplayPanel implements Displayable {
    
    private final ParkingSystem dkit;        
    private static int freeSpace;           // the number of the freeSpaces at the parking lot
    private String panelID;         // the identification number of the display will be stored here
    
    public DisplayPanel(ParkingSystem dkit){
        this.dkit = dkit;
    }   
    
    @Override
    public void update(int freeSpace){         // 
        DisplayPanel.freeSpace = freeSpace;
        display();
    }
    @Override
    public void display(){
        System.out.println(panelID + "Free space: " + freeSpace);
    }
    @Override
    public void displayMessage(String message){
        System.out.println(message);
        
    }
    public String getPanelID(){
        return panelID;
    }
    public void setPanelID(String id){
        panelID = id;
    }
    }

 

