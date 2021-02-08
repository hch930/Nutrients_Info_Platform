package com.hch.nutrients_info_platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.hch.nutrients_info_platform.R;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //네이버
    private static String OAUTH_CLIENT_ID = "B01HqK6QvKZAJmcoDDCL";
    private static String OAUTH_CLIENT_SECRET = "UEvc9L3rGS";
    private static String OAUTH_CLIENT_NAME = "네이버 아이디로 로그인";

    private static OAuthLogin mOAuthLoginModule;
    private static Context mContext;
    private OAuthLoginButton mOAuthLoginButton;

    //카카오톡
    private SessionCallback sessionCallback = new SessionCallback();

    //페이스북
    private LoginCallback LoginCallback;
    private CallbackManager callbackManager;
    private Button facebook_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //네이버
        mContext = MainActivity.this;

        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(
                MainActivity.this
                ,OAUTH_CLIENT_ID
                ,OAUTH_CLIENT_SECRET
                ,OAUTH_CLIENT_NAME
                //,OAUTH_CALLBACK_INTENT
                // SDK 4.1.4 버전부터는 OAUTH_CALLBACK_INTENT변수를 사용하지 않습니다.
        );

        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);

        //카카오톡

        Session.getCurrentSession().addCallback(sessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();

        //페이스북
        AppEventsLogger.activateApp(getApplication());
        callbackManager = CallbackManager.Factory.create();
        LoginCallback = new LoginCallback();

        facebook_login = (Button) findViewById(R.id.facebook_login);
        facebook_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager loginManager = LoginManager.getInstance();
                loginManager.logInWithReadPermissions(MainActivity.this,
                        Arrays.asList("public_profile", "email"));
                loginManager.registerCallback(callbackManager, LoginCallback);
            }
        });
    }

    //네이버
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                redirectHomeActivity();
                String accessToken = mOAuthLoginModule.getAccessToken(mContext);
                String refreshToken = mOAuthLoginModule.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginModule.getExpiresAt(mContext);
                String tokenType = mOAuthLoginModule.getTokenType(mContext);
            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext);
            }
        };
    };

    //카카오톡
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    int result = errorResult.getErrorCode();

                    if(result == ApiErrorCode.CLIENT_ERROR_CODE) {
                        Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"로그인 도중 오류가 발생했습니다: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Toast.makeText(getApplicationContext(),"세션이 닫혔습니다. 다시 시도해 주세요: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    redirectHomeActivity();
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException e) {
            Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: "+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //페이스북
    public class LoginCallback implements FacebookCallback<LoginResult> {
        // 로그인 성공 시 호출 됩니다. Access Token 발급 성공.
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.e("Callback :: ", "onSuccess");
            requestMe(loginResult.getAccessToken());
            redirectHomeActivity();
        }

        // 로그인 창을 닫을 경우, 호출됩니다.
        @Override
        public void onCancel() {
            Log.e("Callback :: ", "onCancel");
        }

        // 로그인 실패 시에 호출됩니다.
        @Override
        public void onError(FacebookException error) {
            Log.e("Callback :: ", "onError : " + error.getMessage());
        }

        // 사용자 정보 요청
        public void requestMe(AccessToken token) {
            GraphRequest graphRequest = GraphRequest.newMeRequest(token,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.e("result",object.toString());
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday");
            graphRequest.setParameters(parameters);
            graphRequest.executeAsync();
        }
    }

    //카카오톡 + 페이스북
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    //로그인 성공시 HomeActivity로 이동
    public void redirectHomeActivity(){
        Intent intent = new Intent(getApplication() ,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}