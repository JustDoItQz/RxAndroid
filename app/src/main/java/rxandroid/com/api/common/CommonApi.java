package rxandroid.com.api.common;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rxandroid.com.model.logic.common.bean.LiveVideoInfo;
import rxandroid.com.net.response.HttpResponse;

import static rxandroid.com.api.NetWorkApi.getLiveVideo;

/**
 * Created by WEQ on 2017/6/12.
 */

public interface CommonApi {
    /*
     *直播播放页
     * @return
     */
    @GET(getLiveVideo+"{room_id}")
    Call<HttpResponse<LiveVideoInfo>> getLiveVideo(@Path("room_id")String room_id, @QueryMap Map<String,String> params) ;

}
