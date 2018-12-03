package com.wonju.yonsei.feelsoweather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class Activity2 extends AppCompatActivity {
    TextView City;
    TextView nowTemperature;
    TextView todayTemp;
    TextView todayDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        City = (TextView)findViewById(R.id.city);
        nowTemperature = (TextView)findViewById(R.id.noww);
        todayDate = (TextView) findViewById(R.id.tod);
        todayTemp = (TextView) findViewById(R.id.today);
        GetWeatherAsyncTask task = new GetWeatherAsyncTask();
        task.execute(new String[] { "Wonju,KR" });

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
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                Toast.makeText(Activity2.this,response.getCityName() + ", " + response.getSysInfo().getCountry() , Toast.LENGTH_LONG).show();
                //city.setText(response.getCityName() + ", " + response.getSysInfo().getCountry());

                City.setText(response.getCityName());
                //c_details.setText(response.getWeatherConditions()[0].getLongDescription());
                double temp = response.getMain().getTemperature().getCurrentValue();
                todayTemp.setText(String.format("%.2f°C", temp-273.15));
                todayDate.setText(formatter.format(response.getTimestamp()));

            }

        }
    }
}
