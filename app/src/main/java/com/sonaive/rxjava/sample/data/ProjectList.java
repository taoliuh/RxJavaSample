package com.sonaive.rxjava.sample.data;

import java.util.List;

/**
 * Created by liutao on 5/12/16.
 */
public class ProjectList {
    public int page;
    public List<Project> projects;

    public boolean isEmpty() {
        return page == 0 && (projects == null || projects.size() == 0);
    }

    public boolean isNoMoreData() {
        return projects == null || projects.size() == 0;
    }
}
