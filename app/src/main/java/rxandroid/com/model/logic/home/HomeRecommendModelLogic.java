package rxandroid.com.model.logic.home;

import android.content.Context;

import java.util.List;

import rx.Observable;
import rxandroid.com.api.home.HomeApi;
import rxandroid.com.model.logic.ParamsMapUtils;
import rxandroid.com.model.logic.home.bean.HomeCarousel;
import rxandroid.com.model.logic.home.bean.HomeFaceScoreColumn;
import rxandroid.com.model.logic.home.bean.HomeHotColumn;
import rxandroid.com.model.logic.home.bean.HomeRecommendHotCate;
import rxandroid.com.net.http.HttpUtils;
import rxandroid.com.net.transformer.DefaultTransformer;
import rxandroid.com.presenter.home.interfaces.HomeRecommendContract;

/**
 * Created on 2017/6/28.
 * Author by Aaron.Wang
 */

public class HomeRecommendModelLogic implements HomeRecommendContract.Model {

    /**
     *  获取首页轮播图
     * @param
     * @return
     */
    @Override
    public Observable<List<HomeCarousel>> getModelCarousel(Context context) {
        return HttpUtils.getInstance(context)
                .setLoadDiskCache(true)
                .getRetofitClinet()
                .builder(HomeApi.class)
                .getCarousel(ParamsMapUtils.getDefaultParams())
                //进行预处理
                .compose(new DefaultTransformer<List<HomeCarousel>>()) ;
    }

    /**
     *  首页 ---推荐----最热
     * @param
     * @return
     */
    @Override
    public Observable<List<HomeHotColumn>> getModelHotColumn(Context context) {
        return HttpUtils.getInstance(context)
                .setLoadDiskCache(true)
                .getRetofitClinet()
                .builder(HomeApi.class)
                .getHotColumn(ParamsMapUtils.getDefaultParams())
                .compose(new DefaultTransformer<List<HomeHotColumn>>()) ;

    }

    /**
     *  首页 ---推荐----颜值
     * @param
     * @return
     */

    @Override
    public Observable<List<HomeFaceScoreColumn>> getModelFaceScoreColumn(Context context, int offset, int limit) {
        return HttpUtils.getInstance(context)
                .getRetofitClinet()
                .builder(HomeApi.class)
                .getFaceScoreColumn(ParamsMapUtils.getHomeFaceScoreColume(offset,limit))
                //进行预处理
                .compose(new DefaultTransformer<List<HomeFaceScoreColumn>>()) ;
    }
    /**
     *  首页----推荐----热门种类
     * @param
     * @return
     */

    @Override
    public Observable<List<HomeRecommendHotCate>> getModelHotCate(Context context) {
        return HttpUtils.getInstance(context)
                .getRetofitClinet()
                .builder(HomeApi.class)
                .getHomeCate(ParamsMapUtils.getDefaultParams())
                //进行预处理
                .compose(new DefaultTransformer<List<HomeRecommendHotCate>>()) ;

    }
}
