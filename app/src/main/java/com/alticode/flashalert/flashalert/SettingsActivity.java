package com.alticode.flashalert.flashalert;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.alticode.flashalert.R;
import com.alticode.flashalert.utils.Config;
import com.alticode.flashalert.utils.FlashHelper;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatActivity {

    private SettingsActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.seting_activity);

        getFragmentManager().beginTransaction().replace(R.id.content_frame, new FlashPreferenceFragment()).commit();

        Toolbar actionbar = (Toolbar) findViewById(R.id.toolbar);
        actionbar.setTitle(getString(R.string.setting));

        actionbar.setTitleTextColor(Color.WHITE);
        actionbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsActivity.this.finish();
            }
        });

    }


    public static class FlashPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
        private Preference btnShare;
        private Preference btnRate;
        private Preference btnMore;
        private Preference btnCall;
        private Preference btnSMS;
        private Preference btnSilent;
        private Preference btnVibrate;
        private Preference btnNormal;

        private FlashHelper mFlashHelper;
        private SettingsActivity mActivity;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mActivity = (SettingsActivity) this.getActivity();
            mFlashHelper = FlashApplication.mFlashHelper;

            addPreferencesFromResource(R.xml.flash_preference);
            btnShare = findPreference(getString(R.string.share_pref));
            btnShare.setOnPreferenceClickListener(this);

            btnRate = findPreference(getString(R.string.rate_pref));
            btnRate.setOnPreferenceClickListener(this);

            btnMore = findPreference(getString(R.string.more_pref));
            btnMore.setOnPreferenceClickListener(this);

            btnCall = findPreference(getString(R.string.status_call_pref));
            btnCall.setOnPreferenceChangeListener(this);

            btnSMS = findPreference(getString(R.string.status_sms_pref));
            btnSMS.setOnPreferenceChangeListener(this);

            btnSilent = findPreference(getString(R.string.mode_silent_pref));
            btnSilent.setOnPreferenceChangeListener(this);

            btnVibrate = findPreference(getString(R.string.mode_vibrate_pref));
            btnVibrate.setOnPreferenceChangeListener(this);

            btnNormal = findPreference(getString(R.string.mode_normal_pref));
            btnNormal.setOnPreferenceChangeListener(this);

            if(mFlashHelper.isAlertOn()) {
                setEnableSetting(true);
            } else {
                setEnableSetting(false);
            }

        }

        private void setEnableSetting(boolean isEnable) {
            btnCall.setEnabled(isEnable);
            btnSMS.setEnabled(isEnable);
            btnSilent.setEnabled(isEnable);
            btnVibrate.setEnabled(isEnable);
            btnNormal.setEnabled(isEnable);
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            if (preference == btnShare) {
                handleShare();
            } else if (preference == btnRate) {
                handleRate();
            } else if (preference == btnMore) {
                handleMore();
            }
            return false;
        }

        private void handleShare() {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                String msg = "\nLet me recommend you this application\n\n";
                msg = msg + Config.APP_URL;
                i.putExtra(Intent.EXTRA_TEXT, msg);
                startActivity(Intent.createChooser(i, "Share via"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void handleRate() {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Config.RATE_APP_URL)));
        }

        private void handleMore() {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Config.MORE_APP_URL)));
        }


        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {

            return true;
        }
    }

}
