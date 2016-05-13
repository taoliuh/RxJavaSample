package com.sonaive.rxjava.sample.api;

import com.sonaive.rxjava.sample.data.ProjectList;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by liutao on 5/11/16.
 */
public interface ProjectsService {
    @GET("/projects")
    Observable<ProjectList> getProjects(@QueryMap Map<String, String> map);
}
