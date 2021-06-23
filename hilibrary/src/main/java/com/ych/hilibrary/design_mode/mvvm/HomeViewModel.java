package com.ych.hilibrary.design_mode.mvvm;

import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

//    private SavedStateHandle savedState;
//    private HomeViewModel(SavedStateHandle savedState){
//        this.savedState = savedState;
//    }

    public LiveData<User> getUserInfo(){
        MutableLiveData<User> liveData = new MutableLiveData<>();
        //数据的获取
        User user = new User();
        user.setNikeName("nikeName");
        user.setAddress("address");
        liveData.postValue(user);
        return liveData;
    }
}
