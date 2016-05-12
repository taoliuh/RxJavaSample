package com.sonaive.rxjava.sample.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sonaive.rxjava.sample.utils.ActivityUtils;
import com.sonaive.rxjava.sample.R;
import com.sonaive.rxjava.sample.data.ProjectsRepository;
import com.sonaive.rxjava.sample.data.remote.ProjectsRemoteDataSource;
import com.sonaive.rxjava.sample.presenter.ProjectsPresenter;
import com.sonaive.rxjava.sample.ui.frag.ProjectsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ProjectsFragment recorderFragment =
                (ProjectsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (recorderFragment == null) {
            // Create the fragment
            recorderFragment = ProjectsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), recorderFragment, R.id.contentFrame);
        }

        ProjectsPresenter projectsPresenter = new ProjectsPresenter(ProjectsRepository.getInstance(ProjectsRemoteDataSource.getInstance()), recorderFragment);
    }
}
