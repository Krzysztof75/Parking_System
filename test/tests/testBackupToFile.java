package tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import tests.regGenerator;
import classes.ParkingSystem;
import classes.User;
import classes.parkingDB;
import static classes.parkingDB.NumberOfRecords;
import static classes.parkingDB.getTraffic;
import static classes.parkingDB.readTrafficDB;
import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kris
 */
public class testBackupToFile {
    
    static ParkingSystem parkingSystem;
    User user;
    String registration;
    
    public testBackupToFile() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        parkingSystem = ParkingSystem.getInstance();
        parkingDB.getTraffic().clear();
        parkingDB.getSubscribers().clear();
        parkingSystem.getDataBase().emptyAllTrafficRecords();
        
    }
    
    @AfterClass
    public static void tearDownClass() {
        parkingSystem.getDataBase().disconnect();
    }
    
    @Before
    public void setUp() {
        
        
    }
    
    @After
    public void tearDown() {
    }

     @Test
     public void test1D() {
     // generate 999 unique users
           for(int i = 0; i < 999; i++){
               try{
               registration = "10LK" + regGenerator.registration();
               }catch(Exception e){
                   System.out.println(e.getMessage());
               }
               // and enter them into the traffic table with registration number and DateIN
               user = new User(registration,"2015:03:13 13:53:52");
            parkingSystem.getDataBase().insertTraffic(user);
        }
           // than reset regGenerator counter
           regGenerator.counter = 0;
           // end use it again so we have matching registration numbers for exit event
           // update 989 records with DateOut, balance and hasPaid
           for(int i = 0; i < 989; i++){
               try{
               registration = "10LK" + regGenerator.registration();
               }catch(Exception e){
                   System.out.println(e.getMessage());
               }
                user = new User(registration,"2015:03:13 13:53:52","2015:03:13 14:53:52",0.6,1);
          
               parkingSystem.getDataBase().exitTraffic(user);
           }
           // assert that there is 999 total records in traffic table
           Assert.assertEquals(999, NumberOfRecords());
     }
     
     @Test
     public void test2D(){
         // By now there is 999 records in the traffic table including 10 records with DateOut null
         // this test enters and updates 2 more records
         String reg1="",reg2="";
         User user1, user2;
         regGenerator.counter = 0;
        
         try{
         reg1 = "12KL" + regGenerator.registration();
         reg2 = "12KL" + regGenerator.registration();
     } catch(Exception e){
         System.out.println(e.getMessage());
     }
         user1 = new User(reg1,"2015:03:13 13:53:52");
         parkingSystem.getDataBase().insertTraffic(user1);
         user1 = new User(reg1,"2015:03:13 13:53:52","2015:03:13 14:53:52",0.6,1);
         parkingSystem.getDataBase().exitTraffic(user1);
         user2 = new User(reg2,"2015:03:13 13:53:52");
         parkingSystem.getDataBase().insertTraffic(user2);
         user2 = new User(reg2,"2015:03:13 13:53:52","2015:03:13 14:53:52",0.6,1);
         parkingSystem.getDataBase().exitTraffic(user2);

         // assert correct number of records left in the data base
         Assert.assertEquals(11, NumberOfRecords());
         
         // count how many records have empty and not empty DateOut values
         int usersWithEmptyDataOutValue = 0;
         int usersWithNotEmptyDataOutValue = 0;
         ArrayList<User> trafficTableContent = readTrafficDB();
         for(User usr : trafficTableContent){
             if(usr.getTimeOut() == null){
                usersWithEmptyDataOutValue++; 
             } else
                 usersWithNotEmptyDataOutValue++;  
         }
         
         // assert correct number of users with empty DateOut value
         Assert.assertEquals(10, usersWithEmptyDataOutValue);
         // assert correct number of users with not empty DateOut value
         Assert.assertEquals(1, usersWithNotEmptyDataOutValue);
}
}