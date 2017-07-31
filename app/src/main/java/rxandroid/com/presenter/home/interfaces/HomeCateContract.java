package rxandroid.com.presenter.home.interfaces;


import android.content.Context;

import java.util.List;

import rx.Observable;
import rxandroid.com.base.BaseModel;
import rxandroid.com.base.BasePresenter;
import rxandroid.com.base.BaseView;
import rxandroid.com.model.logic.home.bean.HomeRecommendHotCate;

/**
 * Created on 2017/7/6.
 * Author by Aaron.Wang
 */

public interface HomeCateContract {
    interface View extends BaseView{
        void getOtherList(List<HomeRecommendHotCate> homeCates);
        void getOtherListRefresh(List<HomeRecommendHotCate> homeCates);
    }
    interface Model extends BaseModel{
        Observable<List<HomeRecommendHotCate>> getHomeCate(Context context, String identification);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void  getHomeCate(String identification);
        public  abstract  void getHomeCateRefresh(String identification);
    }
}
