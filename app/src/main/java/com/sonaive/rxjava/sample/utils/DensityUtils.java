package com.sonaive.rxjava.sample.utils;

import android.content.res.Resources;

/**
 * Created by liutao on 5/6/16.
 */
public class DensityUtils {
    public static int dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return  (int) (dp * scale + 0.5f);
    }

    public static int sp2px(Resources resources, float sp){
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return (int) (sp * scale);
    }
}
