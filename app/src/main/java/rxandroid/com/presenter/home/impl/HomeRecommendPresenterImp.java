package rxandroid.com.presenter.home.impl;

import java.util.List;

import rxandroid.com.model.logic.home.bean.HomeCarousel;
import rxandroid.com.model.logic.home.bean.HomeFaceScoreColumn;
import rxandroid.com.model.logic.home.bean.HomeHotColumn;
import rxandroid.com.model.logic.home.bean.HomeRecommendHotCate;
import rxandroid.com.net.callback.RxSubscriber;
import rxandroid.com.net.exception.ResponeThrowable;
import rxandroid.com.presenter.home.interfaces.HomeRecommendContract;

/**
 * Created on 2017/6/28.
 * Author by Aaron.Wang
 */

public class HomeRecommendPresenterImp extends HomeRecommendContract.Presenter {
    /**
     *  轮播图
     * @param
     * @return
     */

    @Override
    public void getPresenterCarousel() {
        addSubscribe(mModel.getModelCarousel(mContext).subscribe(new RxSubscriber<List<HomeCarousel>>() {
            @Override
            public void onSuccess(List<HomeCarousel> homeCarousels) {
                mView.getViewCarousel(homeCarousels);
            }

            @Override
            protected void onError(ResponeThrowable ex) {
                mView.showErrorWithStatus(ex.message);
            }
        }));
    }

    @Override
    public void getPresenterHotColumn() {
        addSubscribe(mModel.getModelHotColumn(mContext).subscribe(new RxSubscriber<List<HomeHotColumn>>() {
            @Override
            public void onSuccess(List<HomeHotColumn> homeHotColumns) {
                mView.getViewHotColumn(homeHotColumns);
            }

            @Override
            protected void onError(ResponeThrowable ex) {
                mView.showErrorWithStatus(ex.message);
            }
        }));
    }

    @Override
    public void getPresenterFaceScoreColumn(int offset, int limit) {
        addSubscribe(mModel.getModelFaceScoreColumn(mContext,offset,limit).subscribe(new RxSubscriber<List<HomeFaceScoreColumn>>() {
            @Override
            public void onSuccess(List<HomeFaceScoreColumn> homeFaceScoreColumns) {
                mView.getViewFaceScoreColumn(homeFaceScoreColumns);
            }

            @Override
            protected void onError(ResponeThrowable ex) {
                mView.showErrorWithStatus(ex.message);
            }
        }));
    }

    @Override
    public void getPresenterHotCate() {
        addSubscribe(mModel.getModelHotCate(mContext).subscribe(new RxSubscriber<List<HomeRecommendHotCate>>() {
            @Override
            public void onSuccess(List<HomeRecommendHotCate> homeRecommendHotCates) {
                mView.getViewHotCate(homeRecommendHotCates);
            }
            @Override
            protected void onError(ResponeThrowable ex) {
                mView.showErrorWithStatus(ex.message);
            }
        }));
    }
}
