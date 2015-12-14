package com.icer.wifihotspot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ssidET;
    private EditText passwordET;
    private Button startBtn;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshStartBtn();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        regListener();
        refreshStartBtn();
        hideSystemBar();
        registerReceiver(mBroadcastReceiver, new IntentFilter(WiFiAPManager.ACTION_WIFI_AP_STATE_CHANGED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    private void initView() {
        startBtn = (Button) findViewById(R.id.btn);
        ssidET = (EditText) findViewById(R.id.ssid);
        String ssid = AppPreference.getStringValue(AppPreference.KEY_SSID, null);
        if (ssid != null)
            ssidET.setText(ssid);

        passwordET = (EditText) findViewById(R.id.password);
        String password = AppPreference.getStringValue(AppPreference.KEY_PASSWORD, null);
        if (ssid != null)
            passwordET.setText(password);
    }

    private void regListener() {
        startBtn.setOnClickListener(this);
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                refreshStartBtn();
                while (!s.toString().matches("[a-zA-Z0-9\\-_]+")) {
                    if (s.length() > 0) {
                        s.delete(s.length() - 1, s.length());
                    } else
                        break;
                }
            }
        };
        ssidET.addTextChangedListener(tw);
        passwordET.addTextChangedListener(tw);
    }

    private void refreshStartBtn() {
        boolean flag = (!WiFiAPManager.isAPOn(this) && ssidET.getText().toString().trim().length() > 0 && passwordET.getText().toString().length() >= 8) || WiFiAPManager.isAPOn(this);
        startBtn.setClickable(flag);
        startBtn.setTextColor(getResources().getColor(flag ? R.color.colorText : R.color.colorText2));
        startBtn.setText(getString(WiFiAPManager.isAPOn(this) ? R.string.stop : R.string.start));
    }


    private void hideSystemBar() {
        getSupportActionBar().hide();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                if (!WiFiAPManager.isAPOn(this)) {
                    AppPreference.setStringValue(AppPreference.KEY_SSID, ssidET.getText().toString().trim());
                    AppPreference.setStringValue(AppPreference.KEY_PASSWORD, passwordET.getText().toString());
                }
                WiFiAPManager.toggleAP(this, ssidET.getText().toString().trim(), passwordET.getText().toString());
                break;
        }
    }

}
