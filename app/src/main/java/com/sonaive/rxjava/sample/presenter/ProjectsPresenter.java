package com.sonaive.rxjava.sample.presenter;

import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.sonaive.rxjava.sample.data.ProjectList;
import com.sonaive.rxjava.sample.data.ProjectsRepository;
import com.sonaive.rxjava.sample.data.exception.EmptyDataException;

import java.util.concurrent.atomic.AtomicInteger;

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
    private AtomicInteger mPage = new AtomicInteger(0);

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
                .getProjects(mPage.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProjectList>() {
                    @Override
                    public void onCompleted() {
                        mProjectsView.hidePullToRefreshView();
                        mProjectsView.hideView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof EmptyDataException) {
                            mProjectsView.showEmptyView();
                            return;
                        }
                        e.printStackTrace();
                        mProjectsView.hidePullToRefreshView();
                        mProjectsView.showRetryFooter();
                        mProjectsView.showErrorView();
                    }

                    @Override
                    public void onNext(ProjectList projects) {
                        if (projects == null || projects.isNoMoreData()) {
                            mProjectsView.showNoMoreDataFooter();
                            return;
                        }
                        Logger.d("show projects, page %s", projects.page);
                        mProjectsView.showProjects(projects.page, projects.projects);
                        mPage.set(projects.page + 1);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void refresh() {
        mPage.set(0);
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
