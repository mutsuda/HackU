package yql.utils;

import com.google.gson.JsonElement;

public class JSONHelper {

	public static JsonElement navigate(JsonElement jsonElem, String hierarchyList) {
		// Split into array
		String[] hierarchy = hierarchyList.split(",");

		// Navigate and return
		for (int i = 0; i < hierarchy.length; i++) {
			//if(jsonElem != null && jsonElem.getAsJsonObject() != null && jsonElem.getAsJsonObject().get(hierarchy[i]) != null)
				jsonElem = jsonElem.getAsJsonObject().get(hierarchy[i]);
		}

		// Return
		return jsonElem;
	}
}
