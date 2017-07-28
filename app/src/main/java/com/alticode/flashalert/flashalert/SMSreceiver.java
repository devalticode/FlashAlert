package com.alticode.flashalert.flashalert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alticode.flashalert.utils.FlashHelper;


public class SMSreceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        FlashHelper mFlashHelper = FlashApplication.mFlashHelper;
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if ((mFlashHelper.batteryLevel >= FlashApplication.getPrefBattery()) && mFlashHelper.isAlertOn() == true && FlashApplication.getPrefIncomingSms())
                mFlashHelper.alert(FlashHelper.INCOMING_SMS);
        }
    }

}