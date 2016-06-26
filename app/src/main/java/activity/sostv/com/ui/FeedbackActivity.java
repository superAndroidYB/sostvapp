package activity.sostv.com.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import activity.sostv.com.sostvapp.R;
import io.bxbxbai.common.utils.GlobalExecutor;

public class FeedbackActivity extends BaseActivity {

    @ViewInject(R.id.et_feedback)
    private EditText feedDesc;
    @ViewInject(R.id.feedBackSub_btn)
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initToolBar();
        setTitle(R.string.feedBack);
        ViewUtils.inject(this);
    }

    public static void start(final Context context) {
        final Intent intent = new Intent(context, FeedbackActivity.class);
        GlobalExecutor.MAIN_HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                context.startActivity(intent);
            }
        }, 300);
    }
}
