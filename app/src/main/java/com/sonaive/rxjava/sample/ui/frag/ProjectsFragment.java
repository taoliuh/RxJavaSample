package com.sonaive.rxjava.sample.ui.frag;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.sonaive.rxjava.sample.R;
import com.sonaive.rxjava.sample.data.Project;
import com.sonaive.rxjava.sample.presenter.ProjectsContract;
import com.sonaive.rxjava.sample.ui.adapter.ProjectsAdapter;
import com.sonaive.rxjava.sample.ui.widget.EndlessScrollListener;
import com.sonaive.rxjava.sample.ui.widget.ErrorView;
import com.sonaive.rxjava.sample.ui.widget.FooterView;
import com.sonaive.rxjava.sample.utils.DensityUtils;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by liutao on 5/12/16.
 */
public class ProjectsFragment extends Fragment implements ProjectsContract.View {

    private ListView mListView;
    private ErrorView mErrorView;
    private PtrFrameLayout mPtrFrameLayout;
    private FooterView mFooterView;

    private ProjectsAdapter mAdapter;

    private ProjectsContract.Presenter mPresenter;
    private ResetEndlessScrollListener mScrollListener;

    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ProjectsAdapter(getActivity());
    }

    @Override
    public void showProjects(int page, List<Project> projects) {
        mAdapter.addAll(page, projects);
    }

    @Override
    public void showErrorView() {
        mErrorView.showErrorView();
    }

    @Override
    public void showEmptyView() {
        mErrorView.showEmptyView();
    }

    @Override
    public void showProgressView() {
        mErrorView.showProgress();
    }

    @Override
    public void hideView() {
        mErrorView.hide();
    }

    @Override
    public void showNoMoreDataFooter() {
        mFooterView.setFooterStyle(FooterView.STYLE_NO_MORE);
    }

    @Override
    public void showRetryFooter() {
        mFooterView.setFooterStyle(FooterView.STYLE_RETRY);
    }

    @Override
    public void showLoadingFooter() {
        mFooterView.setFooterStyle(FooterView.STYLE_LOADING);
    }

    @Override
    public void hidePullToRefreshView() {
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void setPresenter(ProjectsContract.Presenter presenter) {
        mPresenter = presenter;
        mScrollListener = new ResetEndlessScrollListener(mPresenter);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.projects_frag, container, false);
        mListView = (ListView) root.findViewById(R.id.list_view);
        mPtrFrameLayout = (PtrFrameLayout) root.findViewById(R.id.store_house_ptr_frame);
        configureHeader();
        mListView.setOnScrollListener(mScrollListener);
        mErrorView = (ErrorView) root.findViewById(R.id.error_view);
        mErrorView.setOnRetryListener(new ErrorView.RetryListener() {
            @Override
            public void onRetry() {
                showProgressView();
                mPresenter.retry();
            }
        });

        mFooterView = new FooterView(getActivity());
        mFooterView.setBackgroundResource(R.color.grey_2);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(-1, DensityUtils.dp2px(getResources(), 60));
        mFooterView.setLayoutParams(params);
        mFooterView.setOnRetryListener(new FooterView.RetryListener() {
            @Override
            public void onRetry() {
                showLoadingFooter();
                mPresenter.retry();
            }
        });

        mListView.addFooterView(mFooterView);
        mListView.setAdapter(mAdapter);
        return root;

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }

    private void configureHeader() {
        StoreHouseHeader storeHouseHeader = new StoreHouseHeader(getActivity());
        storeHouseHeader.setPadding(0, getResources().getDimensionPixelSize(R.dimen.pull_to_refresh_padding), 0, getResources().getDimensionPixelSize(R.dimen.pull_to_refresh_padding));
        storeHouseHeader.setTextColor(Color.GRAY);

        storeHouseHeader.initWithString("M");
        mPtrFrameLayout.setHeaderView(storeHouseHeader);
        mPtrFrameLayout.addPtrUIHandler(storeHouseHeader);
        mPtrFrameLayout.autoRefresh(false);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view2) {
                return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, view, view2);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                mPresenter.refresh();
                mScrollListener.reset();
            }
        });
    }

    private static class ResetEndlessScrollListener extends EndlessScrollListener {

        private ProjectsContract.Presenter mPresenter;

        public ResetEndlessScrollListener(ProjectsContract.Presenter presenter) {
            mPresenter = presenter;
        }

        @Override
        public void onLoadMore(int page, int totalItemsCount) {
            mPresenter.loadProjects();
        }
    }
}
