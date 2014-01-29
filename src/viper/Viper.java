/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viper;

import environment.ApplicationStarter;
import environment.Environment;
import image.ResourceTools;

/**
 *
 * @author kimberlygilson
 */
public class Viper {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        start();
        }

    private static void start() {
        ApplicationStarter.run("Viper Runner", new SnakeEnvironment());
        
    }
}
