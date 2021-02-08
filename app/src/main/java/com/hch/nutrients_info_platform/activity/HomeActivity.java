package com.hch.nutrients_info_platform.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hch.nutrients_info_platform.R;

import com.hch.nutrients_info_platform.fragment.Frag1;
import com.hch.nutrients_info_platform.fragment.Frag2;
import com.hch.nutrients_info_platform.fragment.Frag3;
import com.hch.nutrients_info_platform.fragment.Frag4;
import com.hch.nutrients_info_platform.fragment.Frag5;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag1 frag1;
    private Frag2 frag2;
    private Frag3 frag3;
    private Frag4 frag4;
    private Frag5 frag5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.action_bar:
                        setFrag(0);
                        break;
                    case R.id.action_Home:
                        setFrag(1);
                        break;
                    case R.id.action_search:
                        setFrag(2);
                        break;
                    case R.id.action_mynutr:
                        setFrag(3);
                        break;
                    case R.id.action_myinfor:
                        setFrag(4);
                        break;
                }
                return true;
            }
        });

        frag1=new Frag1();
        frag2=new Frag2();
        frag3=new Frag3();
        frag4=new Frag4();
        frag5=new Frag5();
        setFrag(1); // 첫 프래그먼트 화면 지정
    }

    // 프레그먼트 교체
    public void setFrag(int n)
    {
        fm = getSupportFragmentManager();
        ft= fm.beginTransaction();
        switch (n)
        {
            case 0:
                ft.replace(R.id.Main_Frame,frag1);
                ft.commit();
                break;

            case 1:
                ft.replace(R.id.Main_Frame,frag2);
                ft.commit();
                break;

            case 2:
                ft.replace(R.id.Main_Frame,frag3);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.Main_Frame,frag4);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.Main_Frame,frag5);
                ft.commit();
                break;
        }
    }
}
