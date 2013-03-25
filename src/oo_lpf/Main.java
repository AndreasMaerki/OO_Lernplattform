package oo_lpf;

import data.FileHandler;
import logic.Distributor;

/**
 *
 * @author Andreas Maerki
 */
public class Main {

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {

        //Offizieller Applikationsstart
        new Distributor(new FileHandler());
    }
}
