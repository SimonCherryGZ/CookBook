package com.simoncherry.cookbook;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by xl on 2016/11/22.
 */

public class FlingNestedScrollView extends ViewGroup implements NestedScrollingParent, NestedScrollingChild, ScrollingView {

    private NestedScrollingParentHelper parentHelper;
    private NestedScrollingChildHelper childHelper;

    //手势判断
    private GestureDetectorCompat mGestureDetector;
    //惯性滑动计算
    private ScrollerCompat scroller;

    private View nestedScrollingView;
    private OnScrollChangeListener listener;

    private final int[] mScrollConsumed = new int[2];
    private final int[] mNestedOffsets = new int[2];
    private int windowOffsetY = 0;

    private List<View> nestedScrollingChildList = new ArrayList<>();

    private int currentFlingY = 0;


    public FlingNestedScrollView(Context context) {
        this(context, null);
    }

    public FlingNestedScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlingNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOverScrollMode(View.OVER_SCROLL_NEVER);

        parentHelper = new NestedScrollingParentHelper(this);
        childHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);

        scroller = ScrollerCompat.create(context);
        mGestureDetector = new GestureDetectorCompat(getContext(), new MyGestureListener());
    }

    public void setOnScrollChangeListener(OnScrollChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View view = getChildAt(0);
        if (view == null || !(view instanceof ViewGroup)) {
            throw new IllegalArgumentException("must have one child ViewGroup");
        }
        measureChild(view, widthMeasureSpec, heightMeasureSpec);
        ViewGroup viewGroup = (ViewGroup) view;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View view = getChildAt(0);
        if (view == null || !(view instanceof ViewGroup)) {
            throw new IllegalArgumentException("must have one child ViewGroup");
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int parentHeight = getMeasuredHeight();
        int parentTop = viewGroup.getPaddingTop();
        int top = parentTop;
        int width = r - l;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if(child.getVisibility() == View.GONE) continue;
            int childHeight;
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            if (layoutParams.height == LayoutParams.MATCH_PARENT) {
                layoutParams.height = parentHeight;
                child.setLayoutParams(layoutParams);
                childHeight = parentHeight;
            } else {
                childHeight = child.getMeasuredHeight();
            }
            top += layoutParams.topMargin;
            child.layout(0, top, width, top + childHeight);
            top += childHeight + layoutParams.bottomMargin;
        }
        viewGroup.layout(0, parentTop, width, top);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = getChildAt(0);
        if (view == null || !(view instanceof ViewGroup)) {
            throw new IllegalArgumentException("must have one child ViewGroup");
        }
        nestedScrollingChildList.clear();
        ViewGroup viewGroup = (ViewGroup) view;
        getAllNestedChildren(viewGroup);
        Log.i("carpool", "Children count:" + nestedScrollingChildList.size());
        Collections.sort(nestedScrollingChildList, new SortComparator());
    }

    private void onScrollChange(View view, int scroll) {
        if(listener != null) listener.onScrollChange(view, scroll);
    }

    //获取本身滑动高度
    private int getScrollHeight() {
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        int bottom = getChildAt(0).getHeight();
        return bottom - height;
    }

    //获取所有可以滑动的NestedScrollChild
    private void getAllNestedChildren(ViewGroup viewGroup) {
        for (int i = 0, len = viewGroup.getChildCount(); i < len; i++) {
            View child = viewGroup.getChildAt(i);
            if(ViewCompat.isNestedScrollingEnabled(child) && child instanceof ScrollingView) {
                nestedScrollingChildList.add(child);
            } else if(child instanceof ViewGroup) {
                getAllNestedChildren((ViewGroup) child);
            }
        }
    }

    //子view是否显示，不显示则不计算
    private boolean isShownInVertical(View view) {
        if(!view.isShown()) return false;
        int[] position = new int[2];
        view.getLocationOnScreen(position);
        int screenLeft = position[0];
        int screenRight = screenLeft + view.getWidth();
        if(screenRight < getWidth() / 3 || screenLeft > getWidth() / 3 * 2) {
            return false;
        }
        return true;
    }

    //滑动子view，如果子view正在滑动则返回，否则会引起循环调用，nestedChild会“歘”一下就滑到底部
    private void scrollViewYTo(View view, int y) {
        onScrollChange(view, y);
        if(view == nestedScrollingView) return;
        int currentScroll = getViewScrollY(view);
        view.scrollBy(0, y - currentScroll);
    }

    private void scrollViewYBy(View view, int dy) {
        onScrollChange(view, getViewScrollY(view) + dy);
//        if(view == nestedScrollingView) return;
        view.scrollBy(0, dy);
    }

    private void scrollYTo(int dy) {
        onScrollChange(this, dy);
        super.scrollTo(0, dy);
    }

    private void scrollYBy(int dy) {
        int scrollYTo = getScrollY() + dy;
        onScrollChange(this, scrollYTo);
        super.scrollBy(0, dy);
    }

    //获取view相对于root的坐标
    private int getYWithView(View view, View parent) {
        int[] position = new int[2];
        view.getLocationOnScreen(position);
        int viewTop = position[1];
        parent.getLocationOnScreen(position);
        int parentTop = position[1];
        return viewTop - parentTop;
    }

    //获取view相对于root的top
    private int getTopWithView(View view, View parent) {
        int parentY = getYWithView(view, parent);
        return getViewScrollY(parent) + parentY;
    }

    //获取子view当前滑动位置
    private int getViewScrollY(View view) {
        if(view instanceof ScrollingView) {
            return ((ScrollingView)view).computeVerticalScrollOffset();
        } else {
            return view.getScrollY();
        }
    }

    //------------------------------------scrollBy(因为在滑动过程中，scrollingview不知道自己的最大高度，所以不能使用全局坐标！)----------

    //全局滑动，包括子scrollingview
    //true:消费，false：未消费
    private boolean nestedScrollBy(int dy) {
        if(dy == 0) return true;

        int scrollY = getScrollY();
        int scrollMax = getScrollHeight();

        //有正在滑动的子view
        View currentScrollChild = getCurrentScrollChild(dy);
        if(currentScrollChild != null) {
            //如果两者相等，返回false，让其自己消费，以免循环调用
            //按逻辑来说，还应该判断currentScrollChild是否是nestedScrollingView的子控件
            //但是子控件的scrollBy好像并不会再次触发onPreNestedScroll,暂未发现bug
            //所以就先这么写吧
            if(currentScrollChild == nestedScrollingView) {
                onScrollChange(currentScrollChild, getViewScrollY(currentScrollChild) + dy);
                return false;
            } else {
                scrollViewYBy(currentScrollChild, dy);
                return true;
            }
        }

        //将会滑动到子view
        View nextScrollChild = getNextScrollChild(dy);
        if(nextScrollChild != null) {
            scrollYBy(getYWithView(nextScrollChild, this));
            return true;
        }

        //仅滑动自己
        if(dy < 0 && scrollY == 0) return false;
        if(dy > 0 && scrollY == scrollMax) return false;
        if(scrollY + dy <= 0) scrollYTo(0);
        else if(scrollY + dy >= scrollMax) scrollYTo(scrollMax);
        else scrollYBy(dy);
        return true;
    }

    //获取正在滑动的view
    private View getCurrentScrollChild(int dy) {
        for(View child: nestedScrollingChildList) {
            int childY = getYWithView(child, this);
            if(childY == 0 && isShownInVertical(child)) {
                if(canScrollVertically(child, dy)) return child;
            }
        }
        return null;
    }

    //获取下一个将会自滑动的view
    //consumed: index0: parentscroll, index1: viewscroll
    private View getNextScrollChild(int dy) {
        View view = null;
        int viewY = 0;
        for(View child: nestedScrollingChildList) {
            int childY = getYWithView(child, this);
            if(dy > 0 && childY > 0) {
                view = child;
                viewY = childY;
                break;
            } else if(dy < 0 && childY < 0) {
                view  = child;
                viewY = childY;
            }
        }
        if(view == null) return null;
        if(!canScrollVertically(view, dy)) return null;

        if((dy < 0 && dy - viewY < 0) || (dy > 0 && dy - viewY > 0)) {
            return view;
        }
        return null;
    }

    //子View是否能够滑动
    private boolean canScrollVertically(View view, int scrollY) {
        if(scrollY > 0 && ViewCompat.canScrollVertically(view, 1)) {
            return true;
        } else if(scrollY < 0 && ViewCompat.canScrollVertically(view, -1)) {
            return true;
        }
        return false;
    }


    //-----------------------------------scrollBy(end)------------------------------------------

    //惯性滑动
    private void flingY(int velocityY) {
        if (getChildCount() > 0) {
            currentFlingY = 0;
            scroller.fling(0, 0, 0, velocityY, 0, 0, Integer.MIN_VALUE,
                    Integer.MAX_VALUE);
            invalidate();
        }
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            int y = scroller.getCurrY();
            nestedScrollBy(y - currentFlingY);
            currentFlingY = y;
            invalidate();
        }
    }

    @Override
    public void scrollBy(int x, int y) {
        nestedScrollBy(y);
    }

    //---------------------------NestedScrollParent----------------------------------
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.i("carpool", "start");
        if(!nestedScrollingChildList.contains(target)) {
            onFinishInflate();
        }
        nestedScrollingView = target;
        scroller.abortAnimation();
        return (nestedScrollAxes & SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        Log.i("carpool", "accept");
        parentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        Log.i("carpool", "stop");
        if(nestedScrollingView == target) nestedScrollingView = null;
        parentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        boolean isConsumed = nestedScrollBy(dy);
        if(isConsumed) {
            consumed[0] = 0;
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.i("carpool", "fling");
        flingY((int) velocityY);
        return true;
    }

    @Override
    public int getNestedScrollAxes() {
        return parentHelper.getNestedScrollAxes();
    }
    //---------------------------NestedScrollParent(End)-----------------------------


    //---------------------------NestedScrollChild----------------------------------

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        childHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return childHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return childHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        childHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return childHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return childHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return childHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return childHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    //---------------------------NestedScrollChild(End)-----------------------------


    //---------------------------ScrollingView---------------------------
    @Override
    public int computeHorizontalScrollRange() {
        return 0;
    }

    public int computeHorizontalScrollOffset() {
        return 0;
    }

    public int computeHorizontalScrollExtent() {
        return 0;
    }

    public int computeVerticalScrollRange() {
        return getChildAt(0).getHeight();
    }

    public int computeVerticalScrollOffset() {
        return getScrollY();
    }
    public int computeVerticalScrollExtent() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    //--------------------------ScrollingView(End)------------------------------


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        event.offsetLocation(0, windowOffsetY);
        mGestureDetector.onTouchEvent(event);
        if(event.getAction() == MotionEvent.ACTION_UP) {
            windowOffsetY = 0;
            stopNestedScroll();
        }
        return true;
    }

    private class MyGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            scroller.abortAnimation();
            startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
            windowOffsetY = 0;
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int dx = (int) distanceX;
            int dy = (int) distanceY;
            dispatchNestedPreScroll(dx, dy, mScrollConsumed, mNestedOffsets);
            windowOffsetY += mNestedOffsets[1];
            dy -= mScrollConsumed[1];
            if(dy == 0) return true;
            else nestedScrollBy(dy);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(!dispatchNestedPreFling(-velocityX, -velocityY)) {
                dispatchNestedFling(-velocityX, -velocityY, true);
                flingY((int) -velocityY);
            }
            return true;
        }
    }

    public class SortComparator implements Comparator<View> {

        @Override
        public int compare(View lhs, View rhs) {
            return getTopWithView(lhs, FlingNestedScrollView.this) - getTopWithView(rhs, FlingNestedScrollView.this);
        }
    }

    public interface OnScrollChangeListener {
        void onScrollChange(View view, int scroll);
    }
}
