package com.alticode.flashalert.flashalert;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import com.alticode.flashalert.R;
import com.alticode.flashalert.utils.FlashHelper;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Dzung on 9/18/2015.
 */
public class FlashApplication extends Application {
    public static SharedPreferences sharePref;
    public static Context mContext;
    public static FlashHelper mFlashHelper;
    public BatteryReceiver batteryReceiver;

    private static final int NOTIFY_TIMER = 60000 * 60;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        sharePref = PreferenceManager.getDefaultSharedPreferences(this);
        mFlashHelper = FlashHelper.getInstance(this);
        /* Register battery receiver */
        batteryReceiver = new BatteryReceiver();
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, iFilter);

        /* Create notify */
        Timer notifyTimer = new Timer();
        TimerTask notifyTask = new TimerTask() {
            @Override
            public void run() {
                createNotify();
            }
        };
        notifyTimer.scheduleAtFixedRate(notifyTask, 0, NOTIFY_TIMER);
    }

    public static boolean getPrefIncomingCall() {
        return sharePref.getBoolean(mContext.getResources().getString(R.string.status_call_pref), false);
    }

    public static boolean getPrefIncomingSms() {
        return sharePref.getBoolean(mContext.getResources().getString(R.string.status_sms_pref), false);
    }

    public static boolean getPrefModeSilent() {
        return sharePref.getBoolean(mContext.getResources().getString(R.string.mode_silent_pref), false);
    }

    public static boolean getPrefModeVibrate() {
        return sharePref.getBoolean(mContext.getResources().getString(R.string.mode_vibrate_pref), false);
    }

    public static boolean getPrefModeNormal() {
        return sharePref.getBoolean(mContext.getResources().getString(R.string.mode_normal_pref), false);
    }

    public static int getPrefSpeed() {
        return sharePref.getInt(mContext.getResources().getString(R.string.speed_pref), 100);
    }

    public static int getPrefBlinkTimes() {
        return sharePref.getInt(mContext.getResources().getString(R.string.blink_time_pref), 3);
    }

    public static int getPrefBattery() {
        return sharePref.getInt(mContext.getResources().getString(R.string.battery_pref), 20);
    }

    public static boolean getPrefNotify() {
        return sharePref.getBoolean(mContext.getResources().getString(R.string.notify_pref), false);
    }

    public static boolean getPrefAlert() {
        return sharePref.getBoolean(mContext.getResources().getString(R.string.alert_key_pref), true);
    }

    public static void savePrefAlert(boolean value) {
        sharePref.edit().putBoolean(mContext.getResources().getString(R.string.alert_key_pref), value).apply();
    }

    private void createNotify() {
        Intent i = new Intent(this, SettingsActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this.getApplicationContext(), (int) System.currentTimeMillis(), i, 0);
        Notification notify;
        Notification.Builder notifyBuilder = new Notification.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Touch to more setting")
                .setSmallIcon(R.drawable.ic_flash_on_white_36dp)
                .setContentIntent(pIntent);
        // Build notification
        if (Build.VERSION.SDK_INT < 16) {
            notify = notifyBuilder.getNotification();
        } else {
            notify = notifyBuilder.build();
        }

        NotificationManager notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notifyManager.notify(0, notify);
    }
}
