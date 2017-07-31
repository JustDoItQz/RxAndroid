package rxandroid.com.model.logic.me;

import android.content.Context;

import rx.Observable;
import rxandroid.com.model.logic.me.bean.PersonInfoBean;
import rxandroid.com.presenter.me.interfaces.MeContract;

/**
 * Created on 2017/7/10.
 * Author by Aaron.Wang
 */

public class MeModelLogic implements MeContract.Model {
    @Override
    public Observable<PersonInfoBean> getModelPersonalInfo(Context context, String userName, String password) {
        return null;
    }
}
