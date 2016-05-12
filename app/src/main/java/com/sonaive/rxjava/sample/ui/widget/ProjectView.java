package com.sonaive.rxjava.sample.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sonaive.rxjava.sample.R;
import com.sonaive.rxjava.sample.utils.DensityUtils;

/**
 *
 * Created by liutao on 9/18/15.
 */
public class ProjectView extends ViewGroup {

    private SimpleDraweeView mImage;
    private TextView mTitle;
    private TextView mCreators;
    private TextView mExpectationTitle;
    private TextView mExpectation;
    private TextView mPeriod;
    private TextView mMoney;
    private TextView mPercent;
    private ProgressBar mProgressBar;
    private ImageView mStatus;
    private View mBottomDivider;

    public ProjectView(Context context) {
        this(context, null);
    }

    public ProjectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        mImage = new SimpleDraweeView(context);
        LayoutParams params = new LayoutParams((int) (98 * density), (int) (130 * density));
        mImage.setPadding((int) (10 * density), (int) (10 * density), (int) (10 * density), (int) (10 * density));
        mImage.setLayoutParams(params);
        mImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addView(mImage);

        mTitle = new TextView(context);
        LayoutParams titleParams = new LayoutParams(-2, -2);
        mTitle.setPadding(0, (int) (12 * density), 0, 0);
        mTitle.setLayoutParams(titleParams);
        mTitle.setTextColor(getResources().getColor(R.color.body_text_2));
        mTitle.setTextSize(16);
        addView(mTitle);

        mCreators = new TextView(context);
        LayoutParams creatorsParams = new LayoutParams(-2, -2);
        mCreators.setPadding(0, (int) (7 * density), 0, 0);
        mCreators.setLayoutParams(creatorsParams);
        mCreators.setSingleLine();
        mCreators.setEllipsize(TextUtils.TruncateAt.END);
        mCreators.setTextColor(getResources().getColor(R.color.body_text_3));
        mCreators.setTextSize(12);
        addView(mCreators);

        mExpectationTitle = new TextView(context);
        LayoutParams expectationTitleParams = new LayoutParams(-2, -2);
        mExpectationTitle.setPadding(0, (int) (12 * density), 0, 0);
        mExpectationTitle.setLayoutParams(expectationTitleParams);
        mExpectationTitle.setTextColor(getResources().getColor(R.color.body_text_3));
        mExpectationTitle.setTextSize(11);
        mExpectationTitle.setText("预期年化");
        addView(mExpectationTitle);

        mExpectation = new TextView(context);
        LayoutParams expectationParams = new LayoutParams(-2, -2);
        mExpectation.setPadding((int) (6 * density), (int) (6 * density), 0, 0);
        mExpectation.setLayoutParams(expectationParams);
        mExpectation.setTextColor(getResources().getColor(R.color.red_3));
        mExpectation.setTextSize(18);
        addView(mExpectation);

        mPeriod = new TextView(context);
        LayoutParams periodParams = new LayoutParams(-2, -2);
        mPeriod.setPadding(0, (int) (12 * density), (int) (10 * density), 0);
        mPeriod.setLayoutParams(periodParams);
        mPeriod.setTextColor(getResources().getColor(R.color.body_text_3));
        mPeriod.setTextSize(11);
        addView(mPeriod);

        mMoney = new TextView(context);
        LayoutParams moneyParams = new LayoutParams(-2, -2);
        mMoney.setPadding(0, (int) (12 * density), 0, 0);
        mMoney.setLayoutParams(moneyParams);
        mMoney.setTextColor(getResources().getColor(R.color.body_text_3));
        mMoney.setTextSize(10);
        addView(mMoney);

        mPercent = new TextView(context);
        LayoutParams percentParams = new LayoutParams(-2, -2);
        mPercent.setPadding((int) (6 * density), (int) (12 * density), (int) (10 * density), 0);
        mPercent.setLayoutParams(percentParams);
        mPercent.setTextColor(getResources().getColor(R.color.body_text_3));
        mPercent.setTextSize(10);
        addView(mPercent);

        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        LayoutParams progressBarParams = new LayoutParams(-1, (int) (16 * density));
        mProgressBar.setPadding(0, (int) (2 * density), (int) (10 * density), (int) (10 * density));
        mProgressBar.setLayoutParams(progressBarParams);
        mProgressBar.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.progress_bar_layer, null));
        addView(mProgressBar);

        mStatus = new ImageView(context);
        LayoutParams statusParams = new LayoutParams(-2, -2);
        mStatus.setLayoutParams(statusParams);
        addView(mStatus);

        mBottomDivider = new View(context);
        LayoutParams bottomDividerParams = new LayoutParams(-1, (int) DensityUtils.dp2px(getResources(), 10));
        mBottomDivider.setLayoutParams(bottomDividerParams);
        mBottomDivider.setBackgroundColor(getResources().getColor(R.color.grey_2));
        addView(mBottomDivider);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthConstraints = getPaddingLeft() + getPaddingRight();
        int heightConstraints = getPaddingTop() + getPaddingBottom();

        // 1. Measure image
        measureChildWithMargins(
                mImage,
                widthMeasureSpec,
                widthConstraints,
                heightMeasureSpec,
                heightConstraints
        );
        // 2. Update constraints
        widthConstraints += mImage.getMeasuredWidth();
        // 3. Measure title
        measureChildWithMargins(
                mTitle,
                widthMeasureSpec,
                widthConstraints,
                heightMeasureSpec,
                heightConstraints
        );
        // 4. Update constraints
        heightConstraints += mTitle.getMeasuredHeight();
        // 5. Measure creators
        measureChildWithMargins(
                mCreators,
                widthMeasureSpec,
                widthConstraints,
                heightMeasureSpec,
                heightConstraints
        );
        // 6. Update constraints
        heightConstraints += mCreators.getMeasuredHeight();
        // 7. Measure expectation title
        measureChildWithMargins(
                mExpectationTitle,
                widthMeasureSpec,
                widthConstraints,
                heightMeasureSpec,
                heightConstraints
        );
        // 8. Update constraints
        widthConstraints += mExpectationTitle.getMeasuredWidth();
        // 9. Measure expectation
        measureChildWithMargins(
                mExpectation,
                widthMeasureSpec,
                widthConstraints,
                heightMeasureSpec,
                heightConstraints
        );
        // 10. Update constraints
        widthConstraints += mExpectation.getMeasuredWidth();
        // 11. Measure period
        measureChildWithMargins(
                mPeriod,
                widthMeasureSpec,
                widthConstraints,
                heightMeasureSpec,
                heightConstraints
        );
        // 12. Update constraints
        widthConstraints = mImage.getMeasuredWidth() + getPaddingLeft() + getPaddingRight();
        heightConstraints += mExpectationTitle.getMeasuredHeight();
        // 13. Measure money
        measureChildWithMargins(
                mPeriod,
                widthMeasureSpec,
                widthConstraints,
                heightMeasureSpec,
                heightConstraints
        );
        // 14. Update constraints
        widthConstraints += mPeriod.getMeasuredWidth();
        // 15. Measure money
        measureChildWithMargins(
                mMoney,
                widthMeasureSpec,
                widthConstraints,
                heightMeasureSpec,
                heightConstraints
        );
        // 16. Update constraints
        widthConstraints += mMoney.getMeasuredWidth();
        // 17. Measure money
        measureChildWithMargins(
                mPercent,
                widthMeasureSpec,
                widthConstraints,
                heightMeasureSpec,
                heightConstraints
        );
        // 18. Update constraints
        widthConstraints = mImage.getMeasuredWidth() + getPaddingLeft() + getPaddingRight();
        heightConstraints += mPercent.getMeasuredHeight();
        // 19. Measure money
        measureChildWithMargins(
                mProgressBar,
                widthMeasureSpec,
                widthConstraints,
                heightMeasureSpec,
                heightConstraints
        );
        // 20. Update constraints
        widthConstraints = getPaddingLeft() + getPaddingRight();
        heightConstraints = getPaddingTop() + getPaddingBottom() + mImage.getMeasuredHeight();
        // 21.Measure bottom divider
        measureChildWithMargins(
                mBottomDivider,
                widthMeasureSpec,
                widthConstraints,
                heightMeasureSpec,
                heightConstraints
        );
        // 22. Update constraints
        widthConstraints = getPaddingLeft() + getPaddingRight();
        heightConstraints = getPaddingTop() + getPaddingBottom();
        // 23.Measure status
        measureChildWithMargins(
                mStatus,
                widthMeasureSpec,
                widthConstraints,
                heightMeasureSpec,
                heightConstraints
        );
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int totalWidth = getMeasuredWidth();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int temp;

        LayoutParams imageMargins = (LayoutParams) mImage.getLayoutParams();
        int left = paddingLeft + imageMargins.leftMargin;
        int top = paddingTop + imageMargins.topMargin;
        int right = left + mImage.getMeasuredWidth();
        int bottom = top + mImage.getMeasuredHeight();
        mImage.layout(left, top, right, bottom);

        LayoutParams margins = (LayoutParams) mTitle.getLayoutParams();
        left += mImage.getMeasuredWidth() + imageMargins.rightMargin;
        top += margins.topMargin;
        right = left + mTitle.getMeasuredWidth();
        bottom = top + mTitle.getMeasuredHeight();
        mTitle.layout(left, top, right, bottom);

        margins = (LayoutParams) mCreators.getLayoutParams();
        top = bottom + margins.topMargin;
        right = left + mCreators.getMeasuredWidth();
        bottom = top + mCreators.getMeasuredHeight();
        temp = bottom;
        mCreators.layout(left, top, right, bottom);

        margins = (LayoutParams) mExpectationTitle.getLayoutParams();
        top = bottom + margins.topMargin;
        right = left + mExpectationTitle.getMeasuredWidth();
        bottom = top + mExpectationTitle.getMeasuredHeight();
        mExpectationTitle.layout(left, top, right, bottom);

        margins = (LayoutParams) mExpectation.getLayoutParams();
        top = margins.topMargin + temp;
        left = right + margins.leftMargin;
        right = left + mExpectation.getMeasuredWidth();
        bottom = top + mExpectation.getMeasuredHeight();
        mExpectation.layout(left, top, right, bottom);

        margins = (LayoutParams) mPeriod.getLayoutParams();
        right = totalWidth - getPaddingRight() - margins.rightMargin;
        left = right - mPeriod.getMeasuredWidth();
        top = temp + margins.topMargin;
        bottom = top + mPeriod.getMeasuredHeight();
        mPeriod.layout(left, top, right, bottom);

        margins = (LayoutParams) mMoney.getLayoutParams();
        left = mImage.getMeasuredWidth() + paddingLeft + imageMargins.rightMargin + imageMargins.leftMargin;
        top = bottom + margins.topMargin;
        right = left + mMoney.getMeasuredWidth();
        bottom = top + mMoney.getMeasuredHeight();
        mMoney.layout(left, top, right, bottom);

        margins = (LayoutParams) mPercent.getLayoutParams();
        right = totalWidth - getPaddingRight() - margins.rightMargin;
        left = right - mPercent.getMeasuredWidth();
        bottom = top + mPercent.getMeasuredHeight();
        mPercent.layout(left, top, right, bottom);

        margins = (LayoutParams) mProgressBar.getLayoutParams();
        left = mImage.getMeasuredWidth() + imageMargins.rightMargin + imageMargins.leftMargin;
        top = bottom + margins.topMargin;
        right = getMeasuredWidth() - margins.rightMargin;
        bottom = top + mProgressBar.getMeasuredHeight();
        mProgressBar.layout(left, top, right, bottom);

        left = 0;
        top = imageMargins.topMargin + imageMargins.bottomMargin + mImage.getMeasuredHeight();
        right = getMeasuredWidth();
        bottom = top + mBottomDivider.getMeasuredHeight();
        mBottomDivider.layout(left, top, right, bottom);

        right = totalWidth - getPaddingRight();
        left = right - mStatus.getMeasuredWidth();
        top = paddingTop;
        bottom = mStatus.getMeasuredHeight();
        mStatus.layout(left, top, right, bottom);
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

    public static class LayoutParams extends MarginLayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ProjectView.LayoutParams(getContext(), attrs);
    }

    public SimpleDraweeView getImage() {
        return mImage;
    }

    public TextView getTitle() {
        return mTitle;
    }

    public TextView getCreators() {
        return mCreators;
    }

    public TextView getExpectation() {
        return mExpectation;
    }

    public TextView getPeriod() {
        return mPeriod;
    }

    public TextView getMoney() {
        return mMoney;
    }

    public TextView getPercent() {
        return mPercent;
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    public void setStatus(String status) {
        if (status.equals("financing")) {
            mStatus.setImageResource(R.drawable.status_financing);
        } else if (status.equals("reviewing")) {
            mStatus.setImageResource(R.drawable.status_reviewing);
        } else if (status.equals("repaying")) {
            mStatus.setImageResource(R.drawable.status_repaying);
        }
    }

    public void showBottomDivider(boolean isShow) {
        if (isShow) {
            mBottomDivider.setVisibility(VISIBLE);
        } else {
            mBottomDivider.setVisibility(GONE);
        }
    }
}
