package com.icer.mvpdemo.model;

import android.text.TextUtils;

/**
 * Created by icer-SP4 on 2016/3/25.
 */
public class UserModel implements IUser {

    private String mUsername;
    private String mPassword;

    public UserModel(String username, String password) {
        mUsername = username;
        mPassword = password;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    @Override
    public int checkUserValidity(String username, String password) {
        int res = 0;
        if (TextUtils.isEmpty(username))
            res = 1;
        else if (TextUtils.isEmpty(password))
            res = 2;
        else if (!username.equals(mUsername) || !password.equals(mPassword))
            res = 3;
        return res;
    }
}
