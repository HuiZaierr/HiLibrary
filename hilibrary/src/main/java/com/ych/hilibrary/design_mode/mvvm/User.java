package com.ych.hilibrary.design_mode.mvvm;

import androidx.lifecycle.ViewModel;

public class User{
    private String nikeName;
    private String address;

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNikeName() {
        return nikeName;
    }

    public String getAddress() {
        return address;
    }
}
