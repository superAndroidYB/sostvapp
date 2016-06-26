package activity.sostv.com.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import activity.sostv.com.sostvapp.R;

public class WelcomeActivity extends Activity {

    private static final int ANIMATION_DURATION = 2000;
    private static final float SCALE_END = 1.13F;
    public SharedPreferences sp = null;

    @ViewInject(R.id.iv_entry)
    private ImageView welocmeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ViewUtils.inject(this);
        animateImage();
    }

    private void animateImage(){
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(welocmeImage, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(welocmeImage, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                jumpNextPage();
            }
        });
    }

    private void jumpNextPage(){
        sp = getSharedPreferences("config", MODE_PRIVATE);
        if(!sp.getBoolean("is_guide_showed", false)){
            startActivity(new Intent(WelcomeActivity.this,GuideActivity.class));
        }else{
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        }

        finish();
    }
}
