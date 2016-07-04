package activity.sostv.com.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import activity.sostv.com.global.ACache;
import activity.sostv.com.global.Constants;
import activity.sostv.com.global.WebServiceHelper;
import activity.sostv.com.model.SosComment;
import activity.sostv.com.model.SosContent;
import activity.sostv.com.model.SosHome;
import activity.sostv.com.model.SosOrder;
import activity.sostv.com.model.SosUser;
import activity.sostv.com.model.SostvRequest;
import activity.sostv.com.model.SostvResponse;
import activity.sostv.com.sostvapp.R;
import io.bxbxbai.common.T;
import io.bxbxbai.common.utils.GlobalExecutor;
import io.bxbxbai.common.view.CircularLoadingView;

public class NewContentActivity extends BaseActivity implements View.OnClickListener{

    private static final String DIANZAN = "DIANZAN";
    private static final String SHOUCANG = "SHOUCANG";
    private static final String UNDIANZAN = "UNDIANZAN";
    private static final String UNSHOUCANG = "UNSHOUCANG";

    @ViewInject(R.id.tvContentTitle)
    private TextView contentTitle;
    @ViewInject(R.id.contentBody)
    private TextView contentBody;
    @ViewInject(R.id.subheading)
    private TextView subheading;
    @ViewInject(R.id.content_imageview)
    private ImageView contentImageview;

    @ViewInject(R.id.rl_btn_comment)
    private RelativeLayout btnComment;
    @ViewInject(R.id.rl_btn_dianzan)
    private RelativeLayout btnDianzan;
    @ViewInject(R.id.btn_dianzan)
    private ImageView btnDianzanImg;
    @ViewInject(R.id.rl_btn_shoucang)
    private RelativeLayout btnShoucang;
    @ViewInject(R.id.btn_collect)
    private ImageView btnShoucangImg;
    @ViewInject(R.id.rl_btn_fenxiang)
    private RelativeLayout btnfenxiang;

    @ViewInject(R.id.ll_ev_comment)
    private LinearLayout llEvComment;
    @ViewInject(R.id.sendmsg_footer_btn)
    private ImageView sendBtn;
    @ViewInject(R.id.comment_footer_edit)
    private EditText etComment;
    @ViewInject(R.id.v_loading)
    private CircularLoadingView loadingView;
    private BitmapUtils bitmapUtils;

    private SosHome home;
    private ACache mCache;
    private SosOrder order;

    private SharedPreferences spDz;
    private SharedPreferences spSc;

    private SosUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_content);
        ViewUtils.inject(this);
        user = ((SostvApplication)getApplication()).getUser();
        bitmapUtils = new BitmapUtils(this);
        mCache = ACache.get(this);
        spDz = getSharedPreferences("DIANZAN_IDS", Context.MODE_PRIVATE);
        spSc = getSharedPreferences("SHOUCANG_IDS", Context.MODE_PRIVATE);

        initToolBar();
        initOrderUI();
        try {
            home = (SosHome) this.getIntent().getSerializableExtra("sosHome");
        } catch (Exception e) {
            T.showToast( "文章地址解析出错，请联系APP开发人员");
            finish();
            return;
        }

        if(home != null){
            setTitle(home.getTitle());
            contentTitle.setText(home.getTitle());
            subheading.setText(home.getContent());
            bitmapUtils.display(contentImageview, home.getImageUrl());
            loadContentHtml();
            initBtnUI();
        }
    }

    private void initBtnUI(){
        if(isDzContains(home.getId())){
            btnDianzanImg.setImageResource(R.mipmap.dianzan_btn_f);
        }
        if(isScContains(home.getId())){
            btnShoucangImg.setImageResource(R.mipmap.collect_btn_f);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initOrderUI(){
        btnComment.setOnClickListener(this);
        btnDianzan.setOnClickListener(this);
        btnShoucang.setOnClickListener(this);
        btnfenxiang.setOnClickListener(this);
        sendBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_btn_comment:
                showCommentCon();
                break;
            case R.id.rl_btn_dianzan:
                if(isDzContains(home.getId())){
                    orderServiceRequest(UNDIANZAN);
                }else{
                    orderServiceRequest(DIANZAN);
                }
                break;
            case R.id.rl_btn_shoucang:
                if(isScContains(home.getId())){
                    orderServiceRequest(UNSHOUCANG);
                }else{
                    orderServiceRequest(SHOUCANG);
                }
                break;
            case R.id.rl_btn_fenxiang:
                showShareMenu();
                break;
            case R.id.sendmsg_footer_btn:
                sendComment();
                break;
            default:
                break;
        }
    }


    private void sendComment(){
        if(!((SostvApplication)getApplication()).getIsLogin()){
            LoginActivity.start(this);
        }
        String comment = etComment.getText().toString();
        if(comment == null || "".equals(comment)){
            T.showToast("评论内容不能为空");
            return ;
        }
        Gson gson = new Gson();
        SostvRequest request = new SostvRequest();
        SosComment sc = new SosComment();
        sc.setResourceId(home.getId());
        sc.setCommentContent(comment);
        sc.setCommentUser(user.getUserName());
        request.setProperty(0, Constants.SENDCOMMENT);
        request.setProperty(1,"NewContentActivity");
        request.setProperty(2, gson.toJson(sc));
        WebServiceHelper sendCommentService = new WebServiceHelper();
        sendCommentService.execute(request);
        etComment.clearFocus();
        llEvComment.setVisibility(View.GONE);
    }

    private void showCommentCon(){
        llEvComment.setVisibility(View.VISIBLE);
        etComment.requestFocus();
    }

    private void showShareMenu(){
        ShareMenuActivity.start(this,home);
    }

    //设置图片滤镜
    private void setFilter() {
        //先获取设置的src图片
        Drawable drawable=contentImageview.getDrawable();
        //当src图片为Null，获取背景图片
        if (drawable==null) {
            drawable=contentImageview.getBackground();
        }
        if(drawable!=null){
            //设置滤镜
            drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        }
    }

    //缓存已下载的文章
    private void cacheContent(SosContent content){
        mCache.put(home.getId(),content,60*60*24);
    }

    private SosContent getCacheContent(){
        SosContent content = (SosContent) mCache.getAsObject(home.getId());
        return content;
    }

    private void showContentHtml(SosContent content){
        setFilter();
        //loadingView.setVisibility(View.GONE);
        contentBody.setMovementMethod(ScrollingMovementMethod.getInstance());
        contentBody.setText(Html.fromHtml(content.getContent(), new Html.ImageGetter() {

            @Override
            public Drawable getDrawable(String source) {
                Drawable drawable = null;
                URL url;
                try {
                    url = new URL(source);
                    drawable = Drawable.createFromStream(url.openStream(), "");  //获取网路图片
                } catch (Exception e) {
                    return null;
                }
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
                        .getIntrinsicHeight());
                return drawable;
            }
        }, null));
    }

    private void orderServiceRequest(String orderType){
        order = new SosOrder();
        order.setOrderType(orderType);
        order.setOrderId(home.getId());
        Gson gson = new Gson();
        String data = gson.toJson(order);
        SostvRequest request = new SostvRequest();
        request.setProperty(0, Constants.ORDER);
        request.setProperty(1, "NewContentActivity");
        request.setProperty(2, data);
        ContentOrderService orderService = new ContentOrderService();
        orderService.execute(request);
    }

    private void loadContentHtml(){
        SosContent content = getCacheContent();
        if(content != null){
            showContentHtml(content);
        }else{
            SostvRequest request = new SostvRequest();
            request.setProperty(0, Constants.SOS_CONTENT);
            request.setProperty(1, "NewContentActivity");
            request.setProperty(2, home.getId());

            taskTool = new LoadContentHtml();
            taskTool.execute(request);
        }
    }



    private class LoadContentHtml extends WebServiceHelper {

        @Override
        protected void onPostExecute(SostvResponse result) {
            if(result == null){
                return;
            }
            if(!Constants.CODE.equals(result.getReturnCode())){
                T.showToast( "服务器内部错误,请稍后重试");
                return;
            }
            String data = result.getReturnData();
            Gson gson = new Gson();
            List<SosContent> contents = gson.fromJson(data, new TypeToken<List<SosContent>>(){}.getType());
            if(contents.isEmpty()){
                return;
            }
            SosContent content = contents.get(0);
            if(content == null){
                return;
            }
            showContentHtml(content);
            cacheContent(content);
        }
    }

    private class ContentOrderService extends WebServiceHelper{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(DIANZAN.equals(order.getOrderType())){
                Set<String> ids = getDianzanSet();
                ids.add(home.getId());
                insertDianzanSet(ids);
                btnDianzanImg.setImageResource(R.mipmap.dianzan_btn_f);
            }else if(SHOUCANG.equals(order.getOrderType())){
                Set<String> ids = getShouchangSet();
                ids.add(home.getId());
                insertShouchangSet(ids);
                btnShoucangImg.setImageResource(R.mipmap.collect_btn_f);
            }else if(UNDIANZAN.equals(order.getOrderType())){
                Set<String> ids = removeDzSet(home.getId());
                insertDianzanSet(ids);
                btnDianzanImg.setImageResource(R.mipmap.dianzan_btn);
            }else if(UNSHOUCANG.equals(order.getOrderType())){
                Set<String> ids = removeScSet(home.getId());
                insertShouchangSet(ids);
                btnShoucangImg.setImageResource(R.mipmap.collect_btn);
            }
        }
    }

    private Set<String> getDianzanSet(){
        return spDz.getStringSet("DIANZAN_ID_SET",new HashSet<String>());
    }

    private Set<String> getShouchangSet(){
        return spSc.getStringSet("SHOUCANG_ID_SET",new HashSet<String>());
    }

    private void insertDianzanSet(Set<String> dzSet){
        spDz.edit().clear().commit();
        spDz.edit().putStringSet("DIANZAN_ID_SET",dzSet).commit();
    }

    private void insertShouchangSet(Set<String> scSet){
        spSc.edit().clear().commit();
        spSc.edit().putStringSet("SHOUCANG_ID_SET", scSet).commit();
    }

    private boolean isDzContains(String id){
        Set<String> ids = getDianzanSet();
        for(String str : ids){
            if(str.equals(id)){
                return true;
            }
        }
        return false;
    }
    private boolean isScContains(String id){
        Set<String> ids = getShouchangSet();
        for(String str : ids){
            if(str.equals(id)){
                return true;
            }
        }
        return false;
    }

    private Set<String> removeDzSet(String id){
        Set<String> ids = getDianzanSet();
        Set<String> newIds = new HashSet<>();
        for(String str : ids){
            if(str.equals(id)){
                continue;
            }else{
                newIds.add(str);
            }
        }
        return newIds;
    }
    private Set<String> removeScSet(String id){
        Set<String> ids = getShouchangSet();
        Set<String> newIds = new HashSet<>();
        for(String str : ids){
            if(str.equals(id)){
                continue;
            }else{
                newIds.add(str);
            }
        }
        return newIds;
    }




    public static void start(final Context context,SosHome home){
        final Intent intent = new Intent(context, NewContentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("sosHome", home);
        intent.putExtras(bundle);
        GlobalExecutor.MAIN_HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                context.startActivity(intent);
            }
        }, 300);
    }
}
