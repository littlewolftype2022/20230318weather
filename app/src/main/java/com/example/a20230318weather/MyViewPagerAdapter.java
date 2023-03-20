package com.example.a20230318weather;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter {

    private List<View> arrayList;

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(arrayList.get(position),0);
        return arrayList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(arrayList.get(position));

    }

    public MyViewPagerAdapter(List<View> arrayList){
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position){

       List<String> list =new ArrayList<>();
        list.add("天气");list.add("设置");
        return list.get(position);
    }
}
