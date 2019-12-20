package com.example.hongligs.ninegridimage;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hongligs.chatdemo.DimenUtil;


public class NineGridImageView extends ViewGroup implements ViewGroup.OnHierarchyChangeListener {

    private NineGridImageAdapter mAdapter;
    private OnItemClickListener mOnItemClickListener;
    private SimpleWeakObjectPool<View> mRecycledViewPool;
    private SparseArray<ImageView> mItemsViews;
    private int mRows;
    private int mColumns;
    private int mSingleWidth;
    private int mSingleHeight;
    private int mChildWidth;
    private int mChildHeight;
    private int mImageSpacing;

    public NineGridImageView(Context context) {
        super(context);
        init(context);
    }

    public NineGridImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NineGridImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOnHierarchyChangeListener(this);
        mRecycledViewPool = new SimpleWeakObjectPool<>(5);
        mImageSpacing = (int) DimenUtil.dp2Px(context,5);
    }

    public void setAdapter(NineGridImageAdapter adapter) {
        if (adapter == null || adapter.getCount() <= 0) {
            removeAllViews();
            return;
        }
        if (mItemsViews == null) {
            mItemsViews = new SparseArray<>();
        } else {
            mItemsViews.clear();
        }
        mAdapter = adapter;
        int oldCount = getChildCount();
        int newCount = adapter.getCount();
        initMatrix(newCount);
        removeScrapViews(oldCount, newCount);
        initChildrenViews(adapter);
        requestLayout();
    }

    private void initChildrenViews(NineGridImageAdapter adapter) {
        int existChildViewCount = getChildCount();
        int neededChildCount = adapter.getCount();
        // 简单的回收机制, 为ListView/RecyclerView做优化
        for (int i = 0; i < neededChildCount; i++) {
            View convertView = i < existChildViewCount? getChildAt(i) : null;
            if (convertView == null) {
                convertView = mRecycledViewPool.get();
                View child = adapter.getView(i, convertView);
                addViewInLayout(child, i, child.getLayoutParams(), true);
                mItemsViews.put(i, (ImageView)child);
            } else {
                adapter.getView(i, convertView);
                mItemsViews.put(i, (ImageView)convertView);
            }
        }
    }

    private void removeScrapViews(int oldCount, int newCount) {
        if (newCount < oldCount) {
            removeViewsInLayout(newCount, oldCount - newCount);
        }
    }

    public SparseArray<ImageView> getImageViewGroup() {
        return mItemsViews;
    }

    private void initMatrix(int length) {
        if (length <= 3) {
            mRows = 1;
            mColumns = length;
        } else if (length <= 6) {
            mRows = 2;
            mColumns = 3;
            if (length == 4) {
                mColumns = 2;
            }
        } else {
            mRows = 3;
            mColumns = 3;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        if (childCount <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        if ((mRows == 0 || mColumns == 0) && mAdapter == null) {
            initMatrix(childCount);
        }

        int minWidth = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int width = resolveSizeAndState(minWidth, widthMeasureSpec, 1);
        int availableWidth = width - getPaddingLeft() - getPaddingRight();
        if (childCount <= 1) {
            if (mSingleWidth == 0) {
                mChildWidth = availableWidth * 3 / 4;
            } else {
                mChildWidth = mSingleWidth;
            }
            if (mSingleHeight == 0) {
                mChildHeight = mChildWidth;
            } else {
                mChildHeight = mSingleHeight;
            }
        } else {
            mChildWidth = (availableWidth - mImageSpacing * (mColumns - 1)) / 3;
            mChildHeight = mChildWidth;
        }
        int height = mChildHeight * mRows + mImageSpacing * (mRows - 1);
        setMeasuredDimension(width, height + getPaddingTop() + getPaddingBottom());
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildren();
    }

    private void layoutChildren() {
        if (mRows <= 0 || mColumns <= 0) {
            return;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final ImageView view = (ImageView)getChildAt(i);
            int row = i / mColumns;
            int column = i % mColumns;
            int left = (mChildWidth + mImageSpacing) * column + getPaddingLeft();
            int top = (mChildHeight + mImageSpacing) * row + getPaddingTop();
            int right = left + mChildWidth;
            int bottom = top + mChildHeight;
            view.layout(left, top, right, bottom);
            final int position = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position, view);
                    }
                }
            });
        }
    }

    /**
     * Called when a new child is added to a parent view.
     *
     * @param parent the view in which a child was added
     * @param child  the new child view added in the hierarchy
     */
    @Override
    public void onChildViewAdded(View parent, View child) {

    }

    /**
     * Called when a child is removed from a parent view.
     *
     * @param parent the view from which the child was removed
     * @param child  the child removed from the hierarchy
     */
    @Override
    public void onChildViewRemoved(View parent, View child) {
        mRecycledViewPool.put(child);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }
}
