package rxandroid.com.view.user.fragment;

import android.os.Bundle;

import rxandroid.com.R;
import rxandroid.com.base.BaseFragment;
import rxandroid.com.base.BaseView;
import rxandroid.com.model.logic.me.MeModelLogic;
import rxandroid.com.model.logic.me.bean.PersonInfoBean;
import rxandroid.com.presenter.me.impl.MePresenterImpl;
import rxandroid.com.presenter.me.interfaces.MeContract;

/**
 * Created on 2017/7/10.
 * Author by Aaron.Wang
 */

public class UserFragment extends BaseFragment<MeModelLogic,MePresenterImpl> implements MeContract.View {
    @Override
    public void showErrorWithStatus(String msg) {

    }

    @Override
    public void getViewPersonInfo(PersonInfoBean personInfoBean) {

    }

    @Override
    public void showLoginPopWindow() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void onInitView(Bundle bundle) {

    }

    @Override
    protected void onEvent() {

    }

    @Override
    protected BaseView getViewImpl() {
        return null;
    }

    @Override
    protected void lazyFetchData() {

    }
}
