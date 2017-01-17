package ysan.gankio.presenter;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import ysan.gankio.bean.ExtraBean;
import ysan.gankio.bean.FindBean;
import ysan.gankio.bean.FindModel;
import ysan.gankio.model.FindModelmp;
import ysan.gankio.view.FindView;

/**
 * Created by YSAN on 2017/1/7 12:07
 */
public class FindPresenter
        extends MvpBasePresenter<FindView>
        implements FindModelmp.FindOnListener {

    private FindBean mFindData;
    private FindView mFindView;
    private FindModelmp mFindModel;
    private List<String> imageUrl = new ArrayList<>();
    private Banner mBanner;

    public FindPresenter(FindView findView) {
        this.mFindView = findView;
        this.mFindModel = new FindModelmp(this);
    }

    @Override
    public void onStart() {
        mFindView.onLoading();
    }

    @Override
    public void onCompleted() {
        mFindView.onLoadCompleted();
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(int action, FindModel<FindBean> findBeanFindModel) {
        mFindData = findBeanFindModel.results;

        List<ExtraBean> android = mFindData.Android;
        for (ExtraBean a : android) {
            List<String> images = a.images;
            imageUrl.addAll(images);
        }

        mFindView.setImageLoader(imageUrl);
    }

    public void initialize() {
        mFindModel.initializeDatas();
    }

    public void onRefreshData() {
        mFindModel.refreshDatas();
    }


    public void getBanner(Banner banner) {
        this.mBanner = banner;
    }
}
