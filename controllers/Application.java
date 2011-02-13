package controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import play.mvc.Controller;
import twitter.uses.TwitterService;
import twitter4j.TwitterException;
import yql.uses.Flickr;
import yql.uses.Twitter;
import yql.uses.Weather;
import yql.uses.Wordpress;
import yql.uses.dto.PostInfo;
import yql.uses.dto.WeatherInfo;

public class Application extends Controller {

	public static void index() throws TwitterException, IOException {
		TwitterService service = new TwitterService();
		service.start(0, null, new String[] { "#robot_forward", "#robot_backward",
								"#robot_right", "#robot_left", "#robot_beep", "#robot_weather" });
		render();
	}

	public static void wordpress() {
		List<PostInfo> posts = Wordpress.getPosts();
		renderArgs.put("datetime", new Date());
		renderArgs.put("posts", posts);
		render();
	}

	public static void flickr() {
		Object robotImgs = Flickr.getRandomRobotImages();
		renderArgs.put("images", robotImgs);
		render();
	}

	public static void twitmap() {
		Map<String, String> twitterLocations = Twitter.getRobotTwitters(20);
		renderArgs.put("twitterLocations", twitterLocations);
		render();
	}
}