package ysan.gankio.model;

import android.util.Log;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ysan.gankio.bean.FindBean;
import ysan.gankio.bean.FindModel;
import ysan.gankio.service.ServiceManager;

/**
 * Created by YSAN on 2017/1/7 13:59
 */
public class FindModelmp implements BaseModel {
    private FindOnListener mListener;

    public FindModelmp(FindOnListener l) {
        this.mListener = l;
    }

    @Override
    public void initializeDatas() {
        ServiceManager.getInstance().mService.getFindForDay(2017, 1, 12)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FindModel<FindBean>>() {
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
                    public void onNext(FindModel<FindBean> findBeanFindModel) {
                        mListener.onNext(1, findBeanFindModel);
                    }
                });
    }

    @Override
    public void refreshDatas() {
        ServiceManager.getInstance().mService.getFindForDay(2017, 1, 4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FindModel<FindBean>>() {
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
                    public void onNext(FindModel<FindBean> findBeanFindModel) {
                        mListener.onNext(1, findBeanFindModel);
                    }
                });
    }

    public interface FindOnListener {
        void onStart();

        void onCompleted();

        void onError(Throwable e);

        void onNext(int action, FindModel<FindBean> findBeanFindModel);
    }

}
