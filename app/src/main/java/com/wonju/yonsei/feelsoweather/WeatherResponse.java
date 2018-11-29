package com.wonju.yonsei.feelsoweather;

import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("coord") private Coord coord;
    @SerializedName("weather") private ArrayList<Weather> weather;
    @SerializedName("base") private String base;
    @SerializedName("main") private Main main;
    @SerializedName("visibility") private int visibility;
    @SerializedName("wind") private Wind wind;
    @SerializedName("clouds") private Clouds clouds;
    @SerializedName("rain") private Precipitation rain;
    @SerializedName("snow") private Precipitation snow;
    @SerializedName("dt") private long unixTimestamp;
    @SerializedName("sys") private Sys sys;
    @SerializedName("id") private long city_id;
    @SerializedName("name") private String city_name;
    @SerializedName("cod") private int response_code;

    /**
     * Convert the current object to JSON string format
     * @return a JSON representation of the object
     */
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    /**
     * Creates a new WeatherResponse object from a JSON string
     * @param json the JSON string needed to deserialize
     * @return a WeatherResponse object
     */
    public static WeatherResponse fromJson(String json) {
        return new Gson().fromJson(json, WeatherResponse.class);
    }

    /**
     * The internal representation of the "clouds" portion of the API
     */
    public class Clouds {
        @SerializedName("all") private int cloudiness;
    }

    /**
     * The internal representation of the "coord" portion of the API
     */
    public class Coord {
        @SerializedName("lon") private double lon;
        @SerializedName("lat") private double lat;
    }

    /**
     * The internal representation of the "main" portion of the API
     */
    public class Main {
        @SerializedName("temp") private double temp;
        @SerializedName("pressure") private int pressure;
        @SerializedName("humidity") private int humidity;
        @SerializedName("temp_min") private double temp_min;
        @SerializedName("temp_max") private double temp_max;
        @SerializedName("sea_level") private int sea_level;
        @SerializedName("grnd_level") private int ground_level;
    }

    /**
     * The internal representation of the "rain" and "snow" portion of the API
     */
    public class Precipitation {
        @SerializedName("3h") private double volume;
    }

    /**
     * The internal representation of the "sys" portion of the API
     */
    public class Sys {
        @SerializedName("type") private int type;
        @SerializedName("id") private int id;
        @SerializedName("message") private double message;
        @SerializedName("country") private String country;
        @SerializedName("sunrise") private long sunrise;
        @SerializedName("sunset") private long sunset;
    }

    /**
     * The internal representation of the "weather" list portion of the API
     */
    public class Weather {
        @SerializedName("id") private int id;
        @SerializedName("main") private String main;
        @SerializedName("description") private String description;
        @SerializedName("icon") private String icon;
    }

    /**
     * The internal representation of the "wind" portion of the API
     */
    public class Wind {
        @SerializedName("speed") private double speed;
        @SerializedName("deg") private int direction;
    }
}