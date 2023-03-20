package com.example.a20230318weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    Button search_bt;
    TextView city_text,city_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        LayoutInflater layoutInflater = getLayoutInflater().from(this);

        View view1 = layoutInflater.inflate(R.layout.view1,null);
        View view2 = layoutInflater.inflate(R.layout.view2,null);

        ArrayList<View> arrayList = new ArrayList<>();
        arrayList.add(view1);
        arrayList.add(view2);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyViewPagerAdapter(arrayList));
        viewPager.setCurrentItem(0);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_AUTO);

        search_bt = (Button) viewPager.findViewById(R.id.search);
        city_text = (TextView) viewPager.findViewById(R.id.edittext);
        city_show = (TextView) viewPager.findViewById(R.id.textView1);

        search_bt.setOnClickListener(new myOnClickListener());
    }


    public class myOnClickListener implements OnClickListener{
        @Override
        public void onClick(View v) {
//            String city_string = city_text.getText().toString();
//                   city_show.setText(city_string);
        }
    }
}