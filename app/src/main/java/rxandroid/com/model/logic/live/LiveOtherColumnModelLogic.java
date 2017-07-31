package rxandroid.com.model.logic.live;

import android.content.Context;

import java.util.List;

import rx.Observable;
import rxandroid.com.model.logic.live.bean.LiveOtherColumn;
import rxandroid.com.presenter.live.interfaces.LiveOtherColumnContract;

/**
 * Created on 2017/7/10.
 * Author by Aaron.Wang
 */

public class LiveOtherColumnModelLogic implements LiveOtherColumnContract.Model {
    @Override
    public Observable<List<LiveOtherColumn>> getModelLiveOtherColumn(Context context) {
        return null;
    }
}
