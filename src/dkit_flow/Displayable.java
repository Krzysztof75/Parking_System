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
public interface Displayable {
   public void display(); 
   public void displayMessage(String message);
   public void update(int freeSpace);
}
