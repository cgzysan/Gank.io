package ysan.gankio.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import ysan.gankio.R;

/**
 * 创建者     YSAN
 * 创建时间   2016/12/14 20:52
 * 描述	      ${TODO}
 * <p/>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class SwipeRecyclerView extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {

    public SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private OnRefreshListener listener;
    private Adapter adapter;

    /**
     * 在代码调用的时候使用
     */
    public SwipeRecyclerView(Context context) {
        super(context);
    }

    /**
     * 在布局文件中使用的时候调用，比两个参数的多一个样式文件
     */
    public SwipeRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 在布局文件中使用的时候调用
     */
    public SwipeRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initEvent();
    }

    private void initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.swipe_recyclerview, this, true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    /**
     * RecyclerView的滚动监听
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            adapter.loadMoreDatas(recyclerView, dx, dy);
        }
    };

    /**
     * SwipeRefreshLayout的下拉刷新监听事件
     */
    @Override
    public void onRefresh() {
        this.listener.onRefresh();
    }

    public void setOnRefreshListener(OnRefreshListener l) {
        this.listener = l;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        mRecyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    public static abstract class Adapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{
        protected abstract void loadMoreDatas(RecyclerView recyclerView, int dx, int dy);
    }
}
