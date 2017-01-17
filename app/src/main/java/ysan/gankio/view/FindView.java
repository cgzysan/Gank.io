package ysan.gankio.view;

import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by YSAN on 2017/1/7 12:04
 */
public interface FindView extends MvpView {
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
     * banner设置图片加载器
     * @param imageUrl
     */
    void setImageLoader(List<String> imageUrl);
}
