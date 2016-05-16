package com.sonaive.rxjava.sample.data.remote;

import com.sonaive.rxjava.sample.Config;
import com.sonaive.rxjava.sample.api.ParameterMap;
import com.sonaive.rxjava.sample.api.ServiceGenerator;
import com.sonaive.rxjava.sample.data.ProjectList;
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
    public Observable<ProjectList> getProjects(int page) {
        ParameterMap map = new ParameterMap();
        map.put("offset", String.valueOf(page * Config.PAGE_SIZE));
        map.put("limit", String.valueOf(Config.PAGE_SIZE));
        return ServiceGenerator.getInstance().getProjectsService().getProjects(map.transformMap());
    }

    @Override
    public void saveProjects(ProjectList projects) {
        // do nothing
    }
}
