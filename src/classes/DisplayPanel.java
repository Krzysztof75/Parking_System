/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import autParkSys.interfaces.Displayable;

/**
 * displays the current number of free spaces at the parking lot 
 * and messages passed to it
 */
public class DisplayPanel implements Displayable {
    
    private final ParkingSystem parkingSystem;      // we will link two objects (ParkingSystem and DisplayPanel through this link  
    private static int freeSpace;           // the number of the freeSpaces at the parking lot
    private String panelID;                 // the identification number of the display will be stored here we can include a List of panels over here
    
    /**
     *
     * @param parkingSystem
     */
    public DisplayPanel(ParkingSystem parkingSystem){
        this.parkingSystem = parkingSystem;
    }   
     
    /**
     * this method update the count of free spaces and than call display() so that new value is displayed
     * @param freeSpace 
     */
    @Override
    public void update(int freeSpace){
        freeSpace = parkingSystem.getFreeSpaces();
        DisplayPanel.freeSpace = freeSpace;
        display();
    }

    /**
     * displays free space count
     */
    @Override
    public void display(){
        System.out.println(panelID + "Free space: " + freeSpace);
    }

    /**
     * displays message passed to it as an argument
     * @param message - string
     */
    @Override
    public void displayMessage(String message){
        System.out.println(message);
        
    }

    /**
     *
     * @return
     */
    public String getPanelID(){
        return panelID;
    }

    /**
     *
     * @param id
     */
    public void setPanelID(String id){
        panelID = id;
    }
    }

 

