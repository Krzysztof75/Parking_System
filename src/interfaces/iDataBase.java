/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import classes.Subscriber;
import classes.User;

/**
 *
 * @author Kris
 */
public interface iDataBase {

    /**
     *
     */
    void connect();

    /**
     *
     */
    void disconnect();

    /**
     *
     * @param s
     */
    void registerSubscriber(Subscriber s);

    /**
     *
     * @param u
     * @return
     */
    boolean isSubscriber(User u);

    /**
     *
     * @param s
     */
    void removeSubscriber(Subscriber s);

    /**
     *
     * @param u
     * @return
     */
    double getBalance(User u);

    /**
     *
     * @param u
     */
    void updateBalance(User u);

    /**
     *
     * @param s
     * @param charge
     * @return
     */
    double updateBalance(Subscriber s, double charge);

    void emptyTrafficRecords();
    
    void emptyAllTrafficRecords();
    
    /**
     *
     * @param u
     */
    void insertTraffic(User u);

    /**
     *
     * @param u
     */
    void exitTraffic(User u);

    /**
     *
     * @param u
     * @return
     */
    double calculateCharge(User u);

}
