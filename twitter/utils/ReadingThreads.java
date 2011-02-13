package twitter.utils;

import models.Persistence;
import yql.utils.YQLExecutor;

public class ReadingThreads extends Thread {

	public ReadingThreads(){
		super();
//		start();
	}
	@Override
	public void run() {
		super.run();
		while(true){
			int result = RobotCalls.readObstacle();
			YQLExecutor.execute("insert into wordpress.post (title, description, blogurl, username, password) " +
					"values ('Action #"+Persistence.getTotalActions()+"', 'Obstacles on the way. Distance "+result
							+"', 'http://hackurobot.wordpress.com/', 'hackurobot', 'Robotet')", false);
		}
	}
}
