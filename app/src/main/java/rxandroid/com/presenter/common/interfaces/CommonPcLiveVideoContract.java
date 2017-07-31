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

public interface CommonPcLiveVideoContract {

    interface  View extends BaseView{
        void getViewPcLiveVideoInfo(OldLiveVideoInfo oldLiveVideoInfo) ;
    }
    interface Model extends BaseModel{
        Request getModelPcLiveVideoInfo(Context context,String room_id) ;
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getPresenterPcLiveVideoInfo(String room_id) ;
    }
}
