package comp346A2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kerly Titus
 */
public class Driver {

    /** 
     * main class
     * @param args the command line arguments
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws FileNotFoundException {
    	
    	//PrintStream output = new PrintStream("output_synchronized_busywaiting.txt");
    	//PrintStream output = new PrintStream("output_inconsistent.txt");
    	PrintStream output = new PrintStream("output_synchronized_3servers.txt");
        System.setOut(output);
    	Network objNetwork = new Network( );            /* Activate the network */
        objNetwork.start();

        Client objClient1 = new Client("sending");          /* Start the sending client thread */
        objClient1.start();
        Client objClient2 = new Client("receiving");        /* Start the receiving client thread */
        objClient2.start();
        
        Server s1 = new Server("1");
        s1.start();
        Server s2 = new Server("2");
        s2.start();
        Server s3 = new Server("3");
        s3.start();
      /*..............................................................................................................................................................*/
        
    }
    
 }
