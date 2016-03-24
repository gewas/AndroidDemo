package com.icer.mvpdemo.presenter;

/**
 * Created by icer-SP4 on 2016/3/25.
 */
public interface ILoginPresenter {
    void clear();

    void doLogin(String username, String password);

    void setProgressBarVisibility(int visibility);
}
