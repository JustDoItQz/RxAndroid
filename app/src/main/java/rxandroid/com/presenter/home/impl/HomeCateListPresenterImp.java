package rxandroid.com.presenter.home.impl;

import java.util.List;

import rxandroid.com.model.logic.home.bean.HomeCateList;
import rxandroid.com.net.callback.RxSubscriber;
import rxandroid.com.net.exception.ResponeThrowable;
import rxandroid.com.presenter.home.interfaces.HomeCateListContract;

/**
 * Created on 2017/6/28.
 * Author by Aaron.Wang
 */

public class HomeCateListPresenterImp extends HomeCateListContract.Presenter {
    @Override
    public void getHomeCateList() {
        addSubscribe(mModel.getHomeCateList(mContext).subscribe(new RxSubscriber<List<HomeCateList>>() {
            @Override
            public void onSuccess(List<HomeCateList> homeCateLists) {
                mView.getHomeAllList(homeCateLists);
            }
            @Override
            protected void onError(ResponeThrowable ex) {
                mView.showErrorWithStatus(ex.message);
            }
        }));
    }
}
