package twitter.uses;

import java.io.IOException;

import twitter.utils.StatusListenerImpl;
import twitter4j.FilterQuery;
import twitter4j.StatusAdapter;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TwitterService extends StatusAdapter {
	public static void start(int count, int[] followers, String[] trackArray) throws TwitterException, IOException{
		
		StatusListener listener = new StatusListenerImpl();
	    TwitterStream twitterStream = new TwitterStreamFactory(listener).getInstance();
	    // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
	    twitterStream.filter(new FilterQuery(0, followers, trackArray));
//	    Thread t = new ReadingThreads();
//	    t.start();
	}
}
