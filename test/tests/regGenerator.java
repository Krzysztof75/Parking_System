package tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Kris
 */
public class regGenerator {

    static int counter = 0;

    public static String registration() throws Exception {
        String reg = "";

        if (counter < 10) {
            reg = "000" + counter;
        } else if (counter < 100 && counter > 9) {
            reg = "00" + counter;
        } else if (counter < 1000 && counter > 99) {
            reg = "0" + counter;
        } else if (counter < 10000 && counter > 999) {
            reg = String.valueOf(counter);
        } else {
            throw new Exception(" you can't create more than 9999 reg with this regGenerator");
        }

        counter++;
        return reg;
    }
}
