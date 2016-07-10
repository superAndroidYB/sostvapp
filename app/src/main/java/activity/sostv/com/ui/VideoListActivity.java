package activity.sostv.com.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import activity.sostv.com.sostvapp.R;

public class VideoListActivity extends BaseActivity {

    private String videoType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        parseIntent(getIntent());
    }

    private void parseIntent(Intent i){
        videoType = (String)i.getStringExtra("VIDEO_TYPE");
    }

    public static void start(Context context, String videoType){
        Intent intent = new Intent(context, VideoListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("VIDEO_TYPE",videoType);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
