package rxandroid.com.view.common.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import rxandroid.com.R;
import rxandroid.com.view.common.adapter.GuigeAdapter;

/**
 * Created on 2017/6/16.
 * Author by Aaron.Wang
 */

public class GuideActivity extends AppCompatActivity {

    private Context context ;
    @BindView(R.id.guide)
    ViewPager vp_guide ;
    int[] guides = new int[]{R.mipmap.guide_bg1, R.mipmap.guide_bg2, R.mipmap.guide_bg3, R.mipmap.guide_bg4};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this) ;
        GuigeAdapter guigeAdapter = new GuigeAdapter(guides,this) ;
        vp_guide.setAdapter(guigeAdapter);

    }
}
