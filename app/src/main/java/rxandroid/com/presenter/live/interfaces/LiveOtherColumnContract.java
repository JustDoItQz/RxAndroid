package rxandroid.com.presenter.live.interfaces;

import android.content.Context;

import java.util.List;

import rx.Observable;
import rxandroid.com.base.BaseModel;
import rxandroid.com.base.BasePresenter;
import rxandroid.com.base.BaseView;
import rxandroid.com.model.logic.live.bean.LiveOtherColumn;

/**
 * Created on 2017/7/10.
 * Author by Aaron.Wang
 */

public interface LiveOtherColumnContract {
    interface View extends BaseView{
        void getViewLiveOtherColumn(List<LiveOtherColumn> mLiveOtherColumns) ;
    }
    interface Model extends BaseModel{
        Observable<List<LiveOtherColumn>> getModelLiveOtherColumn(Context context) ;
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        //获取直播其他栏目
        public abstract void getPresenterLiveOtherColumn() ;
    }
}
