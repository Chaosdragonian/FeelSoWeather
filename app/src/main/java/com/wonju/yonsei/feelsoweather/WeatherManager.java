package com.wonju.yonsei.feelsoweather;

import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherManager {
    /**
     * The API Key for the Open Weather Map API.  Replace this with your own key
     */
    private final static String API_KEY = "fcec05e3ded6d3bf2545ffcf34c1992a\n";

    /**
     * The Base URL of the Open Weather Map API.
     */
    private final static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    /**
     * The HTTP Client implementation to use
     */
    private OkHttpClient httpClient;

    /**
     * Instantiate a new object for handling the OpenWeatherMap API - instantiates a
     * default OkHttpClient()
     */
    public WeatherManager() {
        httpClient = new OkHttpClient();
    }

    /**
     * Version of the constructor that allows the specification of a HTTP Client.  It must
     * be an OkHttpClient implementation
     * @param client The HTTP Client to use.
     */
    public WeatherManager(OkHttpClient client) {
        httpClient = client;
    }

    /**
     * Call the OpenWeatherMap API using the specified query.  You should never call this
     * directly - use one of the convenience methods above.
     *
     * @param query the query to send to the OpenWeatherMap API
     * @return A WeatherResponse object
     * @throws WeatherException if the request could not be completed
     */
    private WeatherResponse getWeather(String query) throws WeatherException {
        try {
            URL url = new URL(BASE_URL + "?" + query + "&appid=" + API_KEY);
            Request request = new Request.Builder().url(url).build();
            Response response = httpClient.newCall(request).execute();
            String jsonResponse = response.body().string();
            return WeatherResponse.fromJson(jsonResponse);
        } catch (Exception ex) {
            throw new WeatherException("HTTP GET failed", ex);
        }

    }

}
