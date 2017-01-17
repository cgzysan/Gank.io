package ysan.gankio.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;

import ysan.gankio.R;
import ysan.gankio.bean.DataModel;
import ysan.gankio.bean.Welfare;
import ysan.gankio.model.WelfareModelmp;
import ysan.gankio.view.WelfareView;
import ysan.gankio.widget.SwipeRecyclerView;

/**
 * Created by YSAN on 2017/1/2 10:28
 */
public class WelfarePresenter extends MvpBasePresenter<WelfareView> implements WelfareModelmp.HomeOnListener {

    private WelfareView mHomeView;
    private List<Welfare> mDatas = new ArrayList<>();
    private WelfareModelmp mWelfareModel;
    private HomeAdapter mAdapter;


    public WelfarePresenter(WelfareView homeView) {
        this.mHomeView = homeView;
        this.mWelfareModel = new WelfareModelmp(this);
    }


    public void initialize(){
        mDatas.clear();
        mWelfareModel.initializeDatas();
    }

    public void onRefreshData() {
        mDatas.clear();
        mWelfareModel.refreshDatas();
    }


    @Override
    public void onStart() {
        mHomeView.onLoading();
    }

    @Override
    public void onCompleted() {
        mHomeView.onLoadCompleted();
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(int action, DataModel<List<Welfare>> listDataModel) {
        if (action == WelfareModelmp.REFRESH_DATA) {
            mDatas.clear();
        }
        mDatas.addAll(listDataModel.results);
        mAdapter.notifyDataSetChanged();
    }

    public void setAdapter(SwipeRecyclerView sr) {
        mAdapter = new HomeAdapter();
        sr.setAdapter(mAdapter);
    }


    public class HomeAdapter extends SwipeRecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder vh = new MyViewHolder(mHomeView.inflateLayout(parent, false));
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
                    mHomeView.skipActivity(url);
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
            int position = mHomeView.findLastPosition();
            int itemCount = getItemCount();
            if (position + 1 == itemCount) {
                mWelfareModel.loadMoreDatas();
            }
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public ImageView mImageView;

            public MyViewHolder(View itemView) {
                super(itemView);
                mImageView = (ImageView) itemView.findViewById(R.id.item_imageview);
            }
        }

    }
}
