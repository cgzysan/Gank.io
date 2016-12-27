package ysan.gankio.service;


import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import ysan.gankio.bean.BaseModel;
import ysan.gankio.bean.Welfare;

/**
 * 创建者     YSAN
 * 创建时间   2016/12/9 15:36
 * 描述	      http://gank.io/api/data/数据类型/请求个数/第几页
 * <p/>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public interface GankioService {
    @GET("api/data/福利/{count}/{page}")
    Observable<BaseModel<List<Welfare>>> getWelfare(
            @Path("count") int count,
            @Path("page") int page
    );
}
