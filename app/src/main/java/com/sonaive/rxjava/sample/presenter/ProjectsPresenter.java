package com.sonaive.rxjava.sample.presenter;

import android.support.annotation.NonNull;

import com.sonaive.rxjava.sample.data.Projects;
import com.sonaive.rxjava.sample.data.ProjectsRepository;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by liutao on 5/12/16.
 */
public class ProjectsPresenter implements ProjectsContract.Presenter {
    private final ProjectsRepository mRepository;
    private final ProjectsContract.View mProjectsView;
    private CompositeSubscription mSubscriptions;
    private int mPage = 0;

    public ProjectsPresenter(@NonNull ProjectsRepository projectsRepository, @NonNull ProjectsContract.View projectsView) {
        mRepository = checkNotNull(projectsRepository);
        mProjectsView = checkNotNull(projectsView);
        mProjectsView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void loadProjects() {
        mSubscriptions.clear();
        Subscription subscription = mRepository
                .getProjects(mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Projects>() {
                    @Override
                    public void onCompleted() {
                        mProjectsView.hidePullToRefreshView();
                        mProjectsView.hideView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mProjectsView.hidePullToRefreshView();
                        mProjectsView.showRetryFooter();
                        mProjectsView.showErrorView();
                    }

                    @Override
                    public void onNext(Projects projects) {
                        if (projects == null || projects.projects == null || projects.projects.size() == 0) {
                            mProjectsView.showNoMoreDataFooter();
                            return;
                        }
                        mProjectsView.showProjects(mPage, projects.projects);
                        ++mPage;
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void refresh() {
        mPage = 0;
        loadProjects();
    }

    @Override
    public void retry() {
        loadProjects();
    }

    @Override
    public void subscribe() {
        loadProjects();
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
