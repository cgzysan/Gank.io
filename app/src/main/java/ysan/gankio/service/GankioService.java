package ysan.gankio.service;


import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import ysan.gankio.bean.BaseModel;
import ysan.gankio.bean.Welfare;

/**
 * Created by YSAN on 2017/1/1 12:57
 */
public interface GankioService {
    @GET("api/data/福利/{count}/{page}")
    Observable<BaseModel<List<Welfare>>> getWelfare(
            @Path("count") int count,
            @Path("page") int page
    );
}
