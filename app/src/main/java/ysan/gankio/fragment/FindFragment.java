package ysan.gankio.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

import ysan.gankio.R;
import ysan.gankio.presenter.FindPresenter;
import ysan.gankio.view.FindView;

/**
 * Created by YSAN on 2017/1/6 18:52
 */
public class FindFragment
        extends MvpFragment<FindView, FindPresenter>
        implements FindView, SwipeRefreshLayout.OnRefreshListener {


    private RecyclerView mRecycle;
    private SwipeRefreshLayout mFindSwipe;
    private Banner mBanner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecycle = (RecyclerView) view.findViewById(R.id.activity_swipe_recycler);
        mFindSwipe = (SwipeRefreshLayout) view.findViewById(R.id.fragment_find_swipe);
        mBanner = (Banner) view.findViewById(R.id.banner);

        initEvent();
        initData();
    }

    private void initEvent() {
        mFindSwipe.setOnRefreshListener(this);
    }

    private void initData() {
        presenter.initialize();
    }

    @Override
    public FindPresenter createPresenter() {
        return new FindPresenter(this);
    }


    @Override
    public void onRefresh() {
        presenter.onRefreshData();
    }

    @Override
    public void onLoading() {
        mFindSwipe.setRefreshing(true);
    }

    @Override
    public void onLoadCompleted() {
        Log.i(">>>", "onLoadCompleted 初始化完成");
        mFindSwipe.setRefreshing(false);
    }

    @Override
    public View inflateLayout(ViewGroup parent, boolean b) {
        return null;
    }

    @Override
    public void setImageLoader(List<String> imageUrl) {
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(imageUrl);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    public class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }
}
