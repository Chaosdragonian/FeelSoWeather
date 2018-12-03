package com.wonju.yonsei.feelsoweather;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class SplashActivity extends AppCompatActivity {

    private final String strServiceKey = "ZvrUlVNZt1t9GZ3BLraxhM%2BiCEQ65GnsyVV7miah3opqVvy8G1AuOTV8uHfsxMCz6kSblWG15RI%2FdjhiNnfw7g%3D%3D";
    final int PARSE_STATE_NOT_FOUND = 0;
    final int PARSE_STATE_FOUND = 1;
    final int PARSE_STATE_DONE = 2;
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    XmlPullParser xpp= null;
    XmlPullParserFactory factory= null;

    public static String CityString;
    public static String nowTemperatureString;
    public static String todayTempString;
    public static String nowTempString;
    public static String todayDateString;
    public static String cloudPercentString;
    public static String rainPercentString;
    public static String dust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        OpenAPIThreadTask apiThreadTask = new OpenAPIThreadTask();
        try {
            StringBuilder urlBuilder = new StringBuilder("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty");
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + strServiceKey);
            urlBuilder.append("&" + URLEncoder.encode("stationName", "UTF-8") + "=" + URLEncoder.encode("명륜동", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("dataTerm", "UTF-8") + "=" + URLEncoder.encode("DAILY", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
            apiThreadTask.execute(urlBuilder.toString(), null, null);

        } catch (Exception e) {
            Log.e("linsoo", "queryGetAirDatafromStationName=" + e.getMessage());
        }


        GetWeatherAsyncTask task = new GetWeatherAsyncTask();
        task.execute(new String[]{"Wonju,KR"});

        if (apiThreadTask.getStatus() != AsyncTask.Status.FINISHED && task.getStatus() != AsyncTask.Status.FINISHED) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /* 메뉴액티비티를 실행하고 로딩화면을 죽인다.*/
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
    }


    public class GetWeatherAsyncTask extends AsyncTask<String,Void,WeatherResponse> {
        @Override
        protected WeatherResponse doInBackground(String... cities) {
            try {
                WeatherManager manager = new WeatherManager();
                return manager.getWeatherByCityName(cities[0]);
            } catch (WeatherException ex) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(WeatherResponse response) {


            if (response != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                CityString = (response.getCityName() + ", " + response.getSysInfo().getCountry());

                //c_details.setText(response.getWeatherConditions()[0].getLongDescription());
                double temp = response.getMain().getTemperature().getCurrentValue();
                double maxtemp = response.getMain().getTemperature().getMaximumValue();
                double mintemp = response.getMain().getTemperature().getMinimumValue();
                int cloudtemp = response.getClouds().getCloudiness();
                //double rainTemp = response.getRain().getVolume();

                nowTemperatureString = (String.format("%.2f°C", temp-273.15));
                todayDateString = (formatter.format(response.getTimestamp()));
                todayTempString = (String.format("%.2f°C / %.2f°C",maxtemp-273.15,mintemp-273.15));

                cloudPercentString = (String.format("구름량 : %d",cloudtemp));
                // rainPercentString = (String.format("강수량 : %f ",rainTemp));
                todayDateString = (formatter.format(response.getTimestamp()));

            }

        }
    }
    private class OpenAPIThreadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urlString) {
            try{
                URL url = new URL(urlString[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/json");
                System.out.println("Response code: " + conn.getResponseCode());
                BufferedReader rd;
                if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();
                conn.disconnect();
               // dust = sb.toString();



                return sb.toString();

            }catch (Exception e){

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                factory = XmlPullParserFactory.newInstance();
                xpp = factory.newPullParser();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            try {
                String tmpTag;
                int dataTime = PARSE_STATE_NOT_FOUND;
                int pm25 = PARSE_STATE_NOT_FOUND;
                int pm25_24= PARSE_STATE_NOT_FOUND;
                int pm10 = PARSE_STATE_NOT_FOUND;
                int pm10_24 = PARSE_STATE_NOT_FOUND;

                xpp.setInput(new StringReader(result));
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType){
                        case XmlPullParser.START_TAG:
                            tmpTag = xpp.getName();

                            if (pm10 == PARSE_STATE_NOT_FOUND && tmpTag.equals("pm10Value"))
                                pm10 = PARSE_STATE_FOUND;

                            break;
                        case XmlPullParser.TEXT:

                             if(pm10 == PARSE_STATE_FOUND){
                                pm10 = PARSE_STATE_DONE;
                                dust = xpp.getText();
                                break;

                             }

                            break;
                    }
                    eventType = xpp.next();
                }
            } catch (Exception e) {  e.printStackTrace();   }
            }
        }
}
