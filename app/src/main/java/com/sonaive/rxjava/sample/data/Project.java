package com.sonaive.rxjava.sample.data;

import android.support.annotation.NonNull;

/**
 * Created by liutao on 8/5/15.
 */
public class Project implements Comparable<Project> {
    public String _id;
    public int rating;
    public String media_type;
    public String directors;
    public String actors;
    public String creators;
    public String start_time;
    public String release_time;
    public long investment_scale;
    public long investment_amount;
    public long created;
    public int order;
    public String status;
    public boolean show;
    public float annual_returns;
    public String photo;
    public String large_photo;
    public String medium_photo;
    public String small_photo;
    public Founder[] founders;
    public String[] carousel;
    public String repayment;
    public String redemption_mechanism;
    public String redemption_mode;
    public String investment_period;
    public long investment_limit;
    public float acquired_amount;
    public String invest_status;
    public String intro;
    public String name;

    @Override
    public int compareTo(@NonNull Project another) {
        int result = (int) (another.created - this.created);
        if (result == 0) {
            // 已筹资金额较大的排位靠前，是新数据，算法removeDuplicates是从尾部删除重复数据的，这样避免删除重复的新数据
            result = (int) (another.acquired_amount - this.acquired_amount);
        }
        return result;
    }
}
