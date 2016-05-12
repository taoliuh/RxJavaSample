package com.sonaive.rxjava.sample.data.remote;

import com.sonaive.rxjava.sample.Config;
import com.sonaive.rxjava.sample.api.ApiManager;
import com.sonaive.rxjava.sample.data.Projects;
import com.sonaive.rxjava.sample.data.ProjectsDataSource;

import rx.Observable;

/**
 * Created by liutao on 5/11/16.
 */
public class ProjectsRemoteDataSource implements ProjectsDataSource {

    private ProjectsRemoteDataSource() {}

    private static class Holder {
        private static final ProjectsRemoteDataSource INSTANCE = new ProjectsRemoteDataSource();
    }

    public static ProjectsRemoteDataSource getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public Observable<Projects> getProjects(int page) {
        return ApiManager.getInstance().getProjectsService().getProjects(page * Config.PAGE_SIZE, Config.PAGE_SIZE);
    }
}
