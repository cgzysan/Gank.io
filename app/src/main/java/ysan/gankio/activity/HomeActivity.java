package ysan.gankio.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ysan.gankio.R;
import ysan.gankio.bean.BaseModel;
import ysan.gankio.bean.Welfare;
import ysan.gankio.service.GankioService;
import ysan.gankio.widget.SwipeRecyclerView;

public class HomeActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private static final int REFRESH_DATA = 1;
    private static final int LOADMORE_DATA = 2;
    private List<Welfare> mDatas = new ArrayList<>();
    private Toolbar mToolbar;
    private GridLayoutManager mLayoutManager;
    private SwipeRecyclerView mSr;
    private GankioService mGankioService;
    private MyAdapter mMyadapter;
    private int page;
    private int action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initRetrofit();
        initView();
        initEvent();
        initData();
    }

    private void initData() {
        getDatas(REFRESH_DATA);
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mGankioService = retrofit.create(GankioService.class);
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
        mMyadapter = new MyAdapter();
        mSr.setAdapter(mMyadapter);
    }

    class MyAdapter extends SwipeRecyclerView.Adapter<MyAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder vh = new MyViewHolder(LayoutInflater.from(HomeActivity.this).inflate(R.layout.activity_recycler_item, parent, false));
            return vh;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final String url = mDatas.get(position).url;

            Glide.with(holder.mImageView.getContext())
                    .load(url)
                    .centerCrop()
                    .placeholder(R.color.app_primary_dark)
                    .crossFade()
                    .into(holder.mImageView);

            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, BitmapActivity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            if (mDatas == null)
                return 0;
            return mDatas.size();
        }

        @Override
        protected void loadMoreDatas(RecyclerView recyclerView, int dx, int dy) {
            int position = mLayoutManager.findLastVisibleItemPosition();
            int itemCount = mMyadapter.getItemCount();
            if (position + 1 == itemCount) {
                getDatas(LOADMORE_DATA);
            }
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView mImageView;

            public MyViewHolder(View itemView) {
                super(itemView);
                mImageView = (ImageView) itemView.findViewById(R.id.item_imageview);
            }
        }

    }

    private void getDatas(final int action) {

        if (action == REFRESH_DATA) {
            page = 1;
        } else {
            page++;
        }

        mGankioService.getWelfare(50, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<Welfare>>>() {
                    @Override
                    public void onStart() {
                        mSr.mSwipeRefreshLayout.setRefreshing(true);
                    }

                    @Override
                    public void onCompleted() {
                        mSr.mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(">>>", "onError " + e.toString());
                    }

                    @Override
                    public void onNext(BaseModel<List<Welfare>> listBaseModel) {
                        if (action == REFRESH_DATA) {
                            mDatas.clear();
                        }
                        mDatas.addAll(listBaseModel.results);
                        mMyadapter.notifyDataSetChanged();
                    }
                });
    }

    private SwipeRecyclerView.OnRefreshListener mOnRefreshListener = new SwipeRecyclerView.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getDatas(REFRESH_DATA);
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
