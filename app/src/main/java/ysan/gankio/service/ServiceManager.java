package ysan.gankio.service;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YSAN on 2017/1/3 10:57
 */
public class ServiceManager {
    private static ServiceManager mServiceManager;
    public GankioService mService;

    private ServiceManager() {
        initRetrofit();
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mService = retrofit.create(GankioService.class);
    }

    public static ServiceManager getInstance() {
        if (mServiceManager == null) {
            synchronized (ServiceManager.class) {
                if (mServiceManager == null) {
                    mServiceManager = new ServiceManager();
                }
            }
        }
        return mServiceManager;
    }
}
