package com.example.a20230318weather;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLAndJSONGet {
    private OnCompleteListener mListener;

    public interface OnCompleteListener {
        void onComplete(String result);
    }

    public void setOnCompleteListener(OnCompleteListener listener) {
        this.mListener = listener;
    }

    public void request(String urlString) {
        new RequestTask().execute(urlString);
    }

    private class RequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String urlString = params[0];
            if (TextUtils.isEmpty(urlString)) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(urlString);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                return buffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (mListener != null) {
                mListener.onComplete(result);
            }
        }
    }

    public static <T> T parseJson(String json, Class<T> clazz) {
        Gson gson = new Gson();

        try {
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
//powered by GPT3
// 前提条件：Android使用Google控件，尽量少的使用匿名内部类。
// 写一个工具类，请求URL，并存储URL返回的JSON数据。工具类名称叫URLAndJSONGet