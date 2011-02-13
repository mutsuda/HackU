package yql.uses;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import yql.uses.dto.PostInfo;
import yql.utils.JSONHelper;
import yql.utils.YQLExecutor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class Wordpress {

	private static String blogUrl = "http://hackurobot.wordpress.com/";
	private static String username = "hackurobot";
	private static String password = "Robotet";

	public static String newPost(String title, String description) {
		// Execute insertion
		String yql = "insert into wordpress.post(title, description, blogurl, username, password) value (\""
				+ title
				+ "\", \""
				+ description
				+ "\", \""
				+ blogUrl
				+ "\", \"" + username + "\", \"" + password + "\")";
		YQLExecutor.execute(yql, false);

		// Return
		return yql;
	}

	public static List<PostInfo> getPosts() {
		// Init
		List<PostInfo> postList = new LinkedList<PostInfo>();

		// Get JSON
		JsonElement jsonRoot = YQLExecutor
				.execute(
						"select * from html where url=\"http://hackurobot.wordpress.com/\" and xpath='//div[@id=\"content\"]'",
						true);

		// Get to post array
		JsonElement postElem = JSONHelper.navigate(jsonRoot,
				"query,results,div,div");
		if (postElem.isJsonArray()) {
			JsonArray postArray = (JsonArray) postElem;
			for (int i = 0; i < postArray.size(); i++) {
				JsonElement elem = postArray.get(i);

				if (elem.getAsJsonObject().get("id").getAsString()
						.startsWith("post-")) {
					// Get title
					String title = JSONHelper.navigate(elem, "h2,a,content")
							.getAsString();

					// Get post
					JsonElement partialElem = JSONHelper.navigate(elem, "div")
							.getAsJsonArray().get(1);
					JsonElement partialElem2 = JSONHelper.navigate(partialElem,
							"p");
					String post = null;
					if (!partialElem2.isJsonPrimitive())
						partialElem2 = JSONHelper.navigate(partialElem2,
								"content");
					post = partialElem2.getAsString();

					// Create post info
					PostInfo postItem = new PostInfo(title, post);
					postList.add(postItem);
				}
			}
		}

		return postList;
	}

}
