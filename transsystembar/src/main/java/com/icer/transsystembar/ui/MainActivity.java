package com.icer.transsystembar.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.icer.transsystembar.R;
import com.icer.transsystembar.app.BaseActivity;

/**
 * Created by icer on 2015-09-24.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private android.support.v7.widget.Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();
        initView();
        regListener();
    }

    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_launcher);
        mToolbar.setLogo(R.drawable.ic_launcher);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        mToolbar.setSubtitleTextColor(getResources().getColor(R.color.color_white));
        mToolbar.setOverflowIcon(getResources().getDrawable(android.R.drawable.ic_menu_more));
        mToolbar.setSubtitle("Demo");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_collection:
                toast("collection");
                return true;

            case R.id.action_settings:
                toast("settings");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {

    }

    private void regListener() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
