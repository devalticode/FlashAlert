package com.alticode.flashalert.flashalert;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.alticode.flashalert.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnPower;
    private ImageButton btnLed;
    private ImageButton btnSetting;
    MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPower = (ImageButton) findViewById(R.id.btnPower);
        btnLed = (ImageButton) findViewById(R.id.btnLed);
        btnSetting = (ImageButton) findViewById(R.id.btnSetting);
        btnPower.setOnClickListener(this);
        btnSetting.setOnClickListener(this);

        toggleButtonImage();
    }

    private void toggleButtonImage() {
        playSound();
        if (FlashApplication.getPrefAlert()) {
            btnPower.setImageResource(R.mipmap.power_on);
            btnLed.setImageResource(R.mipmap.led_on);
        } else {
            btnPower.setImageResource(R.mipmap.power_off);
            btnLed.setImageResource(R.mipmap.led_off);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPower:
                handlePowerClick();
                break;
            case R.id.btnSetting:
                handleSettingClick();
                break;
            default:
                break;
        }
    }


    private void handleSettingClick() {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);

    }

    private void handlePowerClick() {
        if (FlashApplication.getPrefAlert()) {
            // turn off flash
            FlashApplication.savePrefAlert(false);
        } else {
            // turn on flash
            FlashApplication.savePrefAlert(true);
        }
        toggleButtonImage();
    }

    private void playSound() {
        if (FlashApplication.getPrefAlert()) {
            mp = MediaPlayer.create(MainActivity.this, R.raw.light_switch_off);
        } else {
            mp = MediaPlayer.create(MainActivity.this, R.raw.light_switch_on);
        }

        mp.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
