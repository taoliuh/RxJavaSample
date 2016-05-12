package com.sonaive.rxjava.sample.api;

import com.sonaive.rxjava.sample.data.Projects;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by liutao on 5/11/16.
 */
public interface ProjectsService {
    @GET("/projects")
    Observable<Projects> getProjects(@Query("offset") int offset, @Query("limit") int limit);
}
