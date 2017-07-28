package com.alticode.flashalert.utils;

import android.content.Context;
import android.hardware.Camera;
import android.media.AudioManager;
import android.util.Log;

import com.alticode.flashalert.flashalert.FlashApplication;


/**
 * Created by TienDzung on 9/7/2015.
 */
public class FlashHelper {

    public static final int INCOMING_CALL = 1;
    public static final int INCOMING_SMS = 2;

    private boolean hasFlash;
    private Context mContext;
    private Camera camera;
    Camera.Parameters params;
    private boolean isFlashOn;
    private static FlashHelper mInstance = null;
    private AudioManager audioManager;
    public int batteryLevel = 100;
    public boolean stop = false;

    public void setIsAlertOn(boolean isAlertOn) {
        FlashApplication.savePrefAlert(isAlertOn);
    }

    public boolean isAlertOn() {
        return FlashApplication.getPrefAlert();
    }

    private FlashHelper(Context context) {
        mContext = context;
        audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
    }

    public static FlashHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new FlashHelper(context);
        }
        return mInstance;
    }

    public void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.d("Camera Error: ", e.getMessage());
            }
        }
    }

    public void turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }
    }

    public void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
        }
    }

    public void blinkSms(final int delay, final int times) {
        Log.d("DungNT", "Blink SMS");
        Thread t = new Thread() {
            public void run() {
                getCamera();
                try {
                    for (int i = 0; i < times * 2; i++) {
                        if (isFlashOn) {
                            turnOffFlash();
                        } else {
                            turnOnFlash();
                        }
                        sleep(delay);
                    }
                    releaseCamera();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    public void blinkCall(final int delay) {
        Thread t = new Thread() {
            public void run() {
                try {
                    getCamera();
                    while (!stop) {
                        if (isFlashOn) {
                            turnOffFlash();
                        } else {
                            turnOnFlash();
                        }
                        sleep(delay);
                    }
                    releaseCamera();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

    }

    public void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }


    public void alert(int alert_app) {
        boolean isAlert = false;

        switch (audioManager.getRingerMode()) {
            case AudioManager.RINGER_MODE_NORMAL:
                if (FlashApplication.getPrefModeNormal())
                    isAlert = true;
                break;
            case AudioManager.RINGER_MODE_SILENT:
                if (FlashApplication.getPrefModeSilent())
                    isAlert = true;
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                if (FlashApplication.getPrefModeVibrate())
                    isAlert = true;
                break;
            default:
                break;
        }
        if (!isAlert) {
            return;
        }
        switch (alert_app) {
            case INCOMING_CALL:
                blinkCall(FlashApplication.getPrefSpeed());
                break;
            case INCOMING_SMS:
                blinkSms(FlashApplication.getPrefSpeed(), FlashApplication.getPrefBlinkTimes());
                break;
            default:
                Log.d("DungNT", "Unknown alert app " + alert_app);
                break;
        }
    }

}
