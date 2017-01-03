package ysan.gankio.presenter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ysan.gankio.R;
import ysan.gankio.activity.HomeActivity;
import ysan.gankio.bean.BaseModel;
import ysan.gankio.bean.Welfare;
import ysan.gankio.model.HomeModel;
import ysan.gankio.model.HomeModelmp;
import ysan.gankio.view.HomeView;
import ysan.gankio.widget.SwipeRecyclerView;

/**
 * Created by YSAN on 2017/1/2 10:28
 */
public class HomePresenter implements HomeModelmp.HomeOnListener {

    private HomeView mHomeView;
    private List<Welfare> mDatas = new ArrayList<>();
    private HomeActivity view;
    private HomeModel mHomeModel;
    private HomeAdapter mAdapter;


    public HomePresenter(HomeView homeView) {
        this.mHomeView = homeView;
        this.mHomeModel = new HomeModelmp(this);
    }


    public void initialize(){
        mDatas.clear();
        mHomeModel.initializeDatas();
    }

    public void onRefreshData() {
        mDatas.clear();
        mHomeModel.refreshDatas();
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
    public void onNext(int action, BaseModel<List<Welfare>> listBaseModel) {
        if (action == HomeModelmp.REFRESH_DATA) {
            mDatas.clear();
        }
        mDatas.addAll(listBaseModel.results);
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
                mHomeModel.loadMoreDatas();
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
