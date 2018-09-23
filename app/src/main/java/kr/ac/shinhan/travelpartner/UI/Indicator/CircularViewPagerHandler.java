package kr.ac.shinhan.travelpartner.UI.Indicator;

import android.support.v4.view.ViewPager;

public class CircularViewPagerHandler implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private int mCurrentPosition;
    private int mScrollState;
    CircleAnimIndicator circleAnimIndicator;

    public CircularViewPagerHandler(final ViewPager viewPager, CircleAnimIndicator circleAnimIndicator) {
        mViewPager = viewPager;
        this.circleAnimIndicator = circleAnimIndicator;
    }

    public void onPageSelected(final int position) {
        mCurrentPosition = position;
        circleAnimIndicator.selectDot(position);
    }

    public void onPageScrollStateChanged(final int state) {
        handleScrollState(state);
        mScrollState = state;
    }

    private void handleScrollState(final int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            setNextItemIfNeeded();
        }
    }

    private void setNextItemIfNeeded() {
        if (!isScrollStateSettling()) {
            handleSetNextItem();
        }
    }

    private boolean isScrollStateSettling() {
        return mScrollState == ViewPager.SCROLL_STATE_SETTLING;
    }

    private void handleSetNextItem() {
        final int lastPosition = mViewPager.getAdapter().getCount() - 1;
        if (mCurrentPosition == 0) {
            mViewPager.setCurrentItem(lastPosition, false);
        } else if (mCurrentPosition == lastPosition) {
            mViewPager.setCurrentItem(0, false);
        }
    }

    public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
    }
}