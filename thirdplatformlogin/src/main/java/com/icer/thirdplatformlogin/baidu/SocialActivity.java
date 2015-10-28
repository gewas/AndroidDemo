package com.icer.thirdplatformlogin.baidu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.frontia.Frontia;
import com.baidu.frontia.FrontiaUser;
import com.baidu.frontia.api.FrontiaAuthorization;
import com.baidu.frontia.api.FrontiaAuthorization.MediaType;
import com.baidu.frontia.api.FrontiaAuthorizationListener.AuthorizationListener;
import com.baidu.frontia.api.FrontiaAuthorizationListener.UserInfoListener;
import com.icer.thirdplatformlogin.R;

import java.util.ArrayList;

public class SocialActivity extends Activity {

    public final static String APIKEY = "iG2ffdkYaq8kIjrSfvjMcUrf";

    private TextView mResultTextView;
    private FrontiaAuthorization mAuthorization;
    private final static String Scope_Basic = "basic";

    private final static String Scope_Netdisk = "netdisk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_main);
        Frontia.init(this.getApplicationContext(), APIKEY);
        mAuthorization = Frontia.getAuthorization();
        setupViews();
    }

    private void setupViews() {
        mResultTextView = (TextView) findViewById(R.id.social_result);


        Button baiduButton = (Button) findViewById(R.id.baiduBtn);
        baiduButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startBaidu();
            }

        });

        Button baiduCancelButton = (Button) findViewById(R.id.baiduQuitBtn);
        baiduCancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startBaiduLogout();
            }

        });

        Button baiduStatusButton = (Button) findViewById(R.id.baiduStatusBtn);
        baiduStatusButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startBaiduStatus();
            }

        });

        Button baiduUserInfoButton = (Button) findViewById(R.id.baiduInfoBtn);
        baiduUserInfoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startBaiduUserInfo();
            }

        });

        Button allCancelButton = (Button) findViewById(R.id.allQuit);
        allCancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startAllLogout();
            }

        });

    }


    protected void startBaiduUserInfo() {
        userinfo(MediaType.BAIDU.toString());

    }


    protected void startBaiduStatus() {
        boolean result = mAuthorization.isAuthorizationReady(FrontiaAuthorization.MediaType.BAIDU.toString());
        if (result) {
            mResultTextView.setText("已经登录百度帐号");
        } else {
            mResultTextView.setText("未登录百度帐号");
        }
    }

    protected void startBaiduLogout() {
        boolean result = mAuthorization.clearAuthorizationInfo(
                FrontiaAuthorization.MediaType.BAIDU.toString());
        if (result) {
            mResultTextView.setText("百度退出成功");
        } else {
            mResultTextView.setText("百度退出失败");
        }
    }

    protected void startBaidu() {
        ArrayList<String> scope = new ArrayList<String>();
        scope.add(Scope_Basic);
        scope.add(Scope_Netdisk);
        mAuthorization.authorize(this, FrontiaAuthorization.MediaType.BAIDU.toString(), scope, new AuthorizationListener() {

            @Override
            public void onSuccess(FrontiaUser result) {
                if (null != mResultTextView) {
                    mResultTextView.setText(
                            "social id: " + result.getId() + "\n"
                                    + "token: " + result.getAccessToken() + "\n"
                                    + "expired: " + result.getExpiresIn());
                }
            }

            @Override
            public void onFailure(int errCode, String errMsg) {
                if (null != mResultTextView) {
                    mResultTextView.setText("errCode:" + errCode
                            + ", errMsg:" + errMsg);
                }
            }

            @Override
            public void onCancel() {
                if (null != mResultTextView) {
                    mResultTextView.setText("cancel");
                }
            }

        });
    }

    private void userinfo(String accessToken) {
        mAuthorization.getUserInfo(accessToken, new UserInfoListener() {

            @Override
            public void onSuccess(FrontiaUser.FrontiaUserDetail result) {
                if (null != mResultTextView) {
                    String resultStr = "username:" + result.getName() + "\n"
                            + "birthday:" + result.getBirthday() + "\n"
                            + "city:" + result.getCity() + "\n"
                            + "province:" + result.getProvince() + "\n"
                            + "sex:" + result.getSex() + "\n"
                            + "pic url:" + result.getHeadUrl() + "\n";
                    mResultTextView.setText(resultStr);
                }
            }

            @Override
            public void onFailure(int errCode, String errMsg) {
                if (null != mResultTextView) {
                    mResultTextView.setText("errCode:" + errCode
                            + ", errMsg:" + errMsg);
                }
            }

        });
    }

    private void startAllLogout() {
        boolean result = mAuthorization.clearAllAuthorizationInfos();
        if (result) {
            mResultTextView.setText("所有登录退出成功");
        } else {
            mResultTextView.setText("所有登录退出失败");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != mAuthorization) {
            mAuthorization.onActivityResult(requestCode, resultCode, data);
        }
    }


}
