package com.sonaive.rxjava.sample.data.local;

import android.provider.BaseColumns;

/**
 * Created by liutao on 5/13/16.
 */
public final class ProjectsPersistenceContract {
    interface ProjectEntry extends BaseColumns {
        String TABLE_NAME = "projects";
        String PAGE = "page";
        String DATA = "data";
    }
}
