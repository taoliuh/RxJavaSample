package com.sonaive.rxjava.sample.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.orhanobut.logger.Logger;
import com.sonaive.rxjava.sample.R;
import com.sonaive.rxjava.sample.data.Project;
import com.sonaive.rxjava.sample.ui.widget.ProjectView;
import com.sonaive.rxjava.sample.utils.DensityUtils;
import com.sonaive.rxjava.sample.utils.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liutao on 8/11/15.
 */
public class ProjectsAdapter extends BaseAdapter {
    private ConcurrentHashMap<Integer, List<Project>> mResult = new ConcurrentHashMap<>();
    private List<Project> mProjects = new ArrayList<>();
    private Context mContext;

    public ProjectsAdapter(Context context) {
        mContext = context;
    }

    public void addAll(int page, List<Project> list) {
        if (list == null) {
            return;
        }
        if (mResult.replace(page, list) == null) {
            mResult.put(page, list);
        }

        mProjects.clear();
        for (int i = 0; i < mResult.size(); i++) {
            mProjects.addAll(mResult.get(i));
        }
        Logger.d("Projects size is: " + mProjects.size());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mProjects.size();
    }

    @Override
    public Project getItem(int position) {
        return mProjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_project, parent, false);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(-1, (int) DensityUtils.dp2px(mContext.getResources(), 140));
            convertView.setLayoutParams(params);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder); // setting Holder as arbitrary object for row
        } else { // view recycling
            // row already contains Holder object
            holder = (ViewHolder) convertView.getTag();
        }
        final Project item = mProjects.get(position);
        double progress = item.acquired_amount / (double) item.investment_amount * 100;
        ProjectView project = holder.project;
        if (position == getCount() - 1) {
            if (holder.project.getMeasuredHeight() != (int) DensityUtils.dp2px(mContext.getResources(), 130)) {
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(-1, (int) DensityUtils.dp2px(mContext.getResources(), 130));
                project.setLayoutParams(params);
                project.showBottomDivider(false);
            }
        } else {
            if (holder.project.getMeasuredHeight() != (int) DensityUtils.dp2px(mContext.getResources(), 140)) {
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(-1, (int) DensityUtils.dp2px(mContext.getResources(), 140));
                project.setLayoutParams(params);
                project.showBottomDivider(true);
            }
        }
        project.getImage().setImageURI(Uri.parse(StringUtils.null2Empty(item.small_photo)));
        project.getTitle().setText(item.name);
        project.getCreators().setText("主创：" + item.creators);
        project.getExpectation().setText(new DecimalFormat("#.00").format(item.annual_returns * 100) + "%");
        project.getMoney().setText("已融资" + item.acquired_amount + "元");
        project.getPercent().setText(new DecimalFormat("##").format(progress) + "%");
        project.getProgressBar().setProgress((int) (progress));
        String origin = "投资期限" + item.investment_period;
        SpannableStringBuilder period = new SpannableStringBuilder(origin);
        period.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_3)), 4, origin.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        project.getPeriod().setText(period);
        project.setStatus(item.invest_status);

        project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d("position is %d, name is %s", position, item.name);
            }
        });
        return convertView;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder {
        public ProjectView project;

        public ViewHolder(View view) {
            project = (ProjectView) view.findViewById(R.id.project_view);
        }
    }
}
