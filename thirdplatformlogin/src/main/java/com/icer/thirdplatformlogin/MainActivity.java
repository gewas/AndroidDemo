package com.icer.thirdplatformlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.icer.thirdplatformlogin.baidu.SocialActivity;
import com.icer.thirdplatformlogin.weibo.WeiboActivity;

/**
 * Created by icer on 2015/11/2.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.baidu_btn).setOnClickListener(this);
        findViewById(R.id.wechat_btn).setOnClickListener(this);
        findViewById(R.id.weibo_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.baidu_btn: {
                startActivity(new Intent(this, SocialActivity.class));
            }
            break;

            case R.id.wechat_btn: {

            }
            break;

            case R.id.weibo_btn: {
                startActivity(new Intent(this, WeiboActivity.class));
            }
            break;
        }
    }
}
