package com.alticode.flashalert.flashalert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.alticode.flashalert.utils.FlashHelper;


/**
 * Created by Dzung on 9/18/2015.
 */
public class BatteryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        FlashHelper mFlashHelper = FlashApplication.mFlashHelper;
        int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int level = -1;
        if (currentLevel >= 0 && scale > 0) {
            level = (currentLevel * 100) / scale;
        }
        mFlashHelper.batteryLevel = level;
    }
}
