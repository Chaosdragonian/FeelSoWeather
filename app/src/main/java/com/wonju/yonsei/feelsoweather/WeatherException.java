package com.wonju.yonsei.feelsoweather;

public class WeatherException extends Exception {
    public WeatherException(String message, Exception inner) {
        super(message, inner);
    }
}
