package twitter.utils;

import models.Persistence;
import yql.uses.Weather;
import yql.uses.dto.WeatherInfo;
import yql.utils.YQLExecutor;
import Robot.BTSend;

public class RobotCalls {

	public static void right(long degrees){
		System.out.println("[RIGHT] ----- I'm turning myself to the RIGHT");
		BTSend.right();
		Persistence.incrementRight();
	}

	public static void left(long degrees){
		System.out.println("[LEFT] ----- I'm turning myself to the LEFT");
		BTSend.left();
		Persistence.incrementLeft();
	}

	public static void forward(long time){
		System.out.println("[FORWARD] ----- I'm going FORWARD");
		BTSend.forward();
		Persistence.incrementForward();
	}

	public static void backward(long time){
		System.out.println("[BACKWARD] ----- I'm going BACKWARD");
		BTSend.backward();
		Persistence.incrementBackward();
	}
	
	public static int readObstacle(){
		System.out.println("[BEFORE-READ] ----- We are waintin' to read");
		int result = BTSend.read();
		YQLExecutor.execute("insert into wordpress.post (title, description, blogurl, username, password) " +
				"values ('Action #"+Persistence.getTotalActions()+"', 'Obstacles on the way. Distance "+result
						+"', 'http://hackurobot.wordpress.com/', 'hackurobot', 'Robotet')", false);
		System.out.println("[READ] ----- We've just read #"+result);
		Persistence.incrementPosts();
		return result;
	}
	
	public static void printWeather(){
		System.out.println("[WEATHER] ----- uHMm... is the wheather nice? ");
		WeatherInfo w = Weather.getWeatherInfo();
		System.out.println("W: "+ w.degrees + "text: "+ w.text);
		BTSend.printWeather(Integer.parseInt(w.degrees), w.text);
		Persistence.incrementWeather();
	}

	public static void beep(){
		System.out.println("[BEEP] ----- BEEP BEEP I'm heeere! ");
		BTSend.beep();
		Persistence.incrementBeeps();
	}
	
	public static void publishResume(){
		System.out.println("ACCIONES:" + Persistence.getTotalActions());
		if((Persistence.getTotalActions()%5) == 0){
			YQLExecutor.execute("insert into wordpress.post (title, description, blogurl, username, password) " +
					"values ('Action #"+Persistence.getTotalActions()+"', '"+resume()
							+"', 'http://hackurobot.wordpress.com/', 'hackurobot', 'Robotet')", false);
		}
	}
	
	public static String resume(){
		return "Actions done: " +
				"Left - "+Persistence.getLeft()+"\n" +
				"Right - "+Persistence.getRight()+"\n" +
				"Forward - "+Persistence.getForward()+"\n" +
						"Backwards - "+Persistence.getBackward()+"\n";
	}
	
	
}
