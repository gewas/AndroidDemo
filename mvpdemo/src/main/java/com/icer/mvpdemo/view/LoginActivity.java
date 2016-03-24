package com.icer.mvpdemo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.icer.mvpdemo.R;
import com.icer.mvpdemo.presenter.ILoginPresenter;
import com.icer.mvpdemo.presenter.LoginPresenterImpl;

public class LoginActivity extends AppCompatActivity implements ILoginView, View.OnClickListener {

    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnClear;
    private Button mBtnLogin;
    private ProgressBar mPb;

    private ILoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findView
        mEtUsername = (EditText) findViewById(R.id.login_username_et);
        mEtPassword = (EditText) findViewById(R.id.login_password_et);
        mBtnClear = (Button) findViewById(R.id.login_clear_btn);
        mBtnLogin = (Button) findViewById(R.id.login_login_btn);
        mPb = (ProgressBar) findViewById(R.id.login_pb);

        //setListener
        mBtnClear.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);

        //init
        mLoginPresenter = new LoginPresenterImpl(this);
        mLoginPresenter.setProgressBarVisibility(View.INVISIBLE);
    }

    @Override
    public void onClearText() {
        mEtUsername.setText("");
        mEtPassword.setText("");
    }

    @Override
    public void onLoginResult(Boolean result, int code) {
        mLoginPresenter.setProgressBarVisibility(View.INVISIBLE);
        mBtnClear.setEnabled(true);
        mBtnLogin.setEnabled(true);

        if (result) {
            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Login Failed, code = " + code, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        mPb.setVisibility(visibility);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_clear_btn: {
                mLoginPresenter.clear();
            }
            break;

            case R.id.login_login_btn: {
                mLoginPresenter.setProgressBarVisibility(View.VISIBLE);
                mBtnClear.setEnabled(false);
                mBtnLogin.setEnabled(false);
                mLoginPresenter.doLogin(mEtUsername.getText().toString(), mEtPassword.getText().toString());
            }
            break;
        }
    }
}
