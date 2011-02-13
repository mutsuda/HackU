package yql.uses;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import yql.uses.dto.RobotImg;
import yql.utils.JSONHelper;
import yql.utils.YQLExecutor;

public class Flickr {

	private static String[] words = { "humanoid ", "animal ", "small ", "big ",
			"", "yellow ", "blue ", "micro", "nano", "metallic ", "cute ",
			"funny " };

	public static List<RobotImg> getRandomRobotImages() {
		// Get random IMGs info
		List<RobotImg> robotImgs = new LinkedList<RobotImg>();
		int randomIndex = (new Random(System.currentTimeMillis())).nextInt(10);
		String word = words[randomIndex];
		String yql = "select * from flickr.photos.search where text=\"" + word
				+ "robot\"";
		JsonElement jsonRoot = YQLExecutor.execute(yql, true);

		// Build all URLS
		JsonArray jsonArray = JSONHelper.navigate(jsonRoot,
				"query,results,photo").getAsJsonArray();
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject jsonObj = jsonArray.get(i).getAsJsonObject();
			String id = jsonObj.get("id").getAsString();
			String secret = jsonObj.get("secret").getAsString();
			String server = jsonObj.get("server").getAsString();
			String farm = jsonObj.get("farm").getAsString();
			String url = "http://farm" + farm + ".static.flickr.com/" + server
					+ "/" + id + "_" + secret + "_s.jpg";
			robotImgs.add(new RobotImg(url));
		}

		// Return
		return robotImgs;
	}

}
