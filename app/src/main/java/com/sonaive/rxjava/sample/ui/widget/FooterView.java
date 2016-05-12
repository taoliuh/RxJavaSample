package com.sonaive.rxjava.sample.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sonaive.rxjava.sample.R;

/**
 * Created by liutao on 10/14/15.
 */
public class FooterView extends ViewGroup implements View.OnClickListener {

    public static final int STYLE_LOADING = 1;
    public static final int STYLE_NO_MORE = 2;
    public static final int STYLE_RETRY = 3;

    private TextView mText;
    private View mProgressBar;

    private RetryListener mListener;

    public FooterView(Context context) {
        this(context, null);
    }

    public FooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        mText = new TextView(context);
        LayoutParams textParams = new LayoutParams(-1, (int) (40 * density));
        mText.setLayoutParams(textParams);
        mText.setGravity(Gravity.CENTER);
        mText.setTextColor(getResources().getColor(R.color.body_text_2));
        mText.setTextSize(14);
        mText.setText(R.string.no_more_data);
        mText.setVisibility(INVISIBLE);
        addView(mText);

        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);
        LayoutParams progressBarParams = new LayoutParams((int) (32 * density), (int) (32 * density));
        mProgressBar.setLayoutParams(progressBarParams);
        mProgressBar.setVisibility(VISIBLE);
        addView(mProgressBar);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthConstraints = getPaddingLeft() + getPaddingRight();
        int heightConstraints = getPaddingTop() + getPaddingBottom();
        for (int i = 0; i < getChildCount(); i++) {
            measureChildWithMargins(
                    getChildAt(i),
                    widthMeasureSpec,
                    widthConstraints,
                    heightMeasureSpec,
                    heightConstraints
            );
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0;
        int right = getMeasuredWidth();
        int top = (int) ((getMeasuredHeight() - mText.getMeasuredHeight()) / 2f);
        int bottom = (int) ((getMeasuredHeight() + mText.getMeasuredHeight()) / 2f);
        mText.layout(left, top, right, bottom);

        left = (int) ((getMeasuredWidth() - mProgressBar.getMeasuredWidth()) / 2f);
        right = (int) ((getMeasuredWidth() + mProgressBar.getMeasuredWidth()) / 2f);
        top = (int) ((getMeasuredHeight() - mProgressBar.getMeasuredHeight()) / 2f);
        bottom = (int) ((getMeasuredHeight() + mProgressBar.getMeasuredHeight()) / 2f);
        mProgressBar.layout(left, top, right, bottom);
    }

    @Override
    protected void measureChildWithMargins(
            @NonNull View child,
            int parentWidthMeasureSpec,
            int widthUsed,
            int parentHeightMeasureSpec,
            int heightUsed) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();

        int childWidthMeasureSpec = getChildMeasureSpec(
                parentWidthMeasureSpec,
                widthUsed + lp.leftMargin + lp.rightMargin,
                lp.width);

        int childHeightMeasureSpec = getChildMeasureSpec(
                parentHeightMeasureSpec,
                heightUsed + lp.topMargin + lp.bottomMargin,
                lp.height);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onRetry();
        }
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new FooterView.LayoutParams(getContext(), attrs);
    }

    public void setFooterStyle(int style) {
        if (style == STYLE_LOADING) {
            setOnClickListener(null);
            mText.setVisibility(GONE);
            mProgressBar.setVisibility(VISIBLE);
        } else if (style == STYLE_NO_MORE) {
            setOnClickListener(null);
            mProgressBar.setVisibility(GONE);
            mText.setVisibility(VISIBLE);
            mText.setText(R.string.no_more_data);
        } else {
            setOnClickListener(this);
            mProgressBar.setVisibility(GONE);
            mText.setVisibility(VISIBLE);
            mText.setText(R.string.network_error_retry);
        }
    }

    public void setOnRetryListener(RetryListener listener) {
        mListener = listener;
    }

    public interface RetryListener {
        void onRetry();
    }

}
