package activity.sostv.com.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import activity.sostv.com.sostvapp.R;
import io.bxbxbai.common.utils.GlobalExecutor;

public class AboutUsActivity extends BaseActivity {

    protected Typeface pingfang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setTitle(R.string.aboutUs);
        pingfang = Typeface.createFromAsset(this.getAssets(), "fonts/PingFangHeavy.ttf");
        setPingfangByTextView();
    }

    private void setPingfangByTextView() {
        View view = this.getWindow().getDecorView();
        List<View> views = getAllChildViews(view);
        for (View v : views){
            if(v instanceof TextView){
                ((TextView)v).setTypeface(pingfang);
            }
        }
    }

    private List<View> getAllChildViews(View view) {
        List<View> allchildren = new ArrayList<View>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                allchildren.add(viewchild);
                allchildren.addAll(getAllChildViews(viewchild));
            }
        }
        return allchildren;
    }

    public static void start(final Context context) {
        final Intent intent = new Intent(context, AboutUsActivity.class);
        GlobalExecutor.MAIN_HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                context.startActivity(intent);
            }
        }, 280);
    }
}
