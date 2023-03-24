package com.example.a20230318weather;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.net.URL;
import java.util.Scanner;

public class LocationAndSearch extends AsyncTask<Void, Void, String> {

    private final double longitude; // 经度信息
    private final double latitude; // 纬度信息
    private final String apiKey; // 高德地图 API Key
    private OnGetCityNameListener listener; // 回调监听器

    public LocationAndSearch(double longitude, double latitude, String apiKey,OnGetCityNameListener listener) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.apiKey = apiKey;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            // 构建请求 URL
            URL url = new URL("https://restapi.amap.com/v3/geocode/regeo?key=" + apiKey +
                    "&location=" + longitude + "," + latitude + "&extensions=base&output=json");

            // 发送请求并解析响应数据
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();
            JSONObject jsonObject = new JSONObject(response.toString());
            String city = jsonObject.getJSONObject("regeocode").getJSONObject("addressComponent").getString("city");
            return city;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String city) {
        if (listener != null) {
            listener.onGetCityName(city);
        }
    }

    public interface OnGetCityNameListener {
        void onGetCityName(String city);
    }

}
