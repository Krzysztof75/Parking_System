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
public interface Parkable {
    
    // this interface sets methods for registering/removing elements
    
    public void registerDisplayPanel(Displayable d);
    public void removeDisplayPanel(Displayable d);
    public void updateDisplayable(int freeSpace); 
    public void registerGate(Gate g);
    public void removeGate(Gate g);
    public void registerCamera(iSensor is);
    public void removeCamera(iSensor is);
}
