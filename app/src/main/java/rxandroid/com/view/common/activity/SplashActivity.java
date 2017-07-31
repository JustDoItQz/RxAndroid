package rxandroid.com.view.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import rxandroid.com.R;
import rxandroid.com.utils.SharedPreferenceUtils;

/**
 * Created on 2017/6/13.
 * Author by Aaron.Wang
 */

public class SplashActivity extends AppCompatActivity {

    Handler handler ;
    Intent intent ;
    private boolean isFirst ;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
//        这步取值操作不能放在run中，否则会执行两次导致页面跳转出错
        isFirst = SharedPreferenceUtils.getBooleanData("isFirst", true);
        handler = new Handler();
//        使用lamb代替new Runnable

        handler.postDelayed(() -> {
//                LogUtils.e(Thread.currentThread().getName() + "++++");
            if (isFinishing()) {
                return;
            }
            if (isFirst) {
                intent = new Intent(SplashActivity.this, GuideActivity.class);
                SharedPreferenceUtils.setBooleanData("isFirst", false);

            } else {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            }
            startActivity(intent);
            finish();
        }, 3000);

    }
}
