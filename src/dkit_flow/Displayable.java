/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkit_flow;

/**
 *
 * @author Kris
 * this interface lets us group all "displayable" objects together so we can update the message (freeSpace) 
 * on all panels registered with parkingSystem object at the same time
 */
public interface Displayable {
 
    /**
     *
     */
    public void display(); 

    /**
     *
     * @param message
     */
    public void displayMessage(String message);

    /**
     *
     * @param freeSpace
     */
    public void update(int freeSpace);
}
