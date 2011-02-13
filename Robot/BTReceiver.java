package Robot;

import lejos.nxt.*;
import lejos.nxt.comm.*;
import lejos.robotics.navigation.Pilot;
import lejos.robotics.navigation.TachoPilot;


import java.io.*;

/**
 * Receive data from another NXT, a PC, a phone, 
 * or another bluetooth device.
 * 
 * Waits for a connection, receives an int and returns
 * its negative as a reply, 100 times, and then closes
 * the connection, and waits for a new one.
 * 
 * @author Lawrie Griffiths
 *
 */
public class BTReceiver {

	public static void main(String [] args)  throws Exception 
	{
		String connected = "Connected";
		String waiting = "Waiting...";
		String closing = "Closing...";

		while (true)
		{
			LCD.drawString(waiting,0,0);
			LCD.refresh();

			BTConnection btc = Bluetooth.waitForConnection();

			LCD.clear();
			LCD.drawString(connected,0,0);
			LCD.refresh();	

			DataInputStream dis = btc.openDataInputStream();
			DataOutputStream dos = btc.openDataOutputStream();


			boolean exit = false;

			while( !exit ) {
				int n = dis.readInt();
				
			
				switch (n) {
				case 1:
					goForward(0);
					break;
				case 2:
					goBackWard(0);
					break;

				case 3:
					TurnLeft(0);
					break;

				case 4:
					TurnRight(0);
					break;

				case 5:
					Beep();	
					break;
					
				case 6:
					int degrees = dis.readInt();
					String wt = dis.readUTF();
					LCD.clear();
					LCD.drawString("Temperatura: "+ degrees,0,0);
					LCD.drawString("Temps: "+ wt,1,0);
					LCD.refresh();
					break;
				case 7:
					exit=true;
					break;

				default:
					break;
				}
				
			}

			dis.close();
			dos.close();
			Thread.sleep(100); // wait for data to drain
			LCD.clear();
			LCD.drawString(closing,0,0);
			LCD.refresh();
			btc.close();
			LCD.clear();
		}
	}


	public static void goForward(int tacho) throws InterruptedException{
		Motor.C.resetTachoCount();
		Motor.A.forward();
		Motor.C.forward();
		int count = 0;
		while( count < 500 )count = Motor.C.getTachoCount();
		Motor.A.stop();
		Motor.C.stop();
		LCD.clear();
		LCD.drawString("FORWARD", 0, 0);
		LCD.refresh();
	}

	public static void goBackWard(int tacho) throws InterruptedException{
		Motor.C.resetTachoCount();
		Motor.A.backward();
		Motor.C.backward();
		int count = 0;
		while( count > -500 )count = Motor.C.getTachoCount();
		Motor.A.stop();
		Motor.C.stop();
		LCD.clear();
		LCD.drawString("BACKWARD", 0, 0);
		LCD.refresh();		
	}

	public static void TurnLeft(int degrees) {
		Motor.A.stop();
		Motor.C.stop();
		Pilot pilot = new TachoPilot(4.4f, 4.4f, Motor.A, Motor.C, true);
		pilot.rotate(180);
	}

	public static void TurnRight(int degrees){
		Motor.A.stop();
		Motor.C.stop();
		Pilot pilot = new TachoPilot(4.4f, 4.4f, Motor.A, Motor.C, true);
		pilot.rotate(-180);
	}

	public static void Beep()
	{
		//Sound.systemSound(true, 3);
		final short [] note = {2100,100, 1870,100, 1400,100, 1870,100,2100,100};

		for(int i=0;i <note.length; i+=2) {
			short w = note[i+1];
			int n = note[i];
			if (n != 0) Sound.playTone(n, w*10);
			try{
				Thread.sleep(w*10);
			}
			catch (Exception e) {
				// TODO: handle exception
				System.out.println("interrupt_Exception"); 
			}
		}
	}
		
	public static int CheckSensor(){
		
		UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S1);
		int distance = sonic.getDistance();
		if (distance <= 100) return distance;
		else return -1;
	}

}
