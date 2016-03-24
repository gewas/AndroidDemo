package com.icer.mvpdemo.presenter;

import android.os.Handler;
import android.os.Looper;

import com.icer.mvpdemo.model.IUser;
import com.icer.mvpdemo.model.UserModel;
import com.icer.mvpdemo.view.ILoginView;


/**
 * Created by icer-SP4 on 2016/3/25.
 */
public class LoginPresenterImpl implements ILoginPresenter {

    private ILoginView mILoginView;
    private IUser mUser;
    private Handler mHandler;

    public LoginPresenterImpl(ILoginView iLoginView) {
        mILoginView = iLoginView;
        initUser();
        mHandler = new Handler(Looper.getMainLooper());
    }

    private void initUser() {
        mUser = new UserModel("mvp", "mvp");
    }

    @Override
    public void clear() {
        mILoginView.onClearText();
    }

    @Override
    public void doLogin(String username, String password) {
        Boolean res = true;
        final int code = mUser.checkUserValidity(username, password);
        res = (code == 0);
        final Boolean finalRes = res;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mILoginView.onLoginResult(finalRes, code);
            }
        }, 1000 * 3);
    }

    @Override
    public void setProgressBarVisibility(int visibility) {
        mILoginView.onSetProgressBarVisibility(visibility);
    }
}
