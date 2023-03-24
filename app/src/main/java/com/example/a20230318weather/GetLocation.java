package com.example.a20230318weather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;

import java.util.Timer;
import java.util.TimerTask;

public class GetLocation extends AppCompatActivity {
    String locationInfo,locationGetURL;
    LocationManager locationManager;
    private Timer timer;
    private RequestQueue mRequestQueue;
//    LocationListener locationListener;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    public class MyLocationListener implements LocationListener{
        @Override
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            locationInfo = "纬度：" + latitude + "\n经度：" + longitude;
            locationGetURL = latitude+","+longitude;
            Log.e("LOCATION",locationInfo);
            // 将位置信息输出到EditText控件中
            //请求参数
            URLAndJSONGet urlAndJSONGet = new URLAndJSONGet();
            urlAndJSONGet.setOnCompleteListener(new URLAndJSONGet.OnCompleteListener() {
                @Override
                public void onComplete(String result) {
                    // 在这里处理请求结果，result为请求返回的JSON字符串
                    MyLocationListener obj = URLAndJSONGet.parseJson(result, MyLocationListener.class);

                }
            });
            Log.e("LOCATIONGET",locationGetURL);
            urlAndJSONGet.request("https://api.qweather.com/v7/weather/now?location="+locationGetURL+"&key=8f0dc74c20e94d13bcd27a41384ecddf");
        }
        @Override
        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {finish();}
        @Override
        public void onProviderEnabled(String provider) {}
        @Override
        public void onProviderDisabled(String provider) {}
    }
    LocationListener locationListener = new MyLocationListener();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_layout);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE); // 设置可见性
        TextView textView = (TextView) findViewById(R.id.showlocation);
        textView.setVisibility(View.VISIBLE);
        Log.e("LOCATION",locationInfo+" start oncreate");
        //?Toast toast = Toast.makeText(new Context(), "默认的Toast", Toast.LENGTH_SHORT).show();


        // 检查是否已获得定位权限
        // 初始化EditText控件
//        city_text = view2.findViewById(R.id.edittext);
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
        Log.e("TAG", "startLocation: happen");

    }
    //位置信息销毁


    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 未获得定位权限，请求用户授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // 已获得定位权限，开始定位
            startLocation();
            Log.e("LOCATION",locationInfo+" start in else ");
        }
        Log.e("TAG", "onStart: happen");

        // 创建Timer对象，并通过schedule()方法设置定时任务
        super.onStart();
        timer = new Timer();
        Log.e("LOCATION",locationInfo+" start after timer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.e("LOCATION",locationInfo+" start");
                // 在定时任务中执行关闭应用程序的代码
//                finish();
//                android.os.Process.killProcess(android.os.Process.myPid());

                finish();
            }
        }, 5000); // 设定关闭时间，单位为毫秒，这里设置为5秒后关闭


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }

        Log.d("TAG", "onDestroy: happen");

    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // 在Activity被销毁前保存其状态
        outState.putBoolean("isClosing", true);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // 在Activity重新创建时恢复其状态
        boolean isClosing = savedInstanceState.getBoolean("isClosing", false);
        if (isClosing) {
            finish();
        }
    }
}