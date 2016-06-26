package activity.sostv.com.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import activity.sostv.com.sostvapp.R;
import io.bxbxbai.common.utils.GlobalExecutor;

public class AboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
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
