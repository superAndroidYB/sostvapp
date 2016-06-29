package activity.sostv.com.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import activity.sostv.com.model.SosHome;
import activity.sostv.com.sostvapp.R;
import activity.sostv.com.utils.Utils;
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

    private int THUMB_SIZE = 180;


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
                shareTextToWx(SendMessageToWX.Req.WXSceneTimeline);
                break;
            case R.id.btn_share_weixinhy:
                shareTextToWx(SendMessageToWX.Req.WXSceneSession);
                break;
            case R.id.btn_share_qqhy:
                break;
            case R.id.btn_share_qqzone:
                break;
            case R.id.btn_share_sina:
                break;
        }
    }

    public void shareTextToWx(int type){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.share_img);
        String url = "http://www.sostvcn.com";
        WXWebpageObject localWXWebpageObject = new WXWebpageObject();
        localWXWebpageObject.webpageUrl = url;

        WXMediaMessage localWXMediaMessage = new WXMediaMessage( localWXWebpageObject);
        localWXMediaMessage.title = home.getTitle();//不能太长，否则微信会提示出错。
        localWXMediaMessage.description = home.getContent();
        localWXMediaMessage.thumbData = Utils.bmpToByteArray(bitmap, false);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.scene = type;
        req.transaction = System.currentTimeMillis() + "";
        req.message = localWXMediaMessage;

        weixinApi.sendReq(req);
    }

    public void regToWeixin(){
        weixinApi = WXAPIFactory.createWXAPI(this, WEIXIN_APP_ID, true);
        weixinApi.registerApp(WEIXIN_APP_ID);
    }
}
