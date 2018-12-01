package com.wonju.yonsei.feelsoweather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    Location lastLocation;
    double lon, lat;
    TabLayout sliding_tab;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }
        sliding_tab = (TabLayout) findViewById(R.id.sliding_tabs);
        pager = (ViewPager) findViewById(R.id.viewPager);

        pager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(3); // 안보이는 페이지 로딩해 놓을 갯수
        sliding_tab.addTab(sliding_tab.newTab().setText("미세먼지"), 0, true); // 페이지 등록
        sliding_tab.addTab(sliding_tab.newTab().setText("날씨"), 1);
        sliding_tab.addTab(sliding_tab.newTab().setText("관련정보"), 2);


        sliding_tab.addOnTabSelectedListener(pagerListener);

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(sliding_tab));
        lastLocation = firstSetLocation();
        lon = lastLocation.getLongitude();
        lat = lastLocation.getLatitude();
        WeatherResponse wResponse = new WeatherResponse();
        WeatherManager wManager = new WeatherManager();





    }
    private Location firstSetLocation() {

        Location returnLocation = null;

        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            returnLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        locationManager.removeUpdates(locationListener);
        return returnLocation;
    }
    TabLayout.OnTabSelectedListener pagerListener = new TabLayout.OnTabSelectedListener() {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            pager.setCurrentItem(tab.getPosition());
            // 슬라이딩이 아니라 위에 페이지를 선택했을 때도 페이지 이동 가능하게.
        }

        @Override

        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override

        public void onTabReselected(TabLayout.Tab tab) {
        }

    };
}



