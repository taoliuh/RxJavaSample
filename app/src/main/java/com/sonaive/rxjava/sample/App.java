package com.sonaive.rxjava.sample;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.Logger;
import com.sonaive.rxjava.sample.utils.ImagePipelineConfigFactory;

/**
 * Created by liutao on 5/12/16.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("RxSample").hideThreadInfo().setMethodCount(0);
        Fresco.initialize(getApplicationContext(), ImagePipelineConfigFactory.getOkHttpImagePipelineConfig(getApplicationContext()));
    }
}
