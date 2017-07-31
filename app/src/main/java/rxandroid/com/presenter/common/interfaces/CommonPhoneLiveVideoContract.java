package rxandroid.com.presenter.common.interfaces;

import android.content.Context;

import okhttp3.Request;
import rxandroid.com.base.BaseModel;
import rxandroid.com.base.BasePresenter;
import rxandroid.com.base.BaseView;
import rxandroid.com.model.logic.common.bean.OldLiveVideoInfo;

/**
 * Created by WEQ on 2017/6/12.
 */

public interface CommonPhoneLiveVideoContract {
    interface View extends BaseView{
        void getViewPhoneLiveVideoInfo(OldLiveVideoInfo mLiveVideoInfo) ;
    }
    interface Model extends BaseModel{
        Request getModelPhoneLiveVideoInfo(Context context,String room_id) ;
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getPresenterPhoneLiveVideoInfo(String room_id) ;
    }
}
