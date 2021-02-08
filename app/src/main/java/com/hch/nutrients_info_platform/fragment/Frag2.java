package com.hch.nutrients_info_platform.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.hch.nutrients_info_platform.activity.HomeActivity;
import com.hch.nutrients_info_platform.adapter.MyAdapter;
import com.hch.nutrients_info_platform.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Frag2 extends Fragment // Fragment 클래스를 상속받아야한다
{

    public static Frag2 newInstance() {
        return new Frag2();
    }

    private TextView search_Src_View;
    private View view;
    private ViewPager pager;
    List<Drawable> temp;

    int currentPage = 0;
    Timer timer;
    final int NUM_PAGES = 3;
    final long DELAY_MS = 500;//작업 실행 전 지연 시간(ms)
    final long PERIOD_MS = 3000; // 연속 태스크 실행 사이의 시간(ms)

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag2, container, false);

        //이미지 슬라이드(광고)
        temp = new ArrayList<>();
        temp.add(ContextCompat.getDrawable(getActivity(), R.drawable.syntha));
        temp.add(ContextCompat.getDrawable(getActivity(), R.drawable.protein));
        temp.add(ContextCompat.getDrawable(getActivity(), R.drawable.bcaa));

        MyAdapter a = new MyAdapter(temp, getActivity());

        pager = (ViewPager) view.findViewById(R.id.photos_viewpager);
        pager.setAdapter(a);

        //Indicator
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager, true);

        //검색 창
        search_Src_View = (TextView) view.findViewById(R.id.search_view_src);

        //검색 창 클릭 시 -> frag3으로 이동
        search_Src_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).setFrag(2);
            }
        });
        return view;
    }

    public void onResume() {
        super.onResume();

        // Adapter 세팅 후 타이머 실행
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                currentPage = pager.getCurrentItem();
                int nextPage = currentPage + 1;

                if (nextPage >= NUM_PAGES) {
                    nextPage = 0;
                }
                pager.setCurrentItem(nextPage, true);
                currentPage = nextPage;
            }
        };

        timer = new Timer(); // thread에 작업용 thread 추가
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

    }

    @Override
    public void onPause() {
        super.onPause();
        // 다른 액티비티나 프레그먼트 실행시 타이머 제거
        // 현재 페이지의 번호는 변수에 저장되어 있으니 취소해도 상관없음
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}