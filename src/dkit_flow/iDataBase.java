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
public interface iDataBase {
    
    void connect();
    void disconnect();
    void registerSubscriber(Subscriber s);
    boolean isSubscriber(User u);
    void removeSubscriber(Subscriber s);
    double getBalance(User u);
    void updateBalance(User u);
    double updateBalance(Subscriber s, double charge);
    void insertTraffic(User u);
    void exitTraffic(User u);
    double calculateCharge(User u);
    
    
}
