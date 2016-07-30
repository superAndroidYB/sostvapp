package activity.sostv.com.ui;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

import activity.sostv.com.model.SosVideo;
import activity.sostv.com.sostvapp.R;
import activity.sostv.com.widght.SostvMediaController;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;

public class VideoZBListActivity extends BaseActivity {

	@ViewInject(R.id.vv_zb_sostv)
	private VideoView videoView;

	private List<SosVideo> datas;
	
	private Uri uri;
	private SostvMediaController mediaController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zb_videoview);
		ViewUtils.inject(this);

		setTitle(R.string.btn_zb);
		
		String videoUrl = getZbURL();
		mediaController = new SostvMediaController(this,videoView,this);
		mediaController.setAnchorView(videoView);
		
		if(videoUrl.isEmpty() || "".equals(videoUrl)){
			Toast.makeText(this, "无效的地址", Toast.LENGTH_SHORT).show();
			return ;
		}else{
			uri = Uri.parse(videoUrl);
			videoView.setMediaController(mediaController);
            videoView.setVideoURI(uri);
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setPlaybackSpeed(1.0f);
                }
            });
		}
	}
	

	
	private String getZbURL(){
		return "http://sostvmedia.oss-cn-hangzhou.aliyuncs.com/2015-09-18-kids-33.mp4";
	}
	
}
