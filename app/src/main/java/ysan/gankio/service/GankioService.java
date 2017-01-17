package ysan.gankio.service;


import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import ysan.gankio.bean.FindModel;
import ysan.gankio.bean.DataModel;
import ysan.gankio.bean.FindBean;
import ysan.gankio.bean.Welfare;

/**
 * Created by YSAN on 2017/1/1 12:57
 */
public interface GankioService {
    @GET("api/data/福利/{count}/{page}")
    Observable<DataModel<List<Welfare>>> getWelfare(
            @Path("count") int count,
            @Path("page") int page
    );

    @GET("api/day/{year}/{month}/{day}")
    Observable<FindModel<FindBean>> getFindForDay(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day
    );

}
