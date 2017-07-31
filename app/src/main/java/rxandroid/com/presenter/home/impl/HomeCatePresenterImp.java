package rxandroid.com.presenter.home.impl;

import java.util.List;

import rxandroid.com.base.BaseModel;
import rxandroid.com.model.logic.home.bean.HomeRecommendHotCate;
import rxandroid.com.net.callback.RxSubscriber;
import rxandroid.com.net.exception.ResponeThrowable;
import rxandroid.com.presenter.home.interfaces.HomeCateContract;

/**
 * Created on 2017/7/6.
 * Author by Aaron.Wang
 */

public class HomeCatePresenterImp extends HomeCateContract.Presenter implements BaseModel {

    /**
     *  导航栏+栏目列表
     * @param
     * @return
     */

    @Override
    public void getHomeCate(String identification) {

        addSubscribe(mModel.getHomeCate(mContext,identification).subscribe(new RxSubscriber<List<HomeRecommendHotCate>>() {
            @Override
            public void onSuccess(List<HomeRecommendHotCate> homeRecommendHotCates) {
                mView.getOtherList(homeRecommendHotCates);
            }

            @Override
            protected void onError(ResponeThrowable ex) {
                mView.showErrorWithStatus(ex.message);
            }
        }));
    }

    /**
     *  刷新导航栏栏目列表
     * @param
     * @return
     */

    @Override
    public void getHomeCateRefresh(String identification) {

        addSubscribe(mModel.getHomeCate(mContext,identification).subscribe(new RxSubscriber<List<HomeRecommendHotCate>>() {
            @Override
            public void onSuccess(List<HomeRecommendHotCate> homeRecommendHotCates) {
                mView.getOtherListRefresh(homeRecommendHotCates);
            }

            @Override
            protected void onError(ResponeThrowable ex) {

                mView.showErrorWithStatus(ex.message);
            }
        }));
    }
}
