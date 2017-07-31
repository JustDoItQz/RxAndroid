package rxandroid.com.base;

import android.content.Context;
import android.view.View;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import rxandroid.com.model.Model;

/**
 * Created by WEQ on 2017/6/12.
 */

public class BasePresenter<V extends BaseView,M extends BaseModel> implements Presenter<V,M> {
   /*类描述：
   *        1、获取绑定View实例传递到子类中进行调用
   *        2、注销View实例
   *        3、创建Model实例
   *        4、注销Model实例
   *        5、通过RxJava进行绑定activity和fragment生命周期
   *
   * */

    protected Context mContext ;
    protected V mView ;
    protected M mModel ;
    protected CompositeSubscription mCompositeSubscription  ;

    protected void unSubscribe(){
        if(mCompositeSubscription==null){
            mCompositeSubscription.unsubscribe();
        }
    }
    protected void addSubscribe(Subscription subscription){
        if(mCompositeSubscription==null){
            mCompositeSubscription = new CompositeSubscription() ;
        }
        mCompositeSubscription.add(subscription);
    }
    //获取Model实例
    public M getModel(){
        return mModel ;
    }
    //获取View实例
    public V getView(){
        return mView ;
    }

    //获取绑定View实例
    @Override
    public void attachView(V v) {
        this.mView = v ;
    }
    //获取绑定Model实例
    @Override
    public void attachModel(M m) {
        this.mModel = m ;
    }
    //注销View实例
    @Override
    public void detachView() {
        this.mView = null ;
        unSubscribe();

    }
    //注销Model实例
    @Override
    public void detachModel() {
        this.mModel = null ;

    }
    //判断View是否解绑
    public boolean isViewBind(){
        return mView!=null ;
    }
}
