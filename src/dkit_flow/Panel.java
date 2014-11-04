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
/*
This object represents some for of visual display 
*/
public class Panel {
    
    private ParkingSystem dkit;        
    private int freeSpace;           // the number of the freeSpaces at the parking lot
    private int panelID = 0;         // the identification number of the display will be stored here
    
    public Panel(ParkingSystem dkit){
        this.dkit = dkit;
        panelID +=1;
    }   
    
    public void update(int freeSpace){         // 
        this.freeSpace = freeSpace;
        display();
    }
    public void display(){
        System.out.println(panelID + "Free space: " + freeSpace);
    }
    public void displayMessage(String message){
        System.out.println(message);
        
    }
    public int getPanelID(){
        return panelID;
    }
    
    }

