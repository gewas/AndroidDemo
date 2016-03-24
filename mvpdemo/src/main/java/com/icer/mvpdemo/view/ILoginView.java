package com.icer.mvpdemo.view;

/**
 * Created by icer-SP4 on 2016/3/25.
 */
public interface ILoginView {
    void onClearText();

    void onLoginResult(Boolean result, int code);

    void onSetProgressBarVisibility(int visibility);
}
