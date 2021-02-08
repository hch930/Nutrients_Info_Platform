package com.hch.nutrients_info_platform.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hch.nutrients_info_platform.R;
import com.hch.nutrients_info_platform.activity.MainActivity;
import com.hch.nutrients_info_platform.activity.SplashActivity;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class Frag5 extends Fragment // Fragment 클래스를 상속받아야한다
{
    private View view;
    private Button logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag5, container, false);
        logout = (Button) view.findViewById(R.id.logout);
        logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext().getApplicationContext(), "정상적으로 로그아웃되었습니다.", Toast.LENGTH_SHORT).show();

                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
            }
        });
        return view;
    }
}