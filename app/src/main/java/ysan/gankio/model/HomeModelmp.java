package ysan.gankio.model;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ysan.gankio.bean.BaseModel;
import ysan.gankio.bean.Welfare;
import ysan.gankio.service.ServiceManager;

/**
 * Created by YSAN on 2017/1/3 11:14
 */
public class HomeModelmp implements HomeModel {
    public static final int REFRESH_DATA = 1;
    public static final int LOADMORE_DATA = 2;
    private HomeOnListener mListener;
    private int page;

    public HomeModelmp(HomeOnListener l) {
        this.mListener = l;
    }

    @Override
    public void initializeDatas() {
        getDatas(REFRESH_DATA);
    }

    @Override
    public void refreshDatas() {
        getDatas(REFRESH_DATA);
    }

    @Override
    public void loadMoreDatas() {
        getDatas(LOADMORE_DATA);
    }

    private void getDatas(final int action) {

        if (action == REFRESH_DATA) {
            page = 1;
        } else {
            page++;
        }

        ServiceManager.getInstance().mService.getWelfare(20, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<Welfare>>>() {
                    @Override
                    public void onStart() {
                        mListener.onStart();
                    }

                    @Override
                    public void onCompleted() {
                        mListener.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.onError(e);
                    }

                    @Override
                    public void onNext(BaseModel<List<Welfare>> listBaseModel) {
                        mListener.onNext(action, listBaseModel);
                    }
                });
    }

    public interface HomeOnListener {
        void onStart();

        void onCompleted();

        void onError(Throwable e);

        void onNext(int action, BaseModel<List<Welfare>> listBaseModel);
    }
}
