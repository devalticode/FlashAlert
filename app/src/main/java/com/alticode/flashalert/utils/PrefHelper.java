package com.alticode.flashalert.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.preference.PreferenceManager;

import com.alticode.flashalert.R;


/**
 * Created by Dzung on 9/14/2015.
 */
public class PrefHelper {
    private Context mContext;
    private SharedPreferences sharePref;
    private static PrefHelper mInstance = null;

    private PrefHelper(Context context) {
        mContext = context;
        sharePref = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public static PrefHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PrefHelper(context);
        }
        return mInstance;
    }

    public boolean getPrefIncomingCall() {
        return sharePref.getBoolean(mContext.getResources().getString(R.string.status_call_pref), false);
    }

    public boolean getPrefIncomingSms() {
        return sharePref.getBoolean(mContext.getResources().getString(R.string.status_sms_pref), false);
    }

    public boolean getPrefModeSilent() {
        return sharePref.getBoolean(mContext.getResources().getString(R.string.mode_silent_pref), false);
    }

    public boolean getPrefModeVibrate() {
        return sharePref.getBoolean(mContext.getResources().getString(R.string.mode_vibrate_pref), false);
    }

    public boolean getPrefModeNormal() {
        return sharePref.getBoolean(mContext.getResources().getString(R.string.mode_normal_pref), false);
    }

    public int getPrefSpeed() {
        return sharePref.getInt(mContext.getResources().getString(R.string.speed_pref), 100);
    }

    public int getPrefBlinkTimes() {
        return sharePref.getInt(mContext.getResources().getString(R.string.blink_time_pref), 3);
    }

    public int getPrefBattery() {
        return sharePref.getInt(mContext.getResources().getString(R.string.battery_pref), 20);
    }

    public boolean getPrefNotify() {
        return sharePref.getBoolean(mContext.getResources().getString(R.string.notify_pref), false);
    }

    public boolean getPrefAlert() {
        return sharePref.getBoolean(mContext.getResources().getString(R.string.alert_key_pref), true);
    }

    public void savePrefAlert(boolean value) {
        sharePref.edit().putBoolean(mContext.getResources().getString(R.string.alert_key_pref), value).apply();
    }
}
