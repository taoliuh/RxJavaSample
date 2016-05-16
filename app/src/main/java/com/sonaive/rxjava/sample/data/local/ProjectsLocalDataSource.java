package com.sonaive.rxjava.sample.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.sonaive.rxjava.sample.data.ProjectList;
import com.sonaive.rxjava.sample.data.ProjectsDataSource;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by liutao on 5/13/16.
 */
public class ProjectsLocalDataSource implements ProjectsDataSource {

    private static ProjectsLocalDataSource INSTANCE;
    private final BriteDatabase mDatabaseHelper;
    private Func1<Cursor, ProjectList> mProjectsFunction;

    private ProjectsLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        ProjectsDbHelper dbHelper = new ProjectsDbHelper(context);
        SqlBrite sqlBrite = SqlBrite.create();
        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(dbHelper, Schedulers.io());
        mProjectsFunction = new Func1<Cursor, ProjectList>() {
            @Override
            public ProjectList call(Cursor cursor) {
                String data = cursor.getString(cursor.getColumnIndexOrThrow(ProjectsPersistenceContract.ProjectEntry.DATA));
                return new Gson().fromJson(data, ProjectList.class);
            }
        };
    }

    public static ProjectsLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ProjectsLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public Observable<ProjectList> getProjects(int page) {
        String[] projection = {
                ProjectsPersistenceContract.ProjectEntry.DATA
        };
        String sql = String.format("SELECT %s FROM %s WHERE %s=?",
                TextUtils.join(",", projection),
                ProjectsPersistenceContract.ProjectEntry.TABLE_NAME,
                ProjectsPersistenceContract.ProjectEntry.PAGE);
        return mDatabaseHelper.createQuery(ProjectsPersistenceContract.ProjectEntry.TABLE_NAME, sql, String.valueOf(page))
                .mapToOneOrDefault(mProjectsFunction, null);
    }

    @Override
    public void saveProjects(@NonNull ProjectList projects) {
        checkNotNull(projects);
        ContentValues values = new ContentValues();
        values.put(ProjectsPersistenceContract.ProjectEntry.PAGE, projects.page);
        values.put(ProjectsPersistenceContract.ProjectEntry.DATA, new Gson().toJson(projects));
        mDatabaseHelper.insert(ProjectsPersistenceContract.ProjectEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
    }
}
