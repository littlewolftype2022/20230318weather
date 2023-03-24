package com.example.a20230318weather;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    Button search_bt,location_bt;
    TextView city_show;
    EditText city_text;
    String key;
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            Bundle bundle = msg.getData();
//            String value = bundle.getString("LOCATION");
//            //textView.setText(value);
//            Log.e("TT", "handleMessage:"+value);
//        }
//    };


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

        view2 = arrayList.get(1);
        search_bt = (Button) view2.findViewById(R.id.search);
        city_text = (EditText) view2.findViewById(R.id.edittext);
        city_show = (TextView) view1.findViewById(R.id.textView1);
        location_bt = (Button) view2.findViewById(R.id.locationbutton);

        location_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GetLocation.class);
                startActivity(intent);
//                GetLocation2 myThread = new GetLocation2(MainActivity.this);//this只能在本个activity里面用，
//                // 其他地方要加Mainactivity或者其他class。
//                myThread.start();//线程有问题，还是用activity吧？

            }
//            class MyThread extends Thread {
//                @Override
//                public void run() {
//
//                    // 在后台执行工作
//                    // 注意不能在这里更新 UI，否则会抛出异常
//                }
//            }
//        });

//        String text = getIntent().getStringExtra(key);	//key是发送数据键值对中的key
//        city_show.setText(text);
//        GetLocation2 myThread = new GetLocation2(this,handler);
//        myThread.start();

        });
    }
}

