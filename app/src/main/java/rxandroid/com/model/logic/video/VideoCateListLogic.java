package rxandroid.com.model.logic.video;

import android.content.Context;

import java.util.List;

import rx.Observable;
import rxandroid.com.model.logic.video.bean.VideoCateList;
import rxandroid.com.presenter.video.interfaces.VideoAllCateListContract;

/**
 * Created on 2017/7/10.
 * Author by Aaron.Wang
 */

public class VideoCateListLogic implements VideoAllCateListContract.Model {
    @Override
    public Observable<List<VideoCateList>> getModelVideoAllCate(Context context) {
        return null;
    }
}
