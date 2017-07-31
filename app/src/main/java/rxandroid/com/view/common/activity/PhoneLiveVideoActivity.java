package rxandroid.com.view.common.activity;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.utils.ScreenResolution;
import io.vov.vitamio.widget.VideoView;
import master.flame.danmaku.ui.widget.DanmakuView;
import me.curzbin.library.BottomDialog;
import me.curzbin.library.Item;
import me.curzbin.library.OnItemClickListener;
import rxandroid.com.R;
import rxandroid.com.base.BaseActivity;
import rxandroid.com.base.BaseView;
import rxandroid.com.danmu.utils.DanmuProcess;
import rxandroid.com.model.logic.common.CommonPcLiveVideoModelLogic;
import rxandroid.com.model.logic.common.bean.OldLiveVideoInfo;
import rxandroid.com.presenter.common.impl.CommonPhoneLiveVideoPresenterImp;
import rxandroid.com.presenter.common.interfaces.CommonPhoneLiveVideoContract;
import rxandroid.com.ui.loadplay.LoadingView;

/**
 * Created on 2017/6/16.
 * Author by Aaron.Wang
 */

public class PhoneLiveVideoActivity extends BaseActivity<CommonPcLiveVideoModelLogic,CommonPhoneLiveVideoPresenterImp> implements CommonPhoneLiveVideoContract.View, MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnErrorListener {

    @BindView(R.id.vm_videoview)
    VideoView vmVideoview;
    @BindView(R.id.im_logo)
    ImageView imLogo;
    @BindView(R.id.lv_playloading)
    LoadingView lvPlayloading;
    @BindView(R.id.fl_loading)
    FrameLayout flLoading;
    @BindView(R.id.iv_control_img)
    ImageView ivControlImg;
    @BindView(R.id.tv_control_name)
    TextView tvControlName;
    @BindView(R.id.tv_control)
    TextView tvControl;
    @BindView(R.id.control_center)
    RelativeLayout controlCenter;
    @BindView(R.id.tv_loading_buffer)
    TextView tvLoadingBuffer;
    @BindView(R.id.danmakuView)
    DanmakuView danmakuView;
    @BindView(R.id.img_loading)
    SimpleDraweeView imgLoading;
   // private HomeRecommendHotCate.RoomListEntity mRoomEntity;
    private OldLiveVideoInfo videoInfo;
    private String Room_id;
    private SVProgressHUD svProgressHUD;
    private String imgUrl;

    /**
     * 弹幕
     */
    private DanmuProcess mDanmuProcess;
    /**
     * 音量   亮度
     *
     * @return
     */
    private int mScreenWidth = 0;//屏幕宽度
    private boolean mIsFullScreen = true;//是否为全屏
    private int mShowVolume;//声音
    private int mShowLightness;//亮度
    private int mMaxVolume;//最大声音
    private AudioManager mAudioManager;
    private GestureDetector mGestureDetector;
    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener;
    /**
     * 声音
     */
    public final static int ADD_FLAG = 1;
    /**
     * 亮度
     */
    public final static int SUB_FLAG = -1;

    public static final int HIDE_CONTROL_BAR = 0x02;//隐藏控制条
    public static final int HIDE_TIME = 5000;//隐藏控制条时间
    public static final int SHOW_CENTER_CONTROL = 0x03;//显示中间控制
    public static final int SHOW_CONTROL_TIME = 1000;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                /**
                 *  隐藏top, bottom
                 * @param
                 * @return
                 */
                case HIDE_CONTROL_BAR:

                    break;
                case SHOW_CENTER_CONTROL:
                    if (controlCenter!=null){
                        controlCenter.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    } ;

    @Override
    public void showErrorWithStatus(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                svProgressHUD.showErrorWithStatus("稍等一会，马上开播~~~~~");
            }
        });
    }

    @Override
    public void getViewPhoneLiveVideoInfo(OldLiveVideoInfo mLiveVideoInfo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                videoInfo = mLiveVideoInfo ;
                getViewInfo(mLiveVideoInfo) ;
            }
        });
    }

    /**
     *  获得房间信息
     * @param
     * @return
     */
    private void getViewInfo(OldLiveVideoInfo oldLiveVideoInfo){
        if (oldLiveVideoInfo.getData()!=null){
            String url = oldLiveVideoInfo.getData().getLive_url() ;
            Uri uri = Uri.parse(url) ;
            if (vmVideoview!=null){
                vmVideoview.setVideoURI(uri);
                vmVideoview.setBufferSize(1024*1024*20);
                /**
                 *  设置视频参数质量。参数quality的MediaPlayer常量
                 *  VIDEOQUALITY_LOW(流畅)、VIDEOQUALITY_MEDIUM(普通)、VIDEOQUALITY_HIGH(高质量)
                 * @param
                 * @return
                 */
                vmVideoview.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
                vmVideoview.requestFocus() ;
                vmVideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setPlaybackSpeed(1.0f);
                        flLoading.setVisibility(View.GONE);
                        mHandler.sendEmptyMessageDelayed(HIDE_CONTROL_BAR,HIDE_TIME) ;
                    }
                });
            }
        }
    }

    @Override
    protected int getLayoutId() {
        Vitamio.isInitialized(this) ;
        return R.layout.activity_phonelive_video;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        Room_id = getIntent().getExtras().getString("Room_id") ;
        imgUrl = getIntent().getExtras().getString("Img_Path") ;
        if (imgUrl!=null){
            imgLoading.setImageURI(imgUrl);
        }
        //保持屏幕常亮
        vmVideoview.setKeepScreenOn(true);
        mPresenter.getPresenterPhoneLiveVideoInfo(Room_id);
        svProgressHUD = new SVProgressHUD(this) ;
        //获取屏幕亮度
        Pair<Integer,Integer> screenPair = ScreenResolution.getResolution(this) ;
        mScreenWidth = screenPair.first ;
        //初始化声音和亮度
        initVolumeWithLight() ;

    }
    /**
     *  初始化声音和亮度
     * @param
     * @return
     */
    private void initVolumeWithLight(){
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE) ;
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) ;
        mShowVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) ;
        mShowLightness = getScreenBrightness();
    }
    /**
     *  获取当前屏幕的亮度值
     * @param
     * @return
     */
    private int getScreenBrightness(){

        int screenBrightness = 255 ;
        try{
            screenBrightness = Settings.System.getInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return screenBrightness ;
    }

    @Override
    protected void onEvent() {

        addTouchListener() ;
    }
    /**
     *  添加手势操作
     * @param
     * @return
     */
    private void addTouchListener(){
        //滑动操作
        mSimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (!mIsFullScreen){
                    return false ;
                }
                float x1 = e1.getX() ;
                float absDistanceX = Math.abs(distanceX) ;
                float absDistanceY = Math.abs(distanceY) ;
                //Y方向的距离比X方向大，即 上下 滑动
                if (absDistanceX<absDistanceY){
                    if (distanceY>0){ //向上滑动
                        if (x1>=mScreenWidth*0.65){ //右边调节声音

                            changeVolume(ADD_FLAG) ;
                        }else{//调节亮度
                            changeLightness(ADD_FLAG) ;

                        }
                    }else {//向下滑动
                        if (x1>=mScreenWidth*0.65){
                            changeVolume(SUB_FLAG);
                        }else {
                            changeLightness(SUB_FLAG);
                        }
                    }
                }else {
                    //X方向的距离比Y方向大，即左右滑动
                }
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        } ;
    }

    /**
     *  改变声音
     * @param
     * @return
     */
    private void changeVolume(int flag){
        mShowVolume+=flag ;
        if (mShowVolume>100){
            mShowVolume = 100 ;
        }else if (mShowVolume<0){
            mShowVolume=0 ;
        }
        tvControlName.setText("音量");
        ivControlImg.setImageResource(R.drawable.img_volume);
        tvControl.setText(mShowVolume+"%");
        int tagVolume = mShowVolume*mMaxVolume/100 ;
        //tagVolume:音量绝对值
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,tagVolume,0);
        mHandler.removeMessages(SHOW_CENTER_CONTROL);
        controlCenter.setVisibility(View.VISIBLE);
        mHandler.sendEmptyMessageDelayed(SHOW_CENTER_CONTROL,SHOW_CONTROL_TIME) ;

    }
    /**
     *  改变亮度
     * @param
     * @return
     */
    private void changeLightness(int flag){
        mShowLightness+= flag ;
        if (mShowLightness>255){
            mShowLightness = 255 ;
        }else if (mShowLightness<=0){
            mShowLightness=0 ;
        }
        tvControlName.setText("亮度");
        ivControlImg.setImageResource(R.drawable.img_light);
        tvControl.setText(mShowLightness*100/255+"%");
        WindowManager.LayoutParams lp = getWindow().getAttributes() ;
        lp.screenBrightness = mShowLightness/255f ;
        getWindow().setAttributes(lp);

        mHandler.removeMessages(SHOW_CENTER_CONTROL);
        controlCenter.setVisibility(View.VISIBLE);
        mHandler.sendEmptyMessageDelayed(SHOW_CENTER_CONTROL,SHOW_CONTROL_TIME) ;
    }


    @Override
    protected BaseView getView() {
        return this;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        flLoading.setVisibility(View.VISIBLE);
        if (vmVideoview.isPlaying()){
            vmVideoview.pause();
        }
        tvLoadingBuffer.setText("直播已缓冲"+percent+"%...");

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {

        if (what==MediaPlayer.MEDIA_ERROR_UNKNOWN){
            svProgressHUD.showErrorWithStatus("稍等一会，马上开播~~~~");
        }

        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what){
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (vmVideoview.isPlaying()){
                    vmVideoview.pause();
                }
                mHandler.removeMessages(HIDE_CONTROL_BAR);
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                if (!vmVideoview.isPlaying()){
                    vmVideoview.start();
                }
                mHandler.sendEmptyMessageDelayed(HIDE_CONTROL_BAR,HIDE_TIME) ;
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:

                break;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (vmVideoview!=null){
            vmVideoview.pause();
        }
        if (danmakuView!=null){
            danmakuView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        if (vmVideoview!=null){
            vmVideoview.stopPlayback();
        }
        danmakuView.release();
        mDanmuProcess.finish();
        danmakuView.clear();
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPresenter.getPresenterPhoneLiveVideoInfo(Room_id);
        if (vmVideoview!=null){
            vmVideoview.start();
        }
        if (danmakuView!=null&&mDanmuProcess!=null){
            danmakuView.restart();
            mDanmuProcess.start();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /*分享弹窗*/
    public void share(Context context){

        new BottomDialog(context)
                .title(R.string.title_item)             //设置标题
                .layout(BottomDialog.GRID)              //设置内容layout,默认为线性(LinearLayout)
                .orientation(BottomDialog.VERTICAL)     //设置滑动方向,默认为横向
                .inflateMenu(R.menu.menu_share)         //传人菜单内容
                .itemClick(new OnItemClickListener() {  //设置监听
                    @Override
                    public void click(Item item) {
                        Toast.makeText(context, getString(R.string.share_title) + item.getTitle(), Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    //分享调用
    /*private void onShare(final Context context) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        // 关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字
        // oks.setNotification(R.drawable.logo, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("U糖评估测试");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(URLs.HTML5SERVICE + url.substring(0, url.length() - 11));
        // text是分享文本，所有平台都需要这个字段
        oks.setText(url.substring(url.length() - 21, url.length() - 5)
                + "的评估结果：" + URLs.HTML5SERVICE
                + url.substring(0, url.length() - 11));
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("");// 确保SDcard下面存在此张图片
        oks.setImageUrl(URLs.IMAGE_NS + "/uploadPhoto/estimateAction.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(URLs.HTML5SERVICE + url.substring(0, url.length() - 11));
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        // oks.setComment("我是测试评论文本  ");
        // site是分享此内容的网站名称，仅在QQ空间使用
        // oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        // oks.setSiteUrl("http://sharesdk.cn");

        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {

            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if (Wechat.NAME.equals(platform.getName())) {
                    String text = url.substring(url.length() - 21,
                            url.length() - 5) + "的评估结果";
                    paramsToShare.setText(text);
                }
            }
        });
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {

            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if (QQ.NAME.equals(platform.getName())) {
                    String text = url.substring(url.length() - 21,
                            url.length() - 5) + "的评估结果";
                    paramsToShare.setText(text);
                }
            }
        });
        // 启动分享GUI
        oks.show(context);
    }*/


}
