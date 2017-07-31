package rxandroid.com.view.common.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rxandroid.com.R;
import rxandroid.com.base.BaseView;
import rxandroid.com.ui.NavigateTabBar;
import rxandroid.com.utils.ExampleUtil;
import rxandroid.com.utils.LocalBroadcastManager;
import rxandroid.com.view.follow.fragment.FollowFragment;
import rxandroid.com.view.home.fragment.HomeFragment;
import rxandroid.com.view.live.fragment.LiveFragment;
import rxandroid.com.view.user.fragment.UserFragment;
import rxandroid.com.view.video.fragment.VideoFragment;

import static rxandroid.com.R.id.mainTabBar;

/**
 * Created on 2017/7/4.
 * Author by Aaron.Wang
 */

public class MainActivity extends AppCompatActivity implements BaseView {

    private static final String TAG_PAGE_HOME = "首页";
    private static final String TAG_PAGE_LIVE= "直播";
    private static final String TAG_PAGE_VIDEO = "视频";
    private static final String TAG_PAGE_FOLLOW = "关注";
    private static final String TAG_PAGE_USER = "我的";


    //判断应用是否运行在前台进程
    public static boolean isForeground = false;

    protected Unbinder unbinder ;
    //   退出时间
    private long exitTime = 0 ;
    @BindView(mainTabBar)
    NavigateTabBar mNavigateTabBar ;
    NavigateTabBar.ViewHolder mHolder ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this) ;
        registerMessageReceiver();
        mNavigateTabBar.onRestoreInstanceState(savedInstanceState);
        mNavigateTabBar.addTab(HomeFragment.class, new NavigateTabBar.TabParam(R.mipmap.home_pressed, R.mipmap
                .home_selected,TAG_PAGE_HOME));
        mNavigateTabBar.addTab(LiveFragment.class, new NavigateTabBar.TabParam(R.mipmap.live_pressed, R.mipmap
                .live_selected, TAG_PAGE_LIVE));
        mNavigateTabBar.addTab(VideoFragment.class, new NavigateTabBar.TabParam(R.mipmap.video, R
                .mipmap.video_selected, TAG_PAGE_VIDEO));
        mNavigateTabBar.addTab(FollowFragment.class, new NavigateTabBar.TabParam(R.mipmap.follow_pressed,
                R.mipmap.follow_selected, TAG_PAGE_FOLLOW));
        mNavigateTabBar.addTab(UserFragment.class, new NavigateTabBar.TabParam(R.mipmap.user_pressed, R.mipmap
                .user_selected, TAG_PAGE_USER));

        mNavigateTabBar.setTabSelectListener(new NavigateTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(NavigateTabBar.ViewHolder holder) {
                switch (holder.tag.toString()){
                    //首页
                    case TAG_PAGE_HOME:
                        mNavigateTabBar.showFragment(holder);
                        break;

                    //直播
                    case TAG_PAGE_LIVE:
                        mNavigateTabBar.showFragment(holder);
                        break;

                    //视频
                    case TAG_PAGE_VIDEO:
                        mNavigateTabBar.showFragment(holder);
                        break;
                    //关注

                    case TAG_PAGE_FOLLOW:
                        mNavigateTabBar.showFragment(holder);
                        break;
                    //我的
                    case TAG_PAGE_USER:
                        if (mNavigateTabBar!=null){
                            mNavigateTabBar.showFragment(holder);
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void showErrorWithStatus(String msg) {

    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     *  拦截返回键，要求点击两次返回键才能退出应用
     * @param keyCode 按键代码
     * @param event 点击事件
     * @return 是否处理本次事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            exit() ;
            return false ;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void exit(){
        if ((System.currentTimeMillis()-exitTime)/1000>2000){
            Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis() ;
        }else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
         isForeground = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        isForeground = false ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mNavigateTabBar.onSaveInstanceState(outState);
    }
    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "rxandroid.com.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
