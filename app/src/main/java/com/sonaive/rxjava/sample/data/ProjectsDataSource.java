package com.sonaive.rxjava.sample.data;

import rx.Observable;

/**
 * Created by liutao on 5/11/16.
 */
public interface ProjectsDataSource {

    Observable<ProjectList> getProjects(int page);

    void saveProjects(ProjectList projects);

}
