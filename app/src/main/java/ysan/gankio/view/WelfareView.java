package ysan.gankio.view;

import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by YSAN on 2017/1/2 10:11
 */
public interface WelfareView extends MvpView {
    /**
     * 加载中
     */
    void onLoading();

    /**
     * 加载完成
     */
    void onLoadCompleted();

    /**
     * 加载布局
     */
    View inflateLayout(ViewGroup parent, boolean b);

    /**
     * 跳转
     */
    void skipActivity(String url);

    /**
     * 获取最后一个可见item位置
     */
    int findLastPosition();
}
