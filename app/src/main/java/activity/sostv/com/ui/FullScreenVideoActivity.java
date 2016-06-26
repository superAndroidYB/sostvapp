package activity.sostv.com.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.Date;

import activity.sostv.com.model.SosVideo;
import activity.sostv.com.sostvapp.R;
import activity.sostv.com.widght.SostvMediaController;
import io.bxbxbai.common.T;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;

public class FullScreenVideoActivity extends Activity implements
        View.OnClickListener, MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener, Runnable{

    public static SharedPreferences sp = null;

    @ViewInject(R.id.vv_sostv)
    private VideoView videoView;
    @ViewInject(R.id.probar)
    private ImageView progressBar;
    @ViewInject(R.id.download_rate)
    private TextView downloadRateView;
    @ViewInject(R.id.load_rate)
    private TextView loadRateView;

    private SostvMediaController mediaController;
    private static SosVideo mSosVideo;
    private Uri uri;
    private long mExitTime;
    private static final int TIME = 0;
    private static final int BATTERY = 1;

    public static void start(Context context, SosVideo video){
        Intent intent = new Intent(context, FullScreenVideoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("sosVideo", video);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME:
                    mediaController.setTime(msg.obj.toString());
                    mediaController.setFileName(mSosVideo.getVideoName());
                    break;
                case BATTERY:
                    mediaController.setBattery(msg.obj.toString());
                    mediaController.setFileName(mSosVideo.getVideoName());
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full_screen_video);
        ViewUtils.inject(this);

        sp = getSharedPreferences("VIDEO_TIME",MODE_PRIVATE);

        parseIntent(getIntent());
        initializerVideoView();
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

    public static void editSeekTime(long time){
        sp.edit().putLong(Integer.toString(mSosVideo.getId()),time).commit();
    }

    private void initializerVideoView(){
        long seektime = sp.getLong(Integer.toString(mSosVideo.getId()),0);
        mediaController = new SostvMediaController(this, videoView, this);
        Log.d("你进入了播放视频的Activity", "路径" + mSosVideo.getVideoSdUrl());
        if (mSosVideo == null) {
            T.showToast("视频地址解析出错，请联系APP开发人员");
            finish();
            return;
        } else {
            uri = Uri.parse(mSosVideo.getVideoSdUrl());
            videoView.setVideoURI(uri);
            videoView.requestFocus();
            videoView.setOnInfoListener(this);
            videoView.setOnBufferingUpdateListener(this);
            videoView.setMediaController(mediaController);
            videoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);// 高画质
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setPlaybackSpeed(1.0f);
                }
            });
            if(seektime > 0){
                videoView.seekTo(seektime);
            }
        }
    }

    public void registerBoradcastReceiver() {
        //注册电量广播监听
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryBroadcastReceiver, intentFilter);
    }

    private BroadcastReceiver batteryBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
                //获取当前电量
                int level = intent.getIntExtra("level", 0);
                //电量的总刻度
                int scale = intent.getIntExtra("scale", 100);
                //把它转成百分比
                Message msg = new Message();
                msg.obj = (level*100)/scale+"";
                msg.what = BATTERY;
                mHandler.sendMessage(msg);
            }
        }
    };

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        loadRateView.setText(percent + "%");
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerBoradcastReceiver();
        new Thread(this).start();
        Log.d("videoView=========", "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
        editSeekTime(videoView.getCurrentPosition());
        Log.d("videoView=========", "onPause called+"+videoView.getCurrentPosition());
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            // 媒体播放器内部暂时暂停播放缓冲区更多的数据。
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (videoView.isPlaying()) {
                    videoView.pause();
                    progressBar.setVisibility(View.VISIBLE);
                    downloadRateView.setText("");
                    loadRateView.setText("");
                    downloadRateView.setVisibility(View.VISIBLE);
                    loadRateView.setVisibility(View.VISIBLE);
                }
                break;
            // 媒体播放器后恢复回放填充缓冲区
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                videoView.start();
                progressBar.setVisibility(View.GONE);
                downloadRateView.setVisibility(View.GONE);
                loadRateView.setVisibility(View.GONE);
                break;
            // 媒体播放器下载到缓冲区
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                downloadRateView.setText("" + extra + "kb/s" + "  ");
                break;
        }
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次离开视频", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                editSeekTime(videoView.getCurrentPosition());
                FullScreenVideoActivity.this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void run() {
        while (true) {
            //时间读取线程
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String str = sdf.format(new Date());
            Message msg = new Message();
            msg.obj = str;
            msg.what = TIME;
            mHandler.sendMessage(msg);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
