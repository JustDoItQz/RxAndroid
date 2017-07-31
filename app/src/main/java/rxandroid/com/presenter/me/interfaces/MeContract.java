package rxandroid.com.presenter.me.interfaces;

import android.content.Context;

import rx.Observable;
import rxandroid.com.base.BaseModel;
import rxandroid.com.base.BasePresenter;
import rxandroid.com.base.BaseView;
import rxandroid.com.model.logic.me.bean.PersonInfoBean;

/**
 * Created on 2017/7/10.
 * Author by Aaron.Wang
 */

public interface MeContract {
    interface View extends BaseView{
        void getViewPersonInfo(PersonInfoBean personInfoBean) ;
        void showLoginPopWindow() ;
    }
    interface Model extends BaseModel{
        Observable<PersonInfoBean> getModelPersonalInfo(Context context,String userName,String password) ;
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void login() ;
    }
}
