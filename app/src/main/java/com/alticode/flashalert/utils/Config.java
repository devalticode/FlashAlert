package com.alticode.flashalert.utils;

import android.util.Log;

import java.util.Random;

/**
 * Created by TienDzung on 9/14/2015.
 */
public class Config {
    public static final int AD_FREQUENCY = 3;
    public static final boolean SHOW_FULL_AD_AT_CREATE = true;
    public static final boolean SHOW_FULL_AD_AT_CALL = true;
    public static final boolean SHOW_FULL_AD_AT_SMS = true;
    public static final boolean SHOW_FULL_AD_AT_SILENT = true;
    public static final boolean SHOW_FULL_AD_AT_VIBRATE = true;
    public static final boolean SHOW_FULL_AD_AT_NORMAL = true;
    public static final boolean SHOW_BANNER_AD = true;

    public static final String DEVICE_TEST_SONY = "YT91008UDL";
    public static final String DEVICE_TEST_LG = "LGE975d3097707";

    public static final String APP_URL = "https://play.google.com/store/apps/details?id=alert.flash.com.flashalert \n\n";
    public static final String RATE_APP_URL = "https://play.google.com/store/apps/details?id=alert.flash.com.flashalert";
    public static final String MORE_APP_URL = "https://play.google.com/store/apps/details?id=alert.flash.com.flashalert";

    public static int randomInt() {
        Random rand = new Random();
        return rand.nextInt();
    }

    public static boolean allowAd() {
        boolean show = randomInt() % AD_FREQUENCY == 0 ? true : false;
        Log.d("DungNT", "Show: " + show);
        return show;
    }
}
