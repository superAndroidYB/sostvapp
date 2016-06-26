package activity.sostv.com.ui;

import android.os.Bundle;

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

public class VideoXLListActivity extends BaseActivity {

	@ViewInject(R.id.sostvXlVideoView)
	private SostvGridView gridView;
	
	private SosHomeZxtjGridViewAdapter adapter;
	
	private List<SosVideo> datas = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xl_videoview);
		ViewUtils.inject(this);
		initToolBar();
		setTitle(R.string.btn_xl);
		loadData();
	}
	
	private void loadData(){
		datas = new ArrayList<SosVideo>();
		adapter = new SosHomeZxtjGridViewAdapter(this, datas);
		gridView.setAdapter(adapter);
		
		LoadData loadData = new LoadData();
		SostvRequest request = new SostvRequest();
		request.setProperty(0, Constants.VIDEO_XLJZ);
		request.setProperty(1, "VideoXLListActivity");
		loadData.execute(request);
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
