package com.example.a20230318weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    Button search_bt;
    TextView city_show;

    LocationManager locationManager;
    EditText city_text;
    String locationInfo;

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            locationInfo = "纬度：" + latitude + "\n经度：" + longitude;
            city_show.setText(locationInfo);
            city_text.setText(locationInfo);//上传经纬度换成地方，这经纬度谁懂啊，离谱了。明天搞json。
//            Log.d("LOCATION",locationInfo);
            // 将位置信息输出到EditText控件中


        }
        @Override
        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {}
        @Override
        public void onProviderEnabled(String provider) {}
        @Override
        public void onProviderDisabled(String provider) {}
    };

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

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

        // 检查是否已获得定位权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 未获得定位权限，请求用户授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // 已获得定位权限，开始定位
            startLocation();
        }
        // 初始化EditText控件
//        city_text = view2.findViewById(R.id.edittext);
        city_text.setText(locationInfo);
        Log.d("LOCATION", "locationInfo"+locationInfo);//为什么是空值呢？

        city_show.setText(locationInfo);
        city_show.setVisibility(View.VISIBLE);

//        search_bt.setOnClickListener(new myOnClickListener());//这里有问题，
        //应该是由于不同view之间的切换导致的，不能读取内容。
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户已授权，开始定位
                startLocation();
            } else {
                // 用户拒绝授权，显示提示信息并退出应用
                Toast.makeText(this, "未获得定位权限，无法使用定位功能！", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void startLocation() {
        // 初始化LocationManager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);



        // 请求位置更新
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }



    //位置信息销毁
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        // 检查位置权限是否已经被授予
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//            return;
//        }
//
//        // 注册位置监听器
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        // 移除位置监听器
//        locationManager.removeUpdates(locationListener);
//    }


//    public class myOnClickListener implements OnClickListener{
//        @Override
//        public void onClick(View v) {
////            String city_string = city_text.getText().toString();
////                   city_show.setText(city_string);
//        }
//    }
}