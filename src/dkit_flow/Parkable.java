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
    
    /**
     *
     * @param d
     */
        
    public void registerDisplayPanel(Displayable d);

    /**
     *
     * @param d
     */
    public void removeDisplayPanel(Displayable d);

    /**
     *
     * @param freeSpace
     */
    public void updateDisplayable(int freeSpace); 

    /**
     *
     * @param g
     */
    public void registerGate(Gate g);

    /**
     *
     * @param g
     */
    public void removeGate(Gate g);

    /**
     *
     * @param is
     */
    public void registerCamera(iSensor is);

    /**
     *
     * @param is
     */
    public void removeCamera(iSensor is);
}
