package rxandroid.com.view.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rxandroid.com.R;
import rxandroid.com.base.BaseFragment;
import rxandroid.com.base.BaseView;
import rxandroid.com.model.logic.home.HomeCateListModelLogic;
import rxandroid.com.model.logic.home.bean.HomeCateList;
import rxandroid.com.presenter.home.impl.HomeCateListPresenterImp;
import rxandroid.com.presenter.home.interfaces.HomeCateListContract;
import rxandroid.com.ui.popwindow.MoreWindow;
import rxandroid.com.view.map.location.activity.TestMapActivity;

/**
 * Created on 2017/6/28.
 * Author by Aaron.Wang
 */

public class HomeFragment extends BaseFragment<HomeCateListModelLogic,HomeCateListPresenterImp> implements HomeCateListContract.View{

    private MoreWindow moreWindow ;
    @BindView(R.id.img_scanner)
    ImageView imgScanner;

    @Override
    public void showErrorWithStatus(String msg) {

    }

    @Override
    public void getHomeAllList(List<HomeCateList> cateLists) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
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

    //添加点击事件
    @OnClick(R.id.img_scanner)
    public void scanner(View view) {
        showMoreWindow(view);

    }
    Intent intent = null ;
    @OnClick(R.id.img_search)
    public void search(){
        intent = new Intent(getActivity(), TestMapActivity.class) ;
        startActivity(intent);
    }
    @OnClick(R.id.img_history)
    public void history(){
        intent = new Intent(getActivity(),null) ;
    }
    @OnClick(R.id.img_message)
    public void message(){

    }
    private void showMoreWindow(View view) {
        if (null == moreWindow) {
            moreWindow = new MoreWindow(getActivity());
            moreWindow.init();
        }

        moreWindow.showMoreWindow(view,100);
    }
}
