package com.alticode.flashalert.utils;

import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceCategory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;


/**
 * Created by TienDzung on 9/6/2015.
 */
public class FlashPreferenceCategory extends PreferenceCategory {
    public FlashPreferenceCategory(Context context) {
        super(context);
    }

    public FlashPreferenceCategory(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlashPreferenceCategory(Context context, AttributeSet attrs,
                                   int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        TextView titleView = (TextView) view.findViewById(android.R.id.title);
        titleView.setTextColor(Color.parseColor("#30a399"));
    }
}