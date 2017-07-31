package rxandroid.com.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rxandroid.com.model.logic.ContractProxy;

/**
 * Created on 2017/6/13.
 * Author by Aaron.Wang
 */

public abstract class BaseFragment<M extends BaseModel,P extends BasePresenter> extends RxFragment {

    protected Unbinder unbinder ;
    protected View rootView ;
    private Context mContext ;
    private boolean isViewPrepared ; //标识fragment视图已经初始化完毕
    private boolean hasFetchData ;//标识已经触发过懒加载数据

    //定义Presenter
    protected  P mPresenter ;

    //获得布局资源文件
    protected abstract int getLayoutId() ;

    //初始化数据
    protected abstract void onInitView(Bundle bundle) ;

    //初始化事件Event
    protected abstract void onEvent() ;

    //获得抽取接口Model对象
    protected abstract BaseView getViewImpl() ;

    //获得抽取接口Model对象
    protected Class getModelClazz(){
        return (Class<M>) ContractProxy.getModelClazz(getClass(),0) ;
    }

    //获得抽取接口Presenter对象
    protected Class getPresenterClazz(){
        return (Class<P>) ContractProxy.getPresenterClazz(getClass(),1) ;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (rootView!=null){
            ViewGroup parent = (ViewGroup) rootView.getParent() ;
            if (parent!=null){
                parent.removeView(rootView);
            }
            return rootView ;
        }
        if (getLayoutId()!=0){
            rootView = inflater.inflate(getLayoutId(),container,false) ;
        }else{
            rootView = super.onCreateView(inflater,container,savedInstanceState) ;
        }
        unbinder = ButterKnife.bind(this,rootView) ;
        bindMVP();
        onInitView(savedInstanceState);
        return rootView ;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            lazyFetchDataIfPrepared();
        }
    }

    /**
     *  获取presenter实例
     * @param
     * @return
     */
    private void bindMVP(){
        if (getPresenterClazz()!=null){
            mPresenter =  getPresenterImpl() ;
            mPresenter.mContext = getActivity() ;
            bindVM();

        }
    }
    private <T> T getPresenterImpl(){
        return ContractProxy.getInstance().presenter(getPresenterClazz()) ;
    }

    private void bindVM(){
        if (mPresenter!=null&&!mPresenter.isViewBind()&&getModelClazz()!=null&&getViewImpl()!=null){
            ContractProxy.getInstance().bindModel(getModelClazz(),mPresenter) ;
            ContractProxy.getInstance().bindView(getViewImpl(),mPresenter) ;
            mPresenter.mContext = getActivity() ;
        }
    }

    @Override
    public void onStart() {
        if (mPresenter==null){
            bindMVP();
        }
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity() ;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewPrepared = true ;
        lazyFetchDataIfPrepared();
        if (mPresenter==null){
            bindMVP();
        }
    }

    /**
     *  进行懒加载
     * @param
     * @return
     */
    private void lazyFetchDataIfPrepared(){
        //用户可见fragment&&没有加载过数据&&视图已经准备完毕
        if (getUserVisibleHint()&&!hasFetchData&&isViewPrepared){
            hasFetchData = true ;
            lazyFetchData();
        }
    }
    /**
     *  懒加载的方式获取数据，仅在满足fragment可见和视图已经准备好的时间调用一次
     * @param
     * @return
     */
    protected abstract void lazyFetchData() ;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
        if (mPresenter!=null){
            ContractProxy.getInstance().unbindView(getViewImpl(),mPresenter);
            ContractProxy.getInstance().unbindModel(getModelClazz(),mPresenter);
        }
    }
}
