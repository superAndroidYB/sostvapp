package activity.sostv.com.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import activity.sostv.com.sostvapp.R;

public class VideoCBListActivity extends BaseActivity implements AdapterView.OnItemClickListener{

	@ViewInject(R.id.cb_video_list)
	private ListView listView;
	private List<Object[]> datas = getDatas();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cb_videoview);
		ViewUtils.inject(this);
		setTitle(R.string.btn_cb);
		initUI();
	}
	
	private void initUI(){
		listView.setAdapter(new CbListAdapter());
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
	}

	class CbListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public Object getItem(int position) {
			return datas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public boolean isEnabled(int position) {
			Object[] obj = datas.get(position);
			if(obj.length == 2){
				return false;
			}else{
				return true;
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Object[] obj = datas.get(position);
			View v = null;
			if(obj.length == 2){
				v = LayoutInflater.from(VideoCBListActivity.this).inflate(R.layout.cb_videoview_list_item_tag,null);
				TextView title = (TextView)v.findViewById(R.id.tag_title);
				TextView subTitle = (TextView)v.findViewById(R.id.tag_sub_title);
				title.setText(obj[0].toString());
				subTitle.setText(obj[1].toString());
			}else if(obj.length == 3){
				v = LayoutInflater.from(VideoCBListActivity.this).inflate(R.layout.cb_videoview_list_item_content,null);
				ImageView contentImg = (ImageView)v.findViewById(R.id.cv_contentImg);
				TextView contentTitle = (TextView)v.findViewById(R.id.cv_content_title);
				TextView desc = (TextView)v.findViewById(R.id.cv_description);
				contentImg.setImageResource((int)obj[0]);
				contentTitle.setText((int) obj[1]);
				desc.setText((int)obj[2]);
			}else{
				return null;
			}
			return v;
		}
	}

	private List<Object[]> getDatas(){
		List<Object[]> list = new ArrayList<>();
		list.add(new Object[]{"布道","传扬真理"});
		list.add(new Object[]{R.drawable.video_image_test,R.string.cb_content_t0,R.string.cb_content_0});
		list.add(new Object[]{R.drawable.video_image_test,R.string.cb_content_t1,R.string.cb_content_1});
		list.add(new Object[]{R.drawable.video_image_test,R.string.cb_content_t2,R.string.cb_content_2});
		list.add(new Object[]{"讨论","多人谈论"});
		list.add(new Object[]{R.drawable.video_image_test,R.string.cb_content_t3,R.string.cb_content_3});
		list.add(new Object[]{R.drawable.video_image_test,R.string.cb_content_t4,R.string.cb_content_4});
		list.add(new Object[]{"新闻","聚集全球热点"});
		list.add(new Object[]{R.drawable.video_image_test,R.string.cb_content_t5,R.string.cb_content_5});
		list.add(new Object[]{"音乐","与音乐相关的励志人物"});
		list.add(new Object[]{R.drawable.video_image_test,R.string.cb_content_t6,R.string.cb_content_6});
		list.add(new Object[]{"启发","属灵的启示"});
		list.add(new Object[]{R.drawable.video_image_test,R.string.cb_content_t8,R.string.cb_content_8});
		list.add(new Object[]{"主题","不同的主题，不同的人，不一样的风格"});
		list.add(new Object[]{R.drawable.video_image_test,R.string.cb_content_t9,R.string.cb_content_9});
		list.add(new Object[]{R.drawable.video_image_test,R.string.cb_content_t10,R.string.cb_content_10});
		list.add(new Object[]{"见证","讲述每个人的心声，每个人的信仰历程"});
		list.add(new Object[]{R.drawable.video_image_test,R.string.cb_content_t11,R.string.cb_content_11});
		list.add(new Object[]{R.drawable.video_image_test,R.string.cb_content_t12,R.string.cb_content_12});
		list.add(new Object[]{"健康","饮食营养，料理烹饪，健康疗法"});
		list.add(new Object[]{R.drawable.video_image_test,R.string.cb_content_t13,R.string.cb_content_13});
		list.add(new Object[]{"少儿频道","专为孩童预备的真理节目"});
		list.add(new Object[]{R.drawable.video_image_test,R.string.cb_content_t14,R.string.cb_content_14});
		return list;
	}
}
