package com.wonju.yonsei.feelsoweather;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;


public class MainActivity extends AppCompatActivity {
    Location lastLocation;
    double lon, lat;
    TabLayout sliding_tab;
    ViewPager pager;

    TextView City;
    TextView nowTemperature;
    TextView todayTemp;
    TextView nowTemp;
    TextView todayDate;
    TextView cloudPercent;
    TextView rainPercent;

    TextView howmuchBad;
    TextView dustDate;
    TextView dustBad;
    ImageView mark;



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

        pager.setAdapter(new FragmentAdapter(getApplicationContext()));
        pager.setOffscreenPageLimit(3); // 안보이는 페이지 로딩해 놓을 갯수
        sliding_tab.addTab(sliding_tab.newTab().setText("미세먼지"), 0, true); // 페이지 등록
        sliding_tab.addTab(sliding_tab.newTab().setText("날씨"), 1);
        sliding_tab.addTab(sliding_tab.newTab().setText("관련정보"), 2);


        sliding_tab.addOnTabSelectedListener(pagerListener);

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(sliding_tab));

        lastLocation = firstSetLocation();
        lon = lastLocation.getLongitude();
        lat = lastLocation.getLatitude();




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
    public class FragmentAdapter extends PagerAdapter {

        private LayoutInflater mInflater;
        public View.OnClickListener mButtonClick = new View.OnClickListener(){

            public void onClick(View v){
                switch(v.getId())
                {

                    case R.id.button1:
                        View dialogView1 = (View) View.inflate(MainActivity.this, R.layout.dialog, null);
                        AlertDialog.Builder dlg1 = new AlertDialog.Builder(MainActivity.this);
                        TextView tv1 = (TextView)dialogView1.findViewById(R.id.textView1);
                        dlg1.setTitle("미세먼지란?");
                        tv1.setText("먼지란 대기 중에 떠다니거나 흩날려 내려오는 입자상 물질을 말하는데, 석탄 · 석유 등의 화석연료를 태울 때나 공장 · 자동차 등의 배출가스에서 많이 발생한다.\n" +
                                "\n" +
                                "먼지는 입자의 크기에 따라 50μm 이하인 총먼지(TSP, Total Suspended Particles)와 입자크기가 매우 작은 미세먼지(PM, Particulate Matter)로 구분한다. 미세먼지는 다시 지름이 10μm보다 작은 미세먼지(PM10)와 지름이 2.5μm보다 작은 미세먼지(PM2.5)로 나뉜다. PM10이 사람의 머리카락 지름(50~70μm)보다 약 1/5~1/7 정도로 작은 크기라면, PM2.5는 머리카락의 약 1/20~1/30에 불과할 정도로 매우 작다.\n" + "이처럼 미세먼지는 눈에 보이지 않을 만큼 매우 작기 때문에 대기 중에 머물러 있다 호흡기를 거쳐 폐 등에 침투하거나 혈관을 따라 체내로 이동하여 들어감으로써 건강에 나쁜 영향을 미칠 수도 있다." +

                                "세계보건기구(WHO)는 미세먼지(PM10, PM2.5)에 대한 대기질 가이드라인을 1987년부터 제시해 왔고, 2013년에는 세계보건기구 산하의 국제암연구소(IARC, International Agency for Research on Cancer)에서 미세먼지를 사람에게 발암이 확인된 1군 발암물질(Group 1)로 지정하였다.");
                        dlg1.setIcon(null);
                        dlg1.setView(dialogView1);
                        dlg1.setNegativeButton("닫기", null);
                        dlg1.show();
                        break;

                    case R.id.button2:
                        View dialogView2 = (View) View.inflate(MainActivity.this, R.layout.dialog, null);
                        AlertDialog.Builder dlg2 = new AlertDialog.Builder(MainActivity.this);
                        TextView tv2 = (TextView)dialogView2.findViewById(R.id.textView1);
                        dlg2.setTitle("미세먼지가 우리 몸에 끼치는 영향");
                        tv2.setText("TV나 신문, 인터넷에서 날씨예보와 함께 미세먼지 예보도 전해주는 시대가 되었다. 세계보건기구(WHO)가 미세먼지를 1군(Group 1) 발암물질로 분류하는 등 국민의 우려가 크기 때문이다.\n" +
                                "\n" +
                                "먼지 대부분은 코털이나 기관지 점막에서 걸러져 배출된다. 반면 미세먼지(PM10)는 입자의 지름이 사람 머리카락 굵기의 1/5~1/7 정도인 10μm 이하로 매우 작아 코, 구강, 기관지에서 걸러지지 않고 우리 몸속까지 스며든다.\n" +
                                "만약 미세먼지의 농도와 성분이 동일하다면 입자크기가 더 작을수록 건강에 해롭다. 같은 농도인 경우 PM2.5는 PM10보다 더 넓은 표면적을 갖기 때문에 다른 유해물질들이 더 많이 흡착될 수 있다. 또한 입자크기가 더 작으므로 기관지에서 다른 인체기관으로 이동할 가능성도 높다.\n" +
                                "\n" +
                                "일단 미세먼지가 우리 몸속으로 들어오면 면역을 담당하는 세포가 먼지를 제거하여 우리 몸을 지키도록 작용하게 되는데, 이때 부작용인 염증반응이 나타난다. 기도, 폐, 심혈관, 뇌 등 우리 몸의 각 기관에서 이러한 염증반응이 발생하면 천식, 호흡기, 심혈관계 질환 등이 유발될 수 있다.\n" +
                                "\n" +
                                "노인, 유아, 임산부나 심장 질환, 순환기 질환자들은 미세먼지로 인한 영향을 일반인보다 더 많이 받을 수 있으므로 각별히 주의하여야 한다.\n" +
                                "\n" +
                                "세계보건기구(WHO)는 2014년 한 해에 미세먼지로 인해 기대수명보다 일찍 사망하는 사람이 700만 명에 이른다고 발표했다. 세계보건기구 산하 국제암연구소(IARC)는 미세먼지를 인간에게 암을 일으키는 것으로 확인된 1군(Group 1) 발암물질로 2013년 10월 분류했다.\n");
                        dlg2.setIcon(null);
                        dlg2.setView(dialogView2);
                        dlg2.setNegativeButton("닫기", null);
                        dlg2.show();
                        break;

                    case R.id.button3:
                        View dialogView3 = (View) View.inflate(MainActivity.this, R.layout.dialog, null);
                        AlertDialog.Builder dlg3 = new AlertDialog.Builder(MainActivity.this);
                        TextView tv3 = (TextView)dialogView3.findViewById(R.id.textView1);
                        dlg3.setTitle("미세먼지 대처방법");
                        tv3.setText("① 예방이 최선이나, 어쩔 수 없이 노출되어 증상이 발생하게 되면 즉시 병원을 방문하여 초기에 관리받는 것이 좋다. \n" +
                                "② 대기오염을 줄이기 위한 학문적, 정책적, 외교적의 노력이 필요하나, 당장 개인이 할 수 있는 일들은 다음과 같다. \n" +
                                "\n" +
                                "• 미세먼지 농도가 높을 때에는 호흡기나 심혈관 질환자, 아이와 노인, 임산부는 외출을 자제해야 한다. 흡입되는 미세먼지는 활동의 강도와 기간에 비례하기 때문에 건강한 성인은 과격한 실외 활동을 최소화 것이 좋다. \n" +
                                "• 대개 도로변이 미세먼지 농도가 더 높기 때문에 도로변에서 운동하지 않도록 한다. \n" +
                                "• 실외 활동 시에 황사마스크를 착용하고, 불가피한 외출 후에는 코와 손을 잘 씻는 것이 좋다. \n" +
                                "• 창문을 열어 두면 외부에서 유입된 미세먼지로 실내의 미세먼지 농도가 증가하기 때문에 창문을 닫아야 한다. 에어필터나 공기청정기가 도움이 될 수 있다. \n" +
                                "• 실내에서 흡연을 하거나 촛불을 켜는 것은 미세먼지 농도를 높이는 것이므로 피해야 한다.\n");
                        dlg3.setIcon(null);
                        dlg3.setView(dialogView3);
                        dlg3.setNegativeButton("닫기", null);
                        dlg3.show();
                        break;
                }
            }
        };

        public FragmentAdapter(Context c) {
            super();
            mInflater = LayoutInflater.from(c);
        }

        @Override
        public Object instantiateItem(ViewGroup container , int position) {

            View v = null;

            if (position == 0) {
                v = mInflater.inflate(R.layout.activity_1, null);
                dustDate = (TextView)v.findViewById(R.id.now);
                dustDate.setText(SplashActivity.todayDateString);
                dustBad = (TextView)v.findViewById(R.id.poll);
                String dustTemp = (String.format("미세먼지 농도 : %s",SplashActivity.dust));
                dustBad.setText(dustTemp);
                howmuchBad = (TextView)v.findViewById(R.id.cool);
                mark = (ImageView) v.findViewById(R.id.feeel);
                if (Integer.parseInt(SplashActivity.dust) <=30){
                    howmuchBad.setText("좋음");
                    mark.setImageResource(R.drawable.good);
                }
                else if (31<Integer.parseInt(SplashActivity.dust) && Integer.parseInt(SplashActivity.dust)<=80){
                    howmuchBad.setText("보통");
                    mark.setImageResource(R.drawable.soso);

                }
                else if (81<Integer.parseInt(SplashActivity.dust) && Integer.parseInt(SplashActivity.dust)<=150){
                    howmuchBad.setText("나쁨");
                    mark.setImageResource(R.drawable.bad);

                }
                else if (151<Integer.parseInt(SplashActivity.dust)){
                    howmuchBad.setText("매우나쁨");
                    mark.setImageResource(R.drawable.badbad);

                }
            }
            else if (position == 1) {

                v = mInflater.inflate(R.layout.activity_2, null);

                City = (TextView)v.findViewById(R.id.city);
                nowTemperature = (TextView)v.findViewById(R.id.noww);
                todayTemp = (TextView)v.findViewById(R.id.today);
                todayDate = (TextView)v.findViewById(R.id.tod);
               // rainPercent = (TextView)findViewById(R.id.kang);
                cloudPercent = (TextView)v.findViewById(R.id.cloud);

                City.setText(SplashActivity.CityString);
                nowTemperature.setText(SplashActivity.nowTemperatureString);
                todayTemp.setText(SplashActivity.todayTempString);
                todayDate.setText(SplashActivity.todayDateString);
               // rainPercent.setText(rainPercentString);
                cloudPercent.setText(SplashActivity.cloudPercentString);


            }
            else if (position == 2){
                v = mInflater.inflate(R.layout.activity_3, null);
                v.findViewById(R.id.button1).setOnClickListener(mButtonClick);
                v.findViewById(R.id.button2).setOnClickListener(mButtonClick);
                v.findViewById(R.id.button3).setOnClickListener(mButtonClick);
            }

           // ((ViewPager) view).addView(v, 0);
            pager.addView (v,0);
            return v;
        }

        @Override

        public int getCount() {
            return 3; // 원하는 페이지 수
        }

        @Override
        public void destroyItem(View view, int position, Object object) {
            ((ViewPager)view).removeView((View)view);
        }
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
        @Override public void restoreState(Parcelable arg0, ClassLoader arg1){}
        @Override public Parcelable saveState() {return null; }
        @Override public void startUpdate (View arg0) {}
        @Override public void finishUpdate(View arg0) {}


    }

}




