package com.yash.tongaonkar.multiselectspinnerexample;

/**
 * Created by yash on 18/11/17.
 */

public class LoginResponse {

    public String id;

    public String username;

    public String password;

    public LoginResponse() {
    }

    public LoginResponse(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return this.username;
    }
}
