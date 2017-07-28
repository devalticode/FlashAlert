package com.alticode.flashalert.flashalert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.alticode.flashalert.utils.FlashHelper;

/**
 * Created by TienDzung on 9/7/2015.
 */
public class CallReceiver extends BroadcastReceiver {
    FlashApplication app;
    @Override
    public void onReceive(Context context, Intent intent) {
        app = (FlashApplication)context.getApplicationContext();
        FlashHelper mFlashHelper = FlashApplication.mFlashHelper;

        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)) {
               if ((mFlashHelper.batteryLevel >= FlashApplication.getPrefBattery()) && mFlashHelper.isAlertOn() == true && FlashApplication.getPrefIncomingCall() == true) {
                mFlashHelper.stop = false;
                mFlashHelper.alert(FlashHelper.INCOMING_CALL);
            }
        } else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                TelephonyManager.EXTRA_STATE_IDLE)
                || intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            mFlashHelper.stop = true;
        }
    }
}
