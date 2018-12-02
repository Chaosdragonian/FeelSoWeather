package com.wonju.yonsei.feelsoweather;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    TabLayout sliding_tab;
    ViewPager pager;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliding_tab = (TabLayout) findViewById(R.id.sliding_tabs);
        pager = (ViewPager) findViewById(R.id.viewPager);

        pager.setAdapter(new FragmentAdapter(getApplicationContext()));
        pager.setOffscreenPageLimit(3); // 안보이는 페이지 로딩해 놓을 갯수
        sliding_tab.addTab(sliding_tab.newTab().setText("미세먼지"), 0, true); // 페이지 등록
        sliding_tab.addTab(sliding_tab.newTab().setText("날씨"), 1);
        sliding_tab.addTab(sliding_tab.newTab().setText("관련정보"), 2);


        sliding_tab.addOnTabSelectedListener(pagerListener);

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(sliding_tab));

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
                        dlg1.setTitle("미세먼지란?");
                        dlg1.setIcon(null);
                        dlg1.setView(dialogView1);
                        dlg1.setNegativeButton("닫기", null);
                        dlg1.show();
                        break;

                    case R.id.button2:
                        View dialogView2 = (View) View.inflate(MainActivity.this, R.layout.dialog, null);
                        AlertDialog.Builder dlg2 = new AlertDialog.Builder(MainActivity.this);
                        dlg2.setTitle("미세먼지가 우리 몸에 끼치는 영향");
                        dlg2.setIcon(null);
                        dlg2.setView(dialogView2);
                        dlg2.setNegativeButton("닫기", null);
                        dlg2.show();
                        break;

                    case R.id.button3:
                        View dialogView3 = (View) View.inflate(MainActivity.this, R.layout.dialog, null);
                        AlertDialog.Builder dlg3 = new AlertDialog.Builder(MainActivity.this);
                        dlg3.setTitle("미세먼지 대처방법");
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
        public Object instantiateItem(View view, int position) {

            View v = null;

            if (position == 0) {
                v = mInflater.inflate(R.layout.activity_1, null);

            }
            else if (position == 1) {
                v = mInflater.inflate(R.layout.activity_2, null);

            }
            else if (position == 2){
                v = mInflater.inflate(R.layout.activity_3, null);
                v.findViewById(R.id.button1).setOnClickListener(mButtonClick);
                v.findViewById(R.id.button2).setOnClickListener(mButtonClick);
                v.findViewById(R.id.button3).setOnClickListener(mButtonClick);


            }

            ((ViewPager) view).addView(v, 0);

            return v;
        }

        @Override

        public int getCount() {
            return 3; // 원하는 페이지 수
        }

        @Override
        public void destroyItem(View view, int positionm, Object object) {
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


