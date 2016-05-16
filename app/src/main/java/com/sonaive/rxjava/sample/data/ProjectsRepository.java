package com.sonaive.rxjava.sample.data;

import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.sonaive.rxjava.sample.data.exception.EmptyDataException;

import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Action1;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by liutao on 5/12/16.
 */
public class ProjectsRepository implements ProjectsDataSource {

    private final ProjectsDataSource mProjectsRemoteDataSource;
    private final ProjectsDataSource mProjectsLocalDataSource;

    private static ProjectsRepository INSTANCE = null;

    public static ProjectsRepository getInstance(ProjectsDataSource projectsRemoteDataSource, ProjectsDataSource projectsLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ProjectsRepository(projectsRemoteDataSource, projectsLocalDataSource);
        }
        return INSTANCE;
    }

    private ProjectsRepository(@NonNull ProjectsDataSource projectsRemoteDataSource, @NonNull ProjectsDataSource projectsLocalDataSource) {
        mProjectsRemoteDataSource = checkNotNull(projectsRemoteDataSource);
        mProjectsLocalDataSource = checkNotNull(projectsLocalDataSource);
    }

    @Override
    public Observable<ProjectList> getProjects(final int page) {
        Observable<ProjectList> remoteProjects = mProjectsRemoteDataSource.getProjects(page)
                .doOnNext(new Action1<ProjectList>() {
                    @Override
                    public void call(ProjectList projectList) {
                        if (projectList == null) {
                            return;
                        }
                        projectList.page = page;
                        saveProjects(projectList);
                        if (projectList.isEmpty()) {
                            throw new EmptyDataException();
                        }
                        Logger.d("save projects, page %s", page);
                    }
                });
        Observable<ProjectList> localProjects = mProjectsLocalDataSource.getProjects(page).first();
        return Observable.concat(localProjects, remoteProjects);
    }

    @Override
    public void saveProjects(ProjectList projects) {
        mProjectsLocalDataSource.saveProjects(projects);
    }
}
