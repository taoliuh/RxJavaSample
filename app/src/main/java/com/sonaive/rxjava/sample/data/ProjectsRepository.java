package com.sonaive.rxjava.sample.data;

import android.support.annotation.NonNull;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by liutao on 5/12/16.
 */
public class ProjectsRepository implements ProjectsDataSource {

    private final ProjectsDataSource mProjectsRemoteDataSource;

    private static ProjectsRepository INSTANCE = null;

    public static ProjectsRepository getInstance(ProjectsDataSource projectsRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ProjectsRepository(projectsRemoteDataSource);
        }
        return INSTANCE;
    }

    private ProjectsRepository(@NonNull ProjectsDataSource projectsRemoteDataSource) {
        mProjectsRemoteDataSource = checkNotNull(projectsRemoteDataSource);
    }

    @Override
    public Observable<ProjectList> getProjects(int page) {
        Observable<ProjectList> remoteProjects = mProjectsRemoteDataSource.getProjects(page);
        return remoteProjects;
    }
}
