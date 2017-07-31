package rxandroid.com.presenter.home.interfaces;

import android.content.Context;

import java.util.List;

import rx.Observable;
import rxandroid.com.base.BaseModel;
import rxandroid.com.base.BasePresenter;
import rxandroid.com.base.BaseView;
import rxandroid.com.model.logic.home.bean.HomeCarousel;
import rxandroid.com.model.logic.home.bean.HomeFaceScoreColumn;
import rxandroid.com.model.logic.home.bean.HomeHotColumn;
import rxandroid.com.model.logic.home.bean.HomeRecommendHotCate;

/**
 * Created on 2017/6/28.
 * Author by Aaron.Wang
 */

public interface HomeRecommendContract {
    interface View extends BaseView {
        //        轮播图
        void getViewCarousel(List<HomeCarousel> mHomeCarousel);

        //        最热栏目
        void getViewHotColumn(List<HomeHotColumn> mHomeHotColumn);

        //        颜值栏目
        void getViewFaceScoreColumn(List<HomeFaceScoreColumn> homeFaceScoreColumns);

        //       热门栏目
        void getViewHotCate(List<HomeRecommendHotCate> homeRecommendHotCates);
    }

    interface Model extends BaseModel {
        Observable<List<HomeCarousel>> getModelCarousel(Context context);

        Observable<List<HomeHotColumn>> getModelHotColumn(Context context);

        Observable<List<HomeFaceScoreColumn>> getModelFaceScoreColumn(Context context, int offset, int limit  );

        Observable<List<HomeRecommendHotCate>> getModelHotCate(Context context);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        //        轮播
        public abstract void getPresenterCarousel();

        //        最热栏目
        public abstract void getPresenterHotColumn();

        public abstract void getPresenterFaceScoreColumn(int offset,int limit );

        public abstract void getPresenterHotCate();

    }

}
