package activity.sostv.com.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.UUID;

import activity.sostv.com.model.SosHome;
import activity.sostv.com.sostvapp.R;
import io.bxbxbai.common.T;

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

    private SosHome home;

    public static final String WEIXIN_APP_ID = "wx94f3c66792c96e9f";
    public static final String WEIXIN_APP_SECRET = "7808ce3b2a26778a9ad7969078276669";
    public IWXAPI weixinApi;

    public static void start(Context context,SosHome home){
        Intent intent = new Intent(context,ShareMenuActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("sosHome", home);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_menu);

        try {
            home = (SosHome) this.getIntent().getSerializableExtra("sosHome");
        } catch (Exception e) {
            T.showToast("文章地址解析出错，请联系APP开发人员");
            finish();
            return;
        }

        regToWeixin();
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
                shareToWx(SendMessageToWX.Req.WXSceneTimeline);
                break;
            case R.id.btn_share_weixinhy:
                shareToWx(SendMessageToWX.Req.WXSceneSession);
                break;
            case R.id.btn_share_qqhy:
                break;
            case R.id.btn_share_qqzone:
                break;
            case R.id.btn_share_sina:
                break;
        }
    }

    public void shareToWx(int type){
        WXTextObject textObject = new WXTextObject();
        textObject.text = home.getTitle();

        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = textObject;
        message.description = home.getSubhead();


        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.scene = type;
        req.transaction = String.valueOf(UUID.randomUUID());
        req.message = message;

        weixinApi.sendReq(req);
    }

    public void regToWeixin(){
        weixinApi = WXAPIFactory.createWXAPI(this, WEIXIN_APP_ID, true);
        weixinApi.registerApp(WEIXIN_APP_ID);
    }
}
