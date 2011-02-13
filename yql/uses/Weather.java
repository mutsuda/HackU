package yql.uses;

import java.util.HashMap;
import java.util.Map;

import yql.uses.dto.WeatherInfo;
import yql.utils.JSONHelper;
import yql.utils.YQLExecutor;

import com.google.gson.JsonElement;

public class Weather {

	public static WeatherInfo getWeatherInfo() {
		// Get JSON
		JsonElement jsonRoot = YQLExecutor
				.execute(
						"SELECT * FROM weather.forecast WHERE location=\"SPXX0015\" and u=\"c\"",
						true);

		// Get Degrees and text
		String degrees = JSONHelper.navigate(jsonRoot,
				"query,results,channel,item,condition,temp").getAsString();
		String text = JSONHelper.navigate(jsonRoot,
				"query,results,channel,item,condition,text").getAsString();

		// Translate text
		jsonRoot = YQLExecutor.execute(
				"select * from google.translate where q=\"" + text
						+ "\" and target=\"ca\"", true);
		text = JSONHelper.navigate(jsonRoot, "query,results,translatedText")
				.getAsString();

		// Enveloper
		WeatherInfo weatherInfo = new WeatherInfo(degrees, text);

		return weatherInfo;
	}
}
