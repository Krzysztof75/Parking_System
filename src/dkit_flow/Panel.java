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
public class Panel {
    
    private ParkingSystem dkit;
    private int freeSpace;
    private String panelName;
    
    public Panel(ParkingSystem dkit){
        this.dkit = dkit;
    }   
    
    public void update(int freeSpace){
        this.freeSpace = freeSpace;
        display();
    }
    public void display(){
        System.out.println("Free space: " + freeSpace);
    }
    public void displayMessage(String message){
        System.out.println(message);
        
    }
    public String getPanelName(){
        return panelName;
    }
    public void setPanelName(String panelName){
        this.panelName = panelName;
    }
}
