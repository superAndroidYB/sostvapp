package activity.sostv.com.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import activity.sostv.com.sostvapp.R;
import activity.sostv.com.widght.ParallaxListView;

public class VideoXLListActivity extends BaseActivity {


	private String[] dataTitle = new String[]{
			"但以理视频讲座","启示录视频讲座","圣经预言与时事征兆","半小时布道","5分钟漫步	","罗马书讲座","加拉太书讲座","希伯来书讲座",
			"圣所，福音唯一的蓝图","罗马书简明讲解"
	};
	@ViewInject(R.id.xl_video_listview)
	private ParallaxListView videoListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xl_videoview);
		ViewUtils.inject(this);
		setTitle(R.string.btn_xl);

		initUI();
	}

	private void initUI(){
		final View headerView = LayoutInflater.from(this).inflate(R.layout.xllist_header_view,null);
		final ImageView img = (ImageView)headerView.findViewById(R.id.parallax_img);

		videoListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
		videoListView.addHeaderView(headerView);
		videoListView.setAdapter(new XlListViewAdapter());

		headerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				videoListView.setParallaxImage(img);
				headerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
	}

	class XlListViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return dataTitle.length;
		}

		@Override
		public Object getItem(int position) {
			return dataTitle[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = LayoutInflater.from(VideoXLListActivity.this).inflate(R.layout.xl_video_view_item,null);
			ImageView itemImage = (ImageView)v.findViewById(R.id.xl_item_image);
			TextView itemText = (TextView)v.findViewById(R.id.xl_item_text);
			itemText.setText(dataTitle[position]);
			return v;
		}
	}




	

}
