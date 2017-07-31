package rxandroid.com.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rxandroid.com.model.logic.ContractProxy;

/**
 * Created on 2017/6/13.
 * Author by Aaron.Wang
 */

public abstract class BaseActivity<M extends BaseModel,P extends BasePresenter> extends RxAppCompatActivity {

    //定义Presenter
    protected P mPresenter ;

    protected Unbinder unbinder ;

    //获取布局资源文件
    protected abstract int getLayoutId() ;

    //初始化数据
    protected abstract void onInitView(Bundle bundle) ;

    //初始化事件Event
    protected abstract void onEvent() ;

    //获得抽取View对象
    protected abstract BaseView getView() ;

    //获得抽取接口Model对象
    protected Class getModelClazz(){
        return (Class<M>) ContractProxy.getModelClazz(getClass(),0) ;
    }

    //获得抽取接口Presenter对象
    protected Class getPresenterClazz(){
        return (Class<P>) ContractProxy.getPresenterClazz(getClass(),1) ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if (getLayoutId()!=0){
            //设置布局资源文件
            setContentView(getLayoutId());
            //注解绑定
            unbinder = ButterKnife.bind(this) ;
            bindMVP();
            onInitView(savedInstanceState);
            onEvent();

        }
    }
    /**
     *  获取presenter实例
     * @param
     * @return
     */
    private void bindMVP(){
        if (getPresenterClazz()!=null){
            mPresenter = getPresenterImpl() ;
            mPresenter.mContext = this ;
            bindVM();
        }
    }

    private <T> T getPresenterImpl(){
        return ContractProxy.getInstance().presenter(getPresenterClazz()) ;
    }

    @Override
    protected void onStart() {
        if (mPresenter==null){
            bindMVP();
        }
        super.onStart();
    }
    private void bindVM(){
        if (mPresenter!=null&&!mPresenter.isViewBind()&&getModelClazz()!=null&&getView()!=null){
            ContractProxy.getInstance().bindModel(getModelClazz(),mPresenter) ;
            ContractProxy.getInstance().bindView(getView(),mPresenter) ;
            mPresenter.mContext = this ;
        }
    }
    /**
     *  activity销毁
     * @param
     * @return
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
        if (mPresenter!=null){
            ContractProxy.getInstance().unbindView(getView(),mPresenter);
            ContractProxy.getInstance().unbindModel(getModelClazz(),mPresenter);
            mPresenter=null ;
        }
    }
}
