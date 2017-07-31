package rxandroid.com.presenter.video.interfaces;

import android.content.Context;

import java.util.List;

import rx.Observable;
import rxandroid.com.base.BaseModel;
import rxandroid.com.base.BasePresenter;
import rxandroid.com.base.BaseView;
import rxandroid.com.model.logic.video.bean.VideoCateList;

/**
 * Created on 2017/7/10.
 * Author by Aaron.Wang
 */

public interface VideoAllCateListContract {
    interface View extends BaseView{
        void getViewVideoAllCate(List<VideoCateList> cateLists) ;
    }
    interface Model extends BaseModel{
        Observable<List<VideoCateList>> getModelVideoAllCate(Context context) ;
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getPresenterVideoCateList() ;
    }
}
