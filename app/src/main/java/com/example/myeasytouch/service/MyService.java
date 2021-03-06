package com.example.myeasytouch.service;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.example.myeasytouch.event.MessageEvent;
import com.example.myeasytouch.holder.MyViewHolder;
import com.example.myeasytouch.view.MutiTaskMainView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MyService extends AccessibilityService {
    private final static String TAG = "MyService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        MyViewHolder.setIsServiceRunning(true);
        Log.d(TAG, "isServiceRunning");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        MyViewHolder.setIsServiceRunning(false);
        Log.d(TAG, intent.toString());
        return super.onUnbind(intent);
    }

    @Subscribe
    public void onEventMainThread(MessageEvent event){
        String msg = event.getMsg();
        if (MutiTaskMainView.GLOBAL_ACTION_BACK.equals(msg)) {
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
        }
        if (MutiTaskMainView.GLOBAL_ACTION_RECENTS.equals(msg)){
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
        }
    }

}
