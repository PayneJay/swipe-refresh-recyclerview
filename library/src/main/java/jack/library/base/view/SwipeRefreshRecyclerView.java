package jack.library.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import jack.library.R;
import jack.library.base.BaseAdapter;


/**
 * ================================================
 * description:
 * ================================================
 * package_name: com.waka.view
 * author: PayneJay
 * date: 2018/1/23.
 */

public class SwipeRefreshRecyclerView extends LinearLayout {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout mLoadMoreView;
    private TextView mLoadingView;
    private TextView mNoMoreView;

    private boolean isDown;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private int visibleThreshold = 1;
    private boolean refreshAble, loadMoreAble;
    private OnLoadMoreListener onLoadMoreListener;
    private OnRefreshListener onRefreshListener;

    public SwipeRefreshRecyclerView(Context context) {
        this(context, null);
    }

    public SwipeRefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeRefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RefreshRecyclerView);
        refreshAble = typedArray.getBoolean(R.styleable.RefreshRecyclerView_refresh_able, true);
        loadMoreAble = typedArray.getBoolean(R.styleable.RefreshRecyclerView_load_more_able, true);
        setLoadMoreAble(loadMoreAble);
        typedArray.recycle();

        initView(context);
    }

    private void initView(Context context) {
        View view = inflate(context, R.layout.swipe_refresh_layout, this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.waka_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mLoadMoreView = (LinearLayout) view.findViewById(R.id.load_more_view);
        mLoadingView = (TextView) view.findViewById(R.id.loading_view);
        mNoMoreView = (TextView) view.findViewById(R.id.no_more_view);
        mSwipeRefreshLayout.setEnabled(refreshAble);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        if (isDown) {
                            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                            if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    isDown = true;
                } else {
                    isDown = false;
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (onRefreshListener != null) {
                    onRefreshListener.onRefresh();
                }
            }
        });


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    /**
     * 设置是否可刷新
     *
     * @param refreshAble
     * @return
     */
    public SwipeRefreshRecyclerView setRefreshAble(boolean refreshAble) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setEnabled(refreshAble);
        }
        return this;
    }

    /**
     * 设置是否可加载更多
     *
     * @param loadMoreAble
     * @return
     */
    public SwipeRefreshRecyclerView setLoadMoreAble(boolean loadMoreAble) {
        loading = !loadMoreAble;
        return this;
    }

    /**
     * 设置加载更多文案
     *
     * @param text
     * @param colorId
     * @return
     */
    public SwipeRefreshRecyclerView setLoadingText(String text, int colorId) {
        mLoadingView.setText(text);
        mLoadingView.setTextColor(colorId);
        return this;
    }

    /**
     * 设置没有更多数据文案
     *
     * @param text
     * @param colorId
     * @return
     */
    public SwipeRefreshRecyclerView setNoMoreText(String text, int colorId) {
        mNoMoreView.setText(text);
        mNoMoreView.setTextColor(colorId);
        return this;
    }

    /**
     * 设置分割线
     *
     * @param decor
     * @return
     */
    public SwipeRefreshRecyclerView addItemDecoration(RecyclerView.ItemDecoration decor) {
        if (mRecyclerView != null) {
            mRecyclerView.addItemDecoration(decor);
        }
        return this;
    }

    /**
     * 设置分割线
     *
     * @param decor
     * @param index
     * @return
     */
    public SwipeRefreshRecyclerView addItemDecoration(RecyclerView.ItemDecoration decor, int index) {
        if (mRecyclerView != null) {
            mRecyclerView.addItemDecoration(decor, index);
        }
        return this;
    }

    /**
     * 设置刷新动画颜色
     *
     * @param colors
     * @return
     */
    public SwipeRefreshRecyclerView setColorSchemeColors(int... colors) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeColors(colors);
        }
        return this;
    }

    /**
     * 设置手指在屏幕下拉多少距离会触发下拉刷新
     *
     * @param distance
     * @return
     */
    public SwipeRefreshRecyclerView setDistanceToTriggerSync(int distance) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setDistanceToTriggerSync(distance);
        }
        return this;
    }

    /**
     * 设定下拉圆圈的背景
     *
     * @param color
     * @return
     */
    public SwipeRefreshRecyclerView setProgressBackgroundColorSchemeColor(int color) {
        if (mSwipeRefreshLayout != null) {
            try {
                mSwipeRefreshLayout.setProgressBackgroundColor(color);
            } catch (Exception e) {
                Log.d("Exception", e.getMessage());
            }
        }
        return this;
    }

    /**
     * 设置圆圈的大小
     *
     * @param size
     * @return
     */
    public SwipeRefreshRecyclerView setSize(int size) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setSize(size);
        }
        return this;
    }

    /**
     * 设置layoutManager
     *
     * @param layoutManager
     * @return
     */
    public SwipeRefreshRecyclerView setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(layoutManager);
        }
        return this;
    }

    /**
     * 设置item动画
     *
     * @param animator
     * @return
     */
    public SwipeRefreshRecyclerView setItemAnimator(RecyclerView.ItemAnimator animator) {
        if (mRecyclerView != null) {
            mRecyclerView.setItemAnimator(animator);
        }
        return this;
    }

    public SwipeRefreshRecyclerView setAdapter(BaseAdapter mAdapter) {
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mAdapter);
        }
        return this;
    }

    public int getTotalItemCount() {
        if (mRecyclerView == null) {
            return 0;
        }
        return mRecyclerView.getAdapter().getItemCount();
    }

    public void clear() {
        mLoadMoreView.setVisibility(View.GONE);
        mNoMoreView.setVisibility(View.GONE);
    }

    /**
     * 加载完成后调用
     */
    public void setLoaded() {
        loading = false;
        if (mLoadMoreView != null) {
            mLoadMoreView.post(new Runnable() {
                @Override
                public void run() {
                    mLoadMoreView.setVisibility(View.GONE);
                }
            });
        }
    }

    public void showLoading() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    public void hideLoading() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    public void showNoMore() {
        if (mLoadMoreView != null) {
            mLoadMoreView.post(new Runnable() {
                @Override
                public void run() {
                    mLoadMoreView.setVisibility(View.GONE);
                    mNoMoreView.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    public void openLoadMore() {
        if (mLoadMoreView != null) {
            mLoadMoreView.post(new Runnable() {
                @Override
                public void run() {
                    mLoadMoreView.setVisibility(View.VISIBLE);
                    mNoMoreView.setVisibility(View.GONE);
                }
            });
        }
    }

    /**
     * 设置加载更多的回调
     *
     * @param onLoadMoreListener
     */
    public SwipeRefreshRecyclerView setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        return this;
    }


    /**
     * 设置下拉刷新回调
     *
     * @param onRefreshListener
     * @return
     */
    public SwipeRefreshRecyclerView setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
        return this;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public interface OnRefreshListener {
        void onRefresh();
    }
}
