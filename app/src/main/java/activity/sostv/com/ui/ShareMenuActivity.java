package activity.sostv.com.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import activity.sostv.com.sostvapp.R;

/**
 * Created by Administrator on 2016/6/26.
 */
public class ShareMenuActivity extends Activity implements View.OnClickListener{

    @ViewInject(R.id.btn_share_weixinmoments)
    private ImageView shareWeixinMoments;
    @ViewInject(R.id.btn_share_weixinhy)
    private ImageView shareWeixin;
    @ViewInject(R.id.btn_share_qqhy)
    private ImageView shareQQhy;
    @ViewInject(R.id.btn_share_qqzone)
    private ImageView shareQQzone;
    @ViewInject(R.id.btn_share_sina)
    private ImageView shareSina;

    public static void start(Context context){
        Intent intent = new Intent(context,ShareMenuActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_menu);
        ViewUtils.inject(this);
        shareWeixinMoments.setOnClickListener(this);
        shareWeixin.setOnClickListener(this);
        shareQQhy.setOnClickListener(this);
        shareQQzone.setOnClickListener(this);
        shareSina.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_share_weixinmoments:
                break;
            case R.id.btn_share_weixinhy:
                break;
            case R.id.btn_share_qqhy:
                break;
            case R.id.btn_share_qqzone:
                break;
            case R.id.btn_share_sina:
                break;
        }
    }
}
