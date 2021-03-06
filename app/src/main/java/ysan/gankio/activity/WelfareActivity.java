package ysan.gankio.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ysan.gankio.R;
import ysan.gankio.presenter.WelfarePresenter;
import ysan.gankio.view.WelfareView;
import ysan.gankio.widget.SwipeRecyclerView;

/**
 * Created by YSAN on 2016/12/31 18:59
 */

public class WelfareActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, WelfareView {

    private Toolbar mToolbar;
    private SwipeRecyclerView mSr;
    private WelfarePresenter mWelfarePresenter;
    private GridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare);

        if (mWelfarePresenter == null) {
            mWelfarePresenter = new WelfarePresenter(this);
        }
        initView();
        initEvent();
        initData();
    }

    private void initData() {
        mWelfarePresenter.initialize();
    }


    private void initEvent() {
        mSr.setOnRefreshListener(mOnRefreshListener);
    }

    private void initView() {
        /**toolbar配置*/
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Gank.io");
        mToolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        mToolbar.inflateMenu(R.menu.menu_home);

        mSr = (SwipeRecyclerView) findViewById(R.id.activity_swipe_recycler);
        /**设置布局管理器*/
        mLayoutManager = new GridLayoutManager(this, 3);
        mSr.setLayoutManager(mLayoutManager);
        /**设置adapter*/
        mWelfarePresenter.setAdapter(mSr);
    }

    @Override
    public void onLoading() {
        mSr.mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onLoadCompleted() {
        mSr.mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public View inflateLayout(ViewGroup parent, boolean b) {
        return LayoutInflater.from(WelfareActivity.this).inflate(R.layout.activity_recycler_item, parent, false);
    }

    @Override
    public void skipActivity(String url) {
        Intent intent = new Intent(WelfareActivity.this, BitmapActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    @Override
    public int findLastPosition() {
        return mLayoutManager.findLastVisibleItemPosition();
    }

    private SwipeRecyclerView.OnRefreshListener mOnRefreshListener = new SwipeRecyclerView.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mWelfarePresenter.onRefreshData();
        }
    };

    /**
     * toolbar菜单的监听
     *
     * @param item 菜单的item
     * @return
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_more:
                Toast.makeText(this, "点击更多", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
