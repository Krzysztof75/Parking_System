/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author Kris
 */
public class CameraFactory {

    public CameraFactory() {
    }

    public static Camera createCamera(String camera, ParkingSystem ps) {
        Camera cameraType;
        switch (camera) {
            case "Entry":
                cameraType = new EntryCamera(ps);
                ParkingSystem.log.info("Creating " + camera + " camera");
                break;
            case "Exit":
                cameraType = new ExitCamera(ps);
                ParkingSystem.log.info("Creating " + camera + " camera");
                break;
            default:
                cameraType = null;
                System.out.println("***You need to pas either Entry or Exit as a parameter to the createCamera method*** ");
        }
        return cameraType;
    }

}
