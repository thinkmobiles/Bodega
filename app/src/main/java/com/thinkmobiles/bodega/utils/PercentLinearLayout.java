package com.thinkmobiles.bodega.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.percent.PercentLayoutHelper;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by illia on 27.10.15.
 */
public final class PercentLinearLayout extends LinearLayout {

    private PercentLayoutHelper mPercentHelper;

    public PercentLinearLayout(Context context) {
        super(context);
        init();
    }

    public PercentLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PercentLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PercentLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPercentHelper = new PercentLayoutHelper(this);
    }

    @Override
    protected final void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mPercentHelper.adjustChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mPercentHelper.handleMeasuredStateTooSmall()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected final void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mPercentHelper.restoreOriginalParams();
    }

    @Override
    public final LayoutParams generateLayoutParams(final AttributeSet _attrs) {
        return new LayoutParams(getContext(), _attrs);
    }

    public static final class LayoutParams extends LinearLayout.LayoutParams implements PercentLayoutHelper.PercentLayoutParams {

        private final PercentLayoutHelper.PercentLayoutInfo mPercentLayoutInfo;

        public LayoutParams(final Context _context, final AttributeSet _attrs) {
            super(_context, _attrs);
            mPercentLayoutInfo = PercentLayoutHelper.getPercentLayoutInfo(_context, _attrs);
        }

        @Override
        protected final void setBaseAttributes(final TypedArray _array, final int _widthAttr, final int _heightAttr) {
            PercentLayoutHelper.fetchWidthAndHeight(this, _array, _widthAttr, _heightAttr);
        }

        @Override
        public final PercentLayoutHelper.PercentLayoutInfo getPercentLayoutInfo() {
            return mPercentLayoutInfo;
        }
    }
}
