package twitter.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class StatusListenerImpl implements StatusListener{

	private final static Log log = LogFactory.getLog(StatusListenerImpl.class);
	
	public void onStatus(Status status) {
		String[] tuit = (status.getText().split("#"));
		for(String hashtag: tuit){
			if( hashtag.contains("forward") ){
				RobotCalls.forward(10); //going ahead during 10s
			}else if( hashtag.contains("backward") ){
				RobotCalls.backward(10); //backwards during 10s
			}else if( hashtag.contains("right") ){
				RobotCalls.right(90); //turning 90ª on the right
			}else if( hashtag.contains("left") ){
				RobotCalls.left(90); //turning 90ª on the right
			}else if( hashtag.contains("beep") ){
				RobotCalls.beep(); //turning 90ª on the right
			}else if( hashtag.contains("weather") ){
				RobotCalls.printWeather(); //turning 90ª on the right
			}else{
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!Cagada!!!!!!!!!!!!!!!!!");
			}
		}
		
		RobotCalls.publishResume();
		
        System.out.println(status.getUser().getName() + " : " + status.getText());
        log.debug(status.getUser().getName() + " : " + status.getText());
    }
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
    public void onException(Exception ex) {
        ex.printStackTrace();
    }
}
