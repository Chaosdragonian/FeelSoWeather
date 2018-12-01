package com.wonju.yonsei.feelsoweather;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterViewAnimator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment {


    public Fragment3() {
        // Required empty public constructor
  }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);

        Button btn1 = (Button) view.findViewById(R.id.button1);
        Button btn2 = (Button) view.findViewById(R.id.button2);
        Button btn3 = (Button) view.findViewById(R.id.button3);

        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



            }
        });

        return view;
    }

}
