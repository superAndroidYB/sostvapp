package activity.sostv.com.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import activity.sostv.com.model.SosComment;
import activity.sostv.com.model.SosVideo;
import activity.sostv.com.sostvapp.R;
import activity.sostv.com.widght.ReboundScrollView;
import activity.sostv.com.widght.SostvCollapsibleTextView;
import io.bxbxbai.common.T;

public class VideoViewActivity extends BaseActivity implements View.OnClickListener {
	public static  final String TAG = "VideoViewActivity";
	@ViewInject(R.id.video_parallax_scrollview)
	private ReboundScrollView scrollView;

	@ViewInject(R.id.video_info_list)
	private ListView videInfoList;
	@ViewInject(R.id.video_comment_list)
	private ListView viewCommentList;
	@ViewInject(R.id.view_info_text)
	private SostvCollapsibleTextView videoInfo;
	@ViewInject(R.id.tv_play_name)
	private TextView playName;
	@ViewInject(R.id.iv_cache_btn)
	private ImageView cacheBtn;
	@ViewInject(R.id.iv_screen_btn)
	private ImageView screenBtn;
	@ViewInject(R.id.player_button)
	private ImageView playButton;
	@ViewInject(R.id.title_image)
	private ImageView titleImage;

	private SosVideo mSosVideo;
	private List<SosVideo> videoList;
	private List<SosComment> commentList;
	private BitmapUtils bitmapUtils;

	public static void start(Context context, SosVideo video){
		Intent intent = new Intent(context, VideoViewActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("sosVideo", video);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_view);
		ViewUtils.inject(this);
		initToolBar();
		bitmapUtils =  new BitmapUtils(this);
		parseIntent(getIntent());
		initUI();
		setTitle(mSosVideo.getVideoName());
	}

	private void parseIntent(Intent i){
		try {
			mSosVideo = (SosVideo) i.getSerializableExtra("sosVideo");
		} catch (Exception e) {
			T.showToast("视频地址解析出错，请联系APP开发人员");
			finish();
			return;
		}
	}


	private void initUI() {
		videoList = new ArrayList<>();
		commentList = new ArrayList<>();

		videInfoList.setAdapter(new RecommendListAdapter());
		viewCommentList.setAdapter(new CommentListAdapter());

		String str = "\u3000\u3000本书讲述耶稣的生平故事，对应《圣经·新约》的前4卷。共分九篇，内容包括：耶稣降生、初试锋芒、耶稣传道的故事、被拒绝的救世主、被钉十字架等.本书讲述耶稣的生平故事，对应《圣经·新约》的前4卷。共分九篇，内容包括：耶稣降生、初试锋芒、耶稣传道的故事、被拒绝的救世主、被钉十字架等.本书讲述耶稣的生平故事，对应《圣经·新约》的前4卷。共分九篇，内容包括：耶稣降生、初试锋芒、耶稣传道的故事、被拒绝的救世主、被钉十字架等";
		videoInfo.setDesc(str, TextView.BufferType.NORMAL);

		bitmapUtils.display(titleImage,mSosVideo.getVedioImage());
		playName.setText(mSosVideo.getVideoName());
		cacheBtn.setOnClickListener(this);
		screenBtn.setOnClickListener(this);
		playButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.player_button:
				FullScreenVideoActivity.start(VideoViewActivity.this, mSosVideo);
				break;
			default:
				T.showToast("kwkw");
				break;
		}
	}

	class RecommendListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// return videoList.size();
			return 3;
		}

		@Override
		public Object getItem(int position) {
			return videoList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// if(!videoList.isEmpty()){
			if (convertView == null) {
				LayoutInflater layoutInflater = LayoutInflater
						.from(VideoViewActivity.this);
				convertView = layoutInflater.inflate(
						R.layout.recommend_list_item, null);
			}
			// }
			return convertView;
		}

	}

	class CommentListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// return commentList.size();
			return 3;
		}

		@Override
		public Object getItem(int position) {
			return commentList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// if(!commentList.isEmpty()){
			if (convertView == null) {
				LayoutInflater layoutInflater = LayoutInflater
						.from(VideoViewActivity.this);
				convertView = layoutInflater.inflate(
						R.layout.comment_list_item, null);
			}
			// }
			return convertView;
		}

	}
}
