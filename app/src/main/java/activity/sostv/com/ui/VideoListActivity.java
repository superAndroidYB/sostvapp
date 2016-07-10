package activity.sostv.com.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import activity.sostv.com.global.Constants;
import activity.sostv.com.global.WebServiceHelper;
import activity.sostv.com.model.SosVideo;
import activity.sostv.com.model.SostvRequest;
import activity.sostv.com.model.SostvResponse;
import activity.sostv.com.sostvapp.R;
import io.bxbxbai.common.T;

public class VideoListActivity extends BaseActivity {

    private String videoType;
    private List<SosVideo> datas;

    @ViewInject(R.id.cb_video_list)
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        parseIntent(getIntent());
        initUI();
    }

    private void initUI(){
        datas = new ArrayList<>();
        if(videoType == null || "".equals(videoType))
            return;
        PullDataService service = new PullDataService();
        SostvRequest request = new SostvRequest(Constants.VIDEO_LIST,"VideoListActivity",videoType);
        service.execute(request);
    }

    private void parseIntent(Intent i){
        videoType = (String)i.getStringExtra("VIDEO_TYPE");
    }

    class PullDataService extends WebServiceHelper{

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
            if(!list.isEmpty()){
                datas.clear();
                datas = list;

            }
        }
    }

    class VideoListViewAdapter extends BaseAdapter{

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
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    public static void start(Context context, String videoType){
        Intent intent = new Intent(context, VideoListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("VIDEO_TYPE",videoType);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
