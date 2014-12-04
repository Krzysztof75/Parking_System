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
//  this method update the count of free spaces and than call display() so that new value is displayed
    public void update(int freeSpace){         
        DisplayPanel.freeSpace = freeSpace;
        display();
    }
    @Override
    // displays count of free spaces
    public void display(){
        System.out.println(panelID + "Free space: " + freeSpace);
    }
    @Override
    // displays massage which is passed to it as argument
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

 

