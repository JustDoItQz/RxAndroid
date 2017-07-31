package rxandroid.com.model.logic.home;

import android.content.Context;

import java.util.List;

import rx.Observable;
import rxandroid.com.api.home.HomeApi;
import rxandroid.com.base.BaseView;
import rxandroid.com.model.logic.ParamsMapUtils;
import rxandroid.com.model.logic.home.bean.HomeRecommendHotCate;
import rxandroid.com.net.http.HttpUtils;
import rxandroid.com.net.transformer.DefaultTransformer;
import rxandroid.com.presenter.home.interfaces.HomeCateContract;

/**
 * Created on 2017/7/6.
 * Author by Aaron.Wang
 */

public class HomeCateModelLogic implements HomeCateContract.Model {
    @Override
    public Observable<List<HomeRecommendHotCate>> getHomeCate(Context context, String identification) {
        return HttpUtils.getInstance(context)
                .setLoadDiskCache(true)
                .getRetofitClinet()
                .builder(HomeApi.class)
                .getHomeCate(ParamsMapUtils.getHomeCate(identification))
                .compose(new DefaultTransformer<List<HomeRecommendHotCate>>()) ;

    }
}
