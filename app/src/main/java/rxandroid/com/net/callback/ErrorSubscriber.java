package rxandroid.com.net.callback;

import rx.Subscriber;
import rxandroid.com.net.exception.ResponeThrowable;
import rxandroid.com.utils.L;

public abstract class ErrorSubscriber<T> extends Subscriber<T> {
    @Override
    public void onError(Throwable e) {
        L.e("错误信息:"+e.getMessage());
        if(e instanceof ResponeThrowable){
            onError((ResponeThrowable)e);
        }else{

            onError(new ResponeThrowable(e,1000));
        }
    }
    /**
     * 错误回调
     */
    protected abstract void onError(ResponeThrowable ex);
}

