package com.sonaive.rxjava.sample.ui.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sonaive.rxjava.sample.R;
import com.sonaive.rxjava.sample.utils.DensityUtils;

/**
 * Created by liutao on 10/12/15.
 */
public class ErrorView extends ViewGroup implements View.OnClickListener {

    private ImageView mImage;
    private TextView mText;
    private ProgressBar mProgressBar;
    private ObjectAnimator mAlpha;

    private RetryListener mListener;

    public ErrorView(Context context) {
        this(context, null);
    }

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        mAlpha = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f);
        mAlpha.setDuration(600);
        mAlpha.setInterpolator(new DecelerateInterpolator());
        mAlpha.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setVisibility(GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void initView(Context context) {
        setBackgroundColor(getResources().getColor(R.color.white));
        float density = context.getResources().getDisplayMetrics().density;

        mImage = new ImageView(context);
        LayoutParams imageParams = new LayoutParams(-2, -2);
        mImage.setLayoutParams(imageParams);
        mImage.setImageResource(R.drawable.ic_empty);
        mImage.setVisibility(INVISIBLE);
        addView(mImage);

        mText = new TextView(context);
        LayoutParams textParams = new LayoutParams(-1, -1);
        mText.setLayoutParams(textParams);
        mText.setGravity(Gravity.CENTER);
        mText.setTextColor(Color.parseColor("#b3b3b3"));
        mText.setTextSize(13);
        mText.setText(R.string.no_data);
        mText.setVisibility(INVISIBLE);
        mText.setOnClickListener(this);
        addView(mText);

        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);
        LayoutParams progressBarParams = new LayoutParams((int) (50 * density), (int) (50 * density));
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
        int top = 0;
        int bottom = getMeasuredHeight();
        mText.layout(left, top, right, bottom);

        left = (int) ((getMeasuredWidth() - mImage.getMeasuredWidth()) / 2f);
        right = (int) ((getMeasuredWidth() + mImage.getMeasuredWidth()) / 2f);
        bottom = (int) (getMeasuredHeight() / 2f - DensityUtils.dp2px(getResources(), 30));
        top = bottom - mImage.getMeasuredHeight();
        mImage.layout(left, top, right, bottom);

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
        return new ErrorView.LayoutParams(getContext(), attrs);
    }

    public void showProgress() {
        setAlpha(1f);
        setVisibility(VISIBLE);
        mProgressBar.setVisibility(VISIBLE);
        mText.setVisibility(GONE);
        mImage.setVisibility(GONE);
    }

    public void hide() {
        if (mAlpha.isRunning()) {
            return;
        }
        mAlpha.start();
    }

    public void showErrorView() {
        setAlpha(1f);
        setVisibility(VISIBLE);
        mText.setVisibility(VISIBLE);
        mImage.setVisibility(VISIBLE);
        mText.setText(R.string.err_network_unavailable);
        mImage.setImageResource(R.drawable.ic_error);
        mProgressBar.setVisibility(GONE);
    }

    public void showEmptyView() {
        setAlpha(1f);
        setVisibility(VISIBLE);
        mText.setVisibility(VISIBLE);
        mImage.setVisibility(VISIBLE);
        mText.setText(R.string.no_data);
        mImage.setImageResource(R.drawable.ic_empty);
        mProgressBar.setVisibility(GONE);
    }

    public void setOnRetryListener(RetryListener listener) {
        mListener = listener;
    }

    public interface RetryListener {
        void onRetry();
    }
}
