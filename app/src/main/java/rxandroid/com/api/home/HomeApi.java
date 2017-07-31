package rxandroid.com.api.home;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;
import rxandroid.com.model.logic.home.bean.HomeCarousel;
import rxandroid.com.model.logic.home.bean.HomeCateList;
import rxandroid.com.model.logic.home.bean.HomeColumnMoreAllList;
import rxandroid.com.model.logic.home.bean.HomeColumnMoreOtherList;
import rxandroid.com.model.logic.home.bean.HomeColumnMoreTwoCate;
import rxandroid.com.model.logic.home.bean.HomeFaceScoreColumn;
import rxandroid.com.model.logic.home.bean.HomeHotColumn;
import rxandroid.com.model.logic.home.bean.HomeRecommendHotCate;
import rxandroid.com.net.response.HttpResponse;

import static rxandroid.com.api.NetWorkApi.getCarousel;
import static rxandroid.com.api.NetWorkApi.getHomeCate;
import static rxandroid.com.api.NetWorkApi.getHomeCateList;
import static rxandroid.com.api.NetWorkApi.getHomeColumnMoreAllList;
import static rxandroid.com.api.NetWorkApi.getHomeColumnMoreCate;
import static rxandroid.com.api.NetWorkApi.getHomeColumnMoreOtherList;
import static rxandroid.com.api.NetWorkApi.getHomeFaceScoreColumn;
import static rxandroid.com.api.NetWorkApi.getHomeHotColumn;
import static rxandroid.com.api.NetWorkApi.getHomeRecommendHotCate;

/**
 * Created on 2017/6/28.
 * Author by Aaron.Wang
 */

public interface HomeApi {
    /**
     * 首页分类列表
     *
     * @return
     */
    @GET(getHomeCateList)
    Observable<HttpResponse<List<HomeCateList>>> getHomeCateList(@QueryMap Map<String, String> params);

    /**
     * 首页 列表详情页
     *
     * @return
     */
    @GET(getHomeCate)
    Observable<HttpResponse<List<HomeRecommendHotCate>>> getHomeCate(@QueryMap Map<String, String> params);

    /**
     * 首页   推荐轮播图
     *
     * @return
     */
    @GET(getCarousel)
    Observable<HttpResponse<List<HomeCarousel>>> getCarousel(@QueryMap Map<String, String> params);

    /**
     * 推荐---最热
     *
     * @return
     */
    @GET(getHomeHotColumn)
    Observable<HttpResponse<List<HomeHotColumn>>> getHotColumn(@QueryMap Map<String, String> params);

    /**
     * 推荐---颜值
     *
     * @return
     */
    @GET(getHomeFaceScoreColumn)
    Observable<HttpResponse<List<HomeFaceScoreColumn>>> getFaceScoreColumn(@QueryMap Map<String, String> params);

    /**
     * 推荐---热门 种类
     *
     * @return
     */
    @GET(getHomeRecommendHotCate)
    Observable<HttpResponse<List<HomeRecommendHotCate>>> getHotCate(@QueryMap Map<String, String> params);


    /**
     * 栏目 更多   --二级分类列表
     *
     * @return
     */
    @GET(getHomeColumnMoreCate)
    Observable<HttpResponse<List<HomeColumnMoreTwoCate>>> getHomeColumnMoreCate(@QueryMap Map<String, String> params);

    /**
     * 栏目 更多   --其他列表
     *
     * @return
     */
    @GET(getHomeColumnMoreOtherList)
    Observable<HttpResponse<List<HomeColumnMoreOtherList>>> getHomeColumnMoreOtherList(@QueryMap Map<String, String> params);

    /**
     * 栏目 更多   --全部列表
     *
     * @return
     */
    @GET(getHomeColumnMoreAllList + "{cate_id}")
    Observable<HttpResponse<List<HomeColumnMoreAllList>>> getHomeColumnMoreAllList(@Path("cate_id") String cate_id, @QueryMap Map<String, String> params);



}
