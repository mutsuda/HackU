package yql.uses;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import yql.utils.JSONHelper;
import yql.utils.YQLExecutor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Twitter {

	public static Map<String, String> getRobotTwitters(int limit) {
		// Get robot twits
		String yql = "select * from twitter.search where q='#robot_forward' or q='#robot_backward' or q='#robot_left' or q='#robot_right' or q='#robot_beep'";
		JsonElement jsonRoot = YQLExecutor.execute(yql, true);

		// Iterate and create a user set
		Set<String> users = new HashSet<String>();
		JsonArray jsonArray = JSONHelper.navigate(jsonRoot,
				"query,results,results").getAsJsonArray();
		for (int i = 0; i < jsonArray.size(); i++) {
			String user = JSONHelper.navigate(jsonArray.get(i), "from_user")
					.getAsString();
			if (!users.contains(user)) {
				users.add(user);
				if (users.size() == limit) {
					break;
				}
			}
		}

		// From each user, get position
		Map<String, String> twitterLocations = new HashMap<String, String>();
		for (String twitterUser : users) {
			// Get user info
			yql = "select * from twitter.users where id=\"" + twitterUser
					+ "\"";
			jsonRoot = YQLExecutor.execute(yql, true);
			JsonObject jsonObj = JSONHelper.navigate(jsonRoot,
					"query,results,user").getAsJsonObject();
			String name = jsonObj.get("name").getAsString();
			String userInfo = "<b>" + twitterUser + "</b> " + name + "<br/>";
			try {
				// Existing location
				String location = jsonObj.get("location").getAsString();

				try {
					// Get geocode
					yql = "select * from yahoo.maps.geocode WHERE appid=\"9GKZkzfV34EoQ9xSG9.zOQM4e94_YGcwGGM.lbvx_chsJhvZq32hrY..ryUwLTOQPR1xmpffW1hNzR79v5_KYRFAKYWd1U4-\" and location=\""
							+ location + "\"";
					jsonRoot = YQLExecutor.execute(yql, true);
					jsonObj = JSONHelper.navigate(jsonRoot,
							"query,results,Result").getAsJsonObject();
					String latitude = jsonObj.get("Latitude").getAsString();
					String longitude = jsonObj.get("Longitude").getAsString();

					// Update data structure and save
					String geoID = latitude + "," + longitude;
					String twitterLocation = twitterLocations.get(geoID);
					if (twitterLocation == null) {
						twitterLocation = "";
					}
					twitterLocation += userInfo;
					twitterLocations.put(geoID, twitterLocation);
				} catch (IllegalStateException e) {
				}
			} catch (Exception e) {
			}
		}

		return twitterLocations;
	}

}
