package activity.sostv.com.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import activity.sostv.com.adapter.SosHomeZxtjGridViewAdapter;
import activity.sostv.com.global.Constants;
import activity.sostv.com.global.WebServiceHelper;
import activity.sostv.com.model.SosVideo;
import activity.sostv.com.model.SostvRequest;
import activity.sostv.com.model.SostvResponse;
import activity.sostv.com.sostvapp.R;
import activity.sostv.com.widght.SostvGridView;
import io.bxbxbai.common.T;
import io.bxbxbai.common.utils.GlobalExecutor;

public class VideoHomeActivity extends BaseActivity {

    @ViewInject(R.id.explorGridView)
    private SostvGridView gridView;

    private SosHomeZxtjGridViewAdapter adapter;
    private List<SosVideo> datas;

    @ViewInject(R.id.ivzb)
    private ImageView ivzb;

    @ViewInject(R.id.ivcb)
    private ImageView ivcb;

    @ViewInject(R.id.ivxl)
    private ImageView ivxl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_home);
        initToolBar();
        setTitle(R.string.video_home_title);
        ViewUtils.inject(this);

        initUI();
        loadZxtjDatas();
    }

    private void initUI() {
        datas = new ArrayList<SosVideo>();
        adapter = new SosHomeZxtjGridViewAdapter(this, datas);
        gridView.setAdapter(adapter);

        ivzb.setOnClickListener(new ImageViewOnClick());
        ivcb.setOnClickListener(new ImageViewOnClick());
        ivxl.setOnClickListener(new ImageViewOnClick());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                VideoViewActivity.start(VideoHomeActivity.this, datas.get(arg2));
            }
        });
    }

    private void loadZxtjDatas(){
        taskTool = new LoadZxtjDatas();
        SostvRequest request = new SostvRequest();
        request.setProperty(0, Constants.VIDEO_NEWS);
        request.setProperty(1, "VideoHomeActivity");
        taskTool.execute(request);
    }

    private class LoadZxtjDatas extends WebServiceHelper {

        @Override
        protected void onPostExecute(SostvResponse result) {
            if(result == null){
                return;
            }
            if(!Constants.CODE.equals(result.getReturnCode())){
                T.showToast("服务器内部错误,请稍后重试");
                return;
            }
            String data = result.getReturnData();
            Gson gson = new Gson();
            List<SosVideo> list = gson.fromJson(data, new TypeToken<List<SosVideo>>(){}.getType());
            datas.addAll(0,list);
            adapter.notifyDataSetChanged();
        }
    }

    public static void start(final Context context) {
        final Intent intent = new Intent(context, VideoHomeActivity.class);
        GlobalExecutor.MAIN_HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                context.startActivity(intent);
            }
        }, 300);
    }

    private class ImageViewOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ivzb:
                    startActivity(new Intent(VideoHomeActivity.this,VideoZBListActivity.class));
                    break;
                case R.id.ivcb:
                    startActivity(new Intent(VideoHomeActivity.this,VideoCBListActivity.class));
                    break;
                case R.id.ivxl:
                    startActivity(new Intent(VideoHomeActivity.this,VideoXLListActivity.class));
                    break;
            }
        }

    }
}
