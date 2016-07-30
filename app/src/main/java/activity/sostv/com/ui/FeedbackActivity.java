package activity.sostv.com.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import activity.sostv.com.global.Constants;
import activity.sostv.com.global.WebServiceHelper;
import activity.sostv.com.model.SosFeedback;
import activity.sostv.com.model.SosUser;
import activity.sostv.com.model.SostvRequest;
import activity.sostv.com.sostvapp.R;
import io.bxbxbai.common.utils.GlobalExecutor;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener{

    @ViewInject(R.id.et_feedback)
    private EditText feedDesc;
    @ViewInject(R.id.feedBackSub_btn)
    private Button btnSubmit;
    private SosUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initToolBar();
        setTitle(R.string.feedBack);
        ViewUtils.inject(this);
        user = ((SostvApplication)getApplication()).getUser();
        btnSubmit.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.feedBackSub_btn:
                if(feedDesc.getText().toString() == null || "".equals(feedDesc.getText().toString())){
                    return;
                }
                if(((SostvApplication)getApplication()).getIsLogin()){
                    taskTool = new WebServiceHelper();
                    SosFeedback fb = new SosFeedback();
                    fb.setUserName(user.getUserName());
                    fb.setUserCname(user.getUserCname());
                    fb.setContent(feedDesc.getText().toString());
                    Gson gson = new Gson();
                    SostvRequest request = new SostvRequest();
                    request.setProperty(0, Constants.FEEDBACK);
                    request.setProperty(1,"FeedbackActivity");
                    request.setProperty(2,gson.toJson(fb));

                    taskTool.execute(request);

                    showDialog();
                }else{
                    LoginActivity.start(this);
                }
                break;
        }
    }

    private void showDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("感谢您的反馈，我们会认真查看您的意见，并会根据实际情况做出相应的调整！");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog
                finish();
            }
        });
        builder.create().show();
    }
}
