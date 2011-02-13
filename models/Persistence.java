package models;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Persistence {

	private final static Log log = LogFactory.getLog(Persistence.class);
	
	private static long forward = 0;
	private static long backward = 0;
	private static long right = 0;
	private static long left = 0;
	private static long posts = 0;
	private static long beep = 0;
	private static long weather = 0;
//	private static String supahDB = "./supahDB.db";
	
	public static void incrementForward() {
		Persistence.forward ++;
	}

	public static void incrementBackward() {
		Persistence.backward++;
	}

	public static void incrementRight() {
		Persistence.right++;
	}

	public static void incrementLeft() {
		Persistence.left++;
	}

	public static void incrementBeeps() {
		Persistence.beep++;
	}
	
	public static void incrementWeather() {
		Persistence.weather++;
	}

	public static void incrementPosts() {
		Persistence.posts++;
	}
	
	public static long getTotalActions(){
		return forward+backward+right+left;
	}

	public static long getPosts() {
		return posts;
	}

	public static void setPosts(long posts) {
		Persistence.posts = posts;
	}

	public static long getForward() {
		return forward;
	}

	public static long getBackward() {
		return backward;
	}

	public static long getRight() {
		return right;
	}

	public static long getLeft() {
		return left;
	}
}
