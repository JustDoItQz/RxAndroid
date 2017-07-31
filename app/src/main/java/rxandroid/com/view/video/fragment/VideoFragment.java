package rxandroid.com.view.video.fragment;

import android.os.Bundle;

import java.util.List;

import rxandroid.com.R;
import rxandroid.com.base.BaseFragment;
import rxandroid.com.base.BaseView;
import rxandroid.com.model.logic.video.VideoCateListLogic;
import rxandroid.com.model.logic.video.bean.VideoCateList;
import rxandroid.com.presenter.video.impl.VideoCateListPresenterImpl;
import rxandroid.com.presenter.video.interfaces.VideoAllCateListContract;

/**
 * Created on 2017/7/10.
 * Author by Aaron.Wang
 */

public class VideoFragment extends BaseFragment<VideoCateListLogic,VideoCateListPresenterImpl> implements VideoAllCateListContract.View {
    @Override
    public void showErrorWithStatus(String msg) {

    }

    @Override
    public void getViewVideoAllCate(List<VideoCateList> cateLists) {

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
