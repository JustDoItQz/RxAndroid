package rxandroid.com.presenter.home.interfaces;

import android.content.Context;

import java.util.List;

import rx.Observable;
import rxandroid.com.base.BaseModel;
import rxandroid.com.base.BasePresenter;
import rxandroid.com.base.BaseView;
import rxandroid.com.model.logic.home.bean.HomeCateList;

/**
 * Created on 2017/6/28.
 * Author by Aaron.Wang
 */

public interface HomeCateListContract {
    interface View extends BaseView {
        void getHomeAllList(List<HomeCateList> cateLists);
    }
    interface  Model extends BaseModel {
        Observable getHomeCateList(Context context);
    }
    abstract class Presenter extends BasePresenter<View,Model> {
        public abstract void  getHomeCateList();
    }
}
