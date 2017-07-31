package rxandroid.com.presenter.common.impl;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import rxandroid.com.model.logic.common.bean.OldLiveVideoInfo;
import rxandroid.com.presenter.common.interfaces.CommonPhoneLiveVideoContract;
import rxandroid.com.utils.L;

/**
 * Created by WEQ on 2017/6/12.
 */

public class CommonPhoneLiveVideoPresenterImp extends CommonPhoneLiveVideoContract.Presenter {

    @Override
    public void getPresenterPhoneLiveVideoInfo(String room_id) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .build() ;
        client.newCall(mModel.getModelPhoneLiveVideoInfo(mContext,room_id)).enqueue(new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error",e.getMessage()+"-----") ;
                L.e("错误信息："+e.getMessage());
                mView.showErrorWithStatus(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string().toString() ;
                Log.e("onResponse",json) ;
                try{
                    JSONObject jsonObject = new JSONObject(json) ;
                    if (jsonObject.getInt("error")==0){
                        Gson gson = new Gson() ;
                        OldLiveVideoInfo mLiveVideoInfo = gson.fromJson(json, OldLiveVideoInfo.class) ;
                        mView.getViewPhoneLiveVideoInfo(mLiveVideoInfo);
                    }else{
                        mView.showErrorWithStatus("获取数据失败！");
                    }
                }catch (Exception e){
                    e.printStackTrace();

                }

            }
        });
    }
}
