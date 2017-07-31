package rxandroid.com.model.logic.home;

import android.content.Context;

import java.util.List;

import rx.Observable;
import rxandroid.com.api.home.HomeApi;
import rxandroid.com.model.logic.ParamsMapUtils;
import rxandroid.com.model.logic.home.bean.HomeCateList;
import rxandroid.com.net.http.HttpUtils;
import rxandroid.com.net.transformer.DefaultTransformer;
import rxandroid.com.presenter.home.interfaces.HomeCateListContract;

/**
 * Created on 2017/6/28.
 * Author by Aaron.Wang
 */

public class HomeCateListModelLogic implements HomeCateListContract.Model {
    @Override
    public Observable<List<HomeCateList>> getHomeCateList(Context context) {
        return HttpUtils.getInstance(context)
                .setLoadDiskCache(true)
                .getRetofitClinet()
                .builder(HomeApi.class)
                .getHomeCateList(ParamsMapUtils.getDefaultParams())
                .compose(new DefaultTransformer<List<HomeCateList>>());
    }
}
