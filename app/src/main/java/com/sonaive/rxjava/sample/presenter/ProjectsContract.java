package com.sonaive.rxjava.sample.presenter;

import com.sonaive.rxjava.sample.BasePresenter;
import com.sonaive.rxjava.sample.BaseView;
import com.sonaive.rxjava.sample.data.Project;

import java.util.List;

/**
 * Created by liutao on 5/12/16.
 */
public interface ProjectsContract {

    interface View extends BaseView<Presenter> {
        void showProjects(int page, List<Project> projects);

        void showErrorView();

        void showEmptyView();

        void showProgressView();

        void hideView();

        void showNoMoreDataFooter();

        void showRetryFooter();

        void showLoadingFooter();

        void hidePullToRefreshView();
    }

    interface Presenter extends BasePresenter {
        void loadProjects();

        void refresh();

        void retry();
    }
}
