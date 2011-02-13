package Robot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sun.mail.iap.ByteArray;

import lejos.pc.comm.NXTConnector;

/**
 * This is a PC sample. It connects to the NXT, and then
 * sends an integer and waits for a reply, 100 times.
 * 
 * Compile this program with javac (not nxjc), and run it 
 * with java.
 * 
 * You need pccomm.jar and bluecove.jar on the CLASSPATH. 
 * On Linux, you will also need bluecove-gpl.jar on the CLASSPATH.
 * 
 * Run the program by:
 * 
 *   java BTSend 
 * 
 * Your NXT should be running a sample such as BTReceive or
 * SignalTest. Run the NXT program first until it is
 * waiting for a connection, and then run the PC program. 
 * 
 * @author Lawrie Griffiths
 *
 */
public class BTSend {

	private static NXTConnector conn;
	private static DataOutputStream dos;
	private static DataInputStream dis;
	private static boolean connected;

	public static void connect(){

		conn = new NXTConnector();

		// Connect to any NXT over Bluetooth
		connected = conn.connectTo("btspp://");

		if (!connected) {
			System.err.println("Failed to connect to any NXT");
			System.exit(1);
		}		

		dos = conn.getDataOut();
		dis = conn.getDataIn();
	}

	public static void forward()
	{
		if(!connected) connect();
		try {
			System.out.println("Sending " + (1));
			dos.writeInt(1);
			dos.flush();				
		} catch (IOException ioe) {
			System.out.println("IO Exception writing bytes:");
			System.out.println(ioe.getMessage());
		}		
	}

	public static void backward()
	{
		if(!connected) connect();
		try {
			System.out.println("Sending " + (2));
			dos.writeInt(2);
			dos.flush();				
		} catch (IOException ioe) {
			System.out.println("IO Exception writing bytes:");
			System.out.println(ioe.getMessage());
		}		
	}

	public static void left()
	{
		if(!connected) connect();
		try {
			System.out.println("Sending " + (3));
			dos.writeInt(3);
			dos.flush();				
		} catch (IOException ioe) {
			System.out.println("IO Exception writing bytes:");
			System.out.println(ioe.getMessage());
		}		
	}

	public static void right()
	{
		if(!connected) connect();
		try {
			System.out.println("Sending " + (4));
			dos.writeInt(4);
			dos.flush();				
		} catch (IOException ioe) {
			System.out.println("IO Exception writing bytes:");
			System.out.println(ioe.getMessage());
		}		
	}

	public static int read()
	{
		if(!connected) connect();
		int y=-1;
		try
		{
			y= dis.readInt();
		}
		catch (IOException e) {
			System.out.println("IOException closing connection:");
		}
		
		return y;
	}
	
	public static void beep()
	{
		if(!connected) connect();
		try {
			System.out.println("Sending " + (5));
			dos.writeInt(5);
			dos.flush();				
		} catch (IOException ioe) {
			System.out.println("IO Exception writing bytes:");
			System.out.println(ioe.getMessage());
		}
	}
	
	public static void printWeather(int degrees,String wt){
		if(!connected) connect();
		try {
			System.out.println("Sending " + (6));
			dos.writeInt(6);
			dos.writeInt(degrees);			
			for(int i=0; i< wt.length(); i++)
			{
				dos.writeChar(wt.charAt(i));
			}
			dos.writeChar('$');
			dos.flush();				
		} catch (IOException ioe) {
			System.out.println("IO Exception writing bytes:");
			System.out.println(ioe.getMessage());
		}
		
	}

	public static void close ()
	{
		if(connected){
			try {
				dis.close();
				dos.close();
				conn.close();
			} catch (IOException ioe) {
				System.out.println("IOException closing connection:");
				System.out.println(ioe.getMessage());
			}
		}
	}
	
	


}