package rxandroid.com.view.live.fragment;

import android.os.Bundle;

import java.util.List;

import rxandroid.com.R;
import rxandroid.com.base.BaseFragment;
import rxandroid.com.base.BaseView;
import rxandroid.com.model.logic.live.LiveOtherColumnModelLogic;
import rxandroid.com.model.logic.live.bean.LiveOtherColumn;
import rxandroid.com.presenter.live.impl.LiveOtherColumnPresenterImp;
import rxandroid.com.presenter.live.interfaces.LiveOtherColumnContract;

/**
 * Created on 2017/7/10.
 * Author by Aaron.Wang
 */

public class LiveFragment extends BaseFragment<LiveOtherColumnModelLogic,LiveOtherColumnPresenterImp> implements LiveOtherColumnContract.View {


    @Override
    public void showErrorWithStatus(String msg) {

    }

    @Override
    public void getViewLiveOtherColumn(List<LiveOtherColumn> mLiveOtherColumns) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live;
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
