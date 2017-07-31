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
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;

import butterknife.BindView;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.utils.ScreenResolution;
import io.vov.vitamio.widget.VideoView;
import master.flame.danmaku.ui.widget.DanmakuView;
import rxandroid.com.R;
import rxandroid.com.base.BaseActivity;
import rxandroid.com.base.BaseView;
import rxandroid.com.danmu.utils.DanmuProcess;
import rxandroid.com.model.logic.common.CommonPcLiveVideoModelLogic;
import rxandroid.com.model.logic.common.bean.OldLiveVideoInfo;
import rxandroid.com.presenter.common.impl.CommonPcLiveVideoPresenterImp;
import rxandroid.com.presenter.common.interfaces.CommonPcLiveVideoContract;
import rxandroid.com.ui.loadplay.LoadingView;

/**
 * Created on 2017/6/13.
 * Author by Aaron.Wang
 */

public class PcLiveVideoActivity extends BaseActivity<CommonPcLiveVideoModelLogic, CommonPcLiveVideoPresenterImp> implements CommonPcLiveVideoContract.View, io.vov.vitamio.MediaPlayer.OnInfoListener, io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener,
        io.vov.vitamio.MediaPlayer.OnErrorListener
{

    @BindView(R.id.vm_videoview)
    VideoView vmVideoview;
    @BindView(R.id.fl_loading)
    FrameLayout flLoading;
    @BindView(R.id.danmakuView)
    DanmakuView danmakuView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_live_nickname)
    TextView tvLiveNickname;
    @BindView(R.id.iv_live_setting)
    ImageView ivLiveSetting;
    @BindView(R.id.iv_live_gift)
    ImageView ivLiveGift;
    @BindView(R.id.iv_live_share)
    ImageView ivLiveShare;
    @BindView(R.id.iv_live_follow)
    ImageView ivLiveFollow;
    @BindView(R.id.control_top)
    RelativeLayout controlTop;
    @BindView(R.id.iv_live_play)
    ImageView ivLivePlay;
    @BindView(R.id.iv_live_refresh)
    ImageView ivLiveRefresh;
    @BindView(R.id.control_bottom)
    RelativeLayout controlBottom;
    @BindView(R.id.im_logo)
    ImageView imLogo;
    @BindView(R.id.lv_playloading)
    LoadingView lvPlayloading;
    @BindView(R.id.iv_control_img)
    ImageView ivControlImg;
    @BindView(R.id.tv_control_name)
    TextView tvControlName;
    @BindView(R.id.tv_control)
    TextView tvControl;
    @BindView(R.id.control_center)
    RelativeLayout controlCenter;
    @BindView(R.id.im_danmu_control)
    ImageView imDanmuControl;
    @BindView(R.id.tv_loading_buffer)
    TextView tvLoadingBuffer;

    private OldLiveVideoInfo videoInfo;
    private String Room_id;
    private int mScreenWidth = 0;//屏幕宽度
    private boolean mIsFullScreen = true;//是否为全屏
    private int mShowVolume;//声音
    private int mShowLightness;//亮度
    private int mMaxVolume;//最大声音
    private AudioManager mAudioManager;
    private GestureDetector mGestureDetector;
    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener;
    private SVProgressHUD svProgressHUD;

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
    /**
     * 弹幕
     */
    private DanmuProcess mDanmuProcess;
    //    弹幕控制开关 默认打开状态
    private boolean mDanmuControlFalg = true;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                /**
                 *  隐藏top，bottom
                 * @param
                 * @return
                 */
                case HIDE_CONTROL_BAR:
                    hideControllerBar();
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
    public void getViewPcLiveVideoInfo(OldLiveVideoInfo oldLiveVideoInfo) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                videoInfo = oldLiveVideoInfo ;
                getViewInfo(oldLiveVideoInfo);
            }
        });
    }

    @Override
    protected void onInitView(Bundle bundle) {
        Room_id  = getIntent().getExtras().getString("room_id") ;
        vmVideoview.setKeepScreenOn(true);
        mPresenter.getPresenterPcLiveVideoInfo(Room_id);
        svProgressHUD = new SVProgressHUD(this) ;
        //获取屏幕亮度
        Pair<Integer, Integer> screenPair = ScreenResolution.getResolution(this);
        mScreenWidth = screenPair.first ;
        initDanMu(Room_id);
        initVolumeWithLight();
        addTouchListener();
        vmVideoview.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH,0);

    }

    @Override
    protected void onEvent() {
        vmVideoview.setOnInfoListener(this);
        vmVideoview.setOnBufferingUpdateListener(this);
        vmVideoview.setOnErrorListener(this);
    }

    @Override
    protected BaseView getView() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE) ;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Vitamio.isInitialized(this) ;

        return R.layout.activity_pclive_video;
    }

    /**
     *  获得当前屏幕的亮度值 0--255
     * @param
     * @return
     */
    private int getScreenBrightness(){
        int screenBrightness = 255 ;
        try {
            screenBrightness = Settings.System.getInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return screenBrightness ;
    }
    /**
     *  初始化声音和亮度
     * @param
     * @return
     */
    private void initVolumeWithLight(){
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE) ;
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) ;
        mShowVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)*100/mMaxVolume ;
        mShowLightness = getScreenBrightness() ;
    }

    /**
     *  初始化弹幕
     * @param
     * @return
     */
    private void initDanMu(String room_id){
        mDanmuProcess = new DanmuProcess(this,danmakuView,Integer.valueOf(room_id)) ;
        mDanmuProcess.start();
    }

    /**
     *  获得房间信息
     * @param
     * @return
     */
    private void getViewInfo(OldLiveVideoInfo oldLiveVideoInfo){
        String url = oldLiveVideoInfo.getData().getLive_url() ;
        Uri uri = Uri.parse(url) ;
        if (tvLiveNickname!=null){
            tvLiveNickname.setText(oldLiveVideoInfo.getData().getRoom_name());
        }
        if (vmVideoview!=null){
            vmVideoview.setVideoURI(uri);
            vmVideoview.setBufferSize(1024*1024*2);
            vmVideoview.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
            vmVideoview.requestFocus() ;
            vmVideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setPlaybackSpeed(1.0f);
                    flLoading.setVisibility(View.GONE);
                    ivLivePlay.setImageResource(R.drawable.img_live_videopause);
                    mHandler.sendEmptyMessageDelayed(HIDE_CONTROL_BAR,HIDE_TIME) ;
                }
            });
        }
    }

    /**
     *  隐藏控制条
     * @param
     * @return
     */
    private void hideControllerBar(){
        if (controlBottom!=null&&controlTop!=null){
            controlBottom.setVisibility(View.GONE);
            controlTop.setVisibility(View.GONE);
        }
    }
    /**
     *  添加手势操作
     * @param
     * @return
     */
    private void addTouchListener(){
        mSimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener(){
            /**
             *  双击事件,有的视频播放器支持双击播放暂停，可从这个实现
             * @param
             * @return
             */
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return super.onDoubleTap(e);
            }

            /**
             *  单击事件
             * @param
             * @return
             */

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (!mIsFullScreen){
                    return false ;
                }
                float x1 = e1.getX() ;
                float absDistanceX = Math.abs(distanceX) ;//distanceX<0 从左到右
                float absDistanceY = Math.abs(distanceY) ;//distanceY<0 从上到下
                //Y方向的距离比X方向的大，即上下滑动
                if (absDistanceX<absDistanceY){
                    if (distanceY>0){//向上滑动
                        if (x1>=mScreenWidth*0.65){//右边调节声音
                            changeVolume(ADD_FLAG) ;
                        }else{
                            changeLightness(ADD_FLAG) ;
                        }
                    }else {//向下滑动
                        if (x1>=mScreenWidth*0.65){
                            changeVolume(SUB_FLAG);
                        }else{
                            changeLightness(SUB_FLAG);
                        }

                    }
                }else {
                    //x方向的距离比Y方向的大，即 左右 滑动
                }
                return false ;
            }
        } ;

        mGestureDetector = new GestureDetector(this,mSimpleOnGestureListener) ;

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
        mShowLightness+=flag ;
        if (mShowLightness>255){
            mShowLightness=255 ;
        }else if (mShowLightness<=0){
            mShowLightness=0 ;
        }
        tvControlName.setText("亮度");
        ivControlImg.setImageResource(R.drawable.img_light);
        tvControl.setText(mShowLightness*100/255+"%");
        WindowManager.LayoutParams lp=getWindow().getAttributes() ;
        lp.screenBrightness = mShowLightness/255f ;
        getWindow().setAttributes(lp);

        mHandler.removeMessages(SHOW_CENTER_CONTROL);
        controlCenter.setVisibility(View.VISIBLE);
        mHandler.sendEmptyMessageDelayed(SHOW_CENTER_CONTROL,SHOW_CONTROL_TIME) ;

    }


    @Override
    public void onBufferingUpdate(io.vov.vitamio.MediaPlayer mp, int percent) {
        flLoading.setVisibility(View.VISIBLE);
        if (vmVideoview.isPlaying()){
            vmVideoview.pause();
        }
        tvLoadingBuffer.setText("直播以缓冲"+percent+"%...");

    }

    @Override
    public boolean onError(io.vov.vitamio.MediaPlayer mp, int what, int extra) {
        if (what==MediaPlayer.MEDIA_ERROR_UNKNOWN){
            svProgressHUD.showErrorWithStatus("播放出错~~~");
        }
        return false;
    }

    @Override
    public boolean onInfo(io.vov.vitamio.MediaPlayer mp, int what, int extra) {
        switch (what){
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (vmVideoview.isPlaying()){
                    vmVideoview.pause();
                }
                mHandler.removeMessages(HIDE_CONTROL_BAR);
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                flLoading.setVisibility(View.GONE);
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
    public void showErrorWithStatus(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                svProgressHUD.showErrorWithStatus("播放出错");
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPresenter.getPresenterPcLiveVideoInfo(Room_id);
        if (vmVideoview!=null){
            vmVideoview.start();
        }
        if (danmakuView!=null&&mDanmuProcess!=null){
            danmakuView.restart();
            mDanmuProcess.start();
        }
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
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector!=null){
            mGestureDetector.onTouchEvent(event) ;
        }
        return super.onTouchEvent(event);
    }
}
