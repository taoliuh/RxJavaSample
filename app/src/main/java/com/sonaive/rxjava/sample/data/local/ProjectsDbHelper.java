package com.sonaive.rxjava.sample.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liutao on 5/13/16.
 */
public class ProjectsDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "projects.db";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String TEXT_TYPE = " TEXT";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ProjectsPersistenceContract.ProjectEntry.TABLE_NAME + " (" +
                    ProjectsPersistenceContract.ProjectEntry._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    ProjectsPersistenceContract.ProjectEntry.PAGE + TEXT_TYPE + COMMA_SEP +
                    ProjectsPersistenceContract.ProjectEntry.DATA + TEXT_TYPE + COMMA_SEP +
                    "UNIQUE (" + ProjectsPersistenceContract.ProjectEntry.PAGE + ") ON CONFLICT REPLACE)";

    public ProjectsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
