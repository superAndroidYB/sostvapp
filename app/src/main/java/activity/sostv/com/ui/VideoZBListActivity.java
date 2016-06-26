package activity.sostv.com.ui;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

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
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;

public class VideoZBListActivity extends BaseActivity {

	@ViewInject(R.id.vv_zb_sostv)
	private VideoView videoView;
	
	@ViewInject(R.id.zb_video_gridView)
	private SostvGridView gridView;
	
	private SosHomeZxtjGridViewAdapter adapter;
	private List<SosVideo> datas;
	
	private Uri uri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zb_videoview);
		ViewUtils.inject(this);

		initToolBar();
		setTitle(R.string.btn_zb);

		initUI();
		loadData();
		
		String videoUrl = getZbURL();
		
		if(videoUrl.isEmpty() || "".equals(videoUrl)){
			Toast.makeText(this, "无效的地址", Toast.LENGTH_SHORT).show();
			return ;
		}else{
			uri = Uri.parse(videoUrl);
            videoView.setVideoURI(uri);
            //videoView.setMediaController(new MediaController(this));
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setPlaybackSpeed(1.0f);
                }
            });
		}
	}
	
	private void initUI(){
		datas = new ArrayList<SosVideo>();
		adapter = new SosHomeZxtjGridViewAdapter(this, datas);
        gridView.setAdapter(adapter);
	}
	
	private void loadData(){
		datas = new ArrayList<SosVideo>();
		adapter = new SosHomeZxtjGridViewAdapter(this, datas);
		gridView.setAdapter(adapter);
		
		LoadData loadData = new LoadData();
		SostvRequest request = new SostvRequest();
		request.setProperty(0, Constants.VIDEO_LBZB);
		request.setProperty(1, "VideoCBListActivity");
		loadData.execute(request);
	}
	
	private String getZbURL(){
		return "http://sostvmedia.oss-cn-hangzhou.aliyuncs.com/2015-09-18-kids-33.mp4";
	}
	
	private class LoadData extends WebServiceHelper {

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
}
