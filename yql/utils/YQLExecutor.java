package yql.utils;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

import com.google.gson.JsonElement;

import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.WS.WSRequest;
import play.libs.XML;

public class YQLExecutor {

	public static JsonElement execute(String query, Boolean isGet) {
		// Prepare parameters
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("q", query);
		params.put("format", "json");
		params.put("env", "store://datatables.org/alltableswithkeys");

		// Do request and return string
		WSRequest request = WS.url("http://query.yahooapis.com/v1/public/yql")
				.params(params);
		HttpResponse response = isGet ? request.get() : request.post();
		try {
			JsonElement json = response.getJson();
			return json;
		} catch (Exception e) {
			return null;
		}
	}

}
