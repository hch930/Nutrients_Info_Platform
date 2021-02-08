package com.hch.nutrients_info_platform.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hch.nutrients_info_platform.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends PagerAdapter {
    Context context;
    List<Drawable> obj;

    public MyAdapter(List<Drawable> res, Context context){
        obj = res;
        this.context = context;
    }

    public MyAdapter(Context context) {
        this.context = context;
    }

    //position 위치에 속하는 페이지 뷰를 생성하고, 이를 container 뷰에 추가
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = null;

        if(context != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.pager_adapter, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            imageView.setImageDrawable(obj.get(position));
        }
        container.addView(view);
        return view;
    }

    //전체 페이지 수 결정
    public int getCount() {
        return obj.size();
    }

    //페이지뷰가 키 객체와 연관되는지 확인.
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object) ;
    }

    //position 위치의 페이지 제거.
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}