package activity.sostv.com.widght;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import activity.sostv.com.model.SostvSlideImageEntity;
import activity.sostv.com.sostvapp.R;

public class SostvSlideImageView extends FrameLayout {

    //图片异步加载插件
    private BitmapUtils bitmapUtils;

    private List<SostvSlideImageEntity> mImages;

    //当前页下标
    private int currentItem = 0;

    private Context context;

    //放轮播图片的ImageView 的list
    private List<ImageView> imageViewsList;

    //放轮播图片的TextView 的list
    private List<TextView> textViewList;

    //放圆点的View的list
    private List<View> dotViewsList;

    private ViewPager viewPager;

    private TextView tvTitle;

    //定时任务
    private ScheduledExecutorService scheduledExecutorService;

    //自动轮播启用开关
    private final static boolean isAutoPlay = true;

    private static final long SLIDE_PERIOD = 5;

    public SostvSlideImageView(Context context) {
        this(context, null);
    }

    public SostvSlideImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SostvSlideImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        bitmapUtils = new BitmapUtils(context);
        initData();
        if (isAutoPlay) {
            startPlay();
        }
    }

    //消息传递机制 接收子线程的消息并更新UI
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentItem);
        }
    };

    private void startPlay(){
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 3, SLIDE_PERIOD, TimeUnit.SECONDS);
    }

    /**
     * 执行轮播图切换任务
     */
    private class SlideShowTask implements Runnable {

        @Override
        public void run() {
            synchronized (viewPager) {
                currentItem = (currentItem + 1) % imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }

    }

    /**
     * 初始化相关数据
     */
    private void initData(){
        imageViewsList = new ArrayList<ImageView>();
        textViewList = new ArrayList<TextView>();
        dotViewsList = new ArrayList<View>();

        new GetListTask().execute("www.baidu.com");
    }

    /**
     * Params 启动任务执行的输入参数，比如HTTP请求的URL。
     Progress 后台任务执行的百分比。
     Result 后台执行任务最终返回的结果，比如String。
     * @author Administrator
     *
     */
    class GetListTask extends AsyncTask<String, Integer, List<SostvSlideImageEntity>> {

        @Override
        protected List<SostvSlideImageEntity> doInBackground(String... params) {
            mImages = new ArrayList<SostvSlideImageEntity>();
            SostvSlideImageEntity entity1 = new SostvSlideImageEntity();
            entity1.setTitle("脚前的灯");
            entity1.setImgURL("http://img.taopic.com/uploads/allimg/120101/8117-12010114091483.jpg");
            entity1.setLinkURL("http://sostvmedia.oss-cn-hangzhou.aliyuncs.com/2015-09-18-kids-33.mp4");
            SostvSlideImageEntity entity2 = new SostvSlideImageEntity();
            entity2.setTitle("路上的光");
            entity2.setImgURL("http://pic.58pic.com/58pic/14/74/66/04J58PICxzu_1024.jpg");
            entity2.setLinkURL("http://sostvmedia.oss-cn-hangzhou.aliyuncs.com/2015-09-18-kids-33.mp4");
            SostvSlideImageEntity entity3 = new SostvSlideImageEntity();
            entity3.setTitle("生命的粮");
            entity3.setImgURL("http://img4.imgtn.bdimg.com/it/u=1583481888,2937256478&fm=21&gp=0.jpg");
            entity3.setLinkURL("http://sostvmedia.oss-cn-hangzhou.aliyuncs.com/2015-09-18-kids-33.mp4");

            mImages.add(entity1);
            mImages.add(entity2);
            mImages.add(entity3);
            return mImages;
        }

        @Override
        protected void onPostExecute(List<SostvSlideImageEntity> result) {
            super.onPostExecute(result);
            if(!result.isEmpty()){
                initUI(context);
            }
        }
    }

    /**
     * 初始化Views等UI
     */
    private void initUI(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sostv_slideshow_layout, this, true);

        LinearLayout dotLayout = (LinearLayout) findViewById(R.id.ll_dot_layout);
        dotLayout.removeAllViews();

        tvTitle = (TextView)findViewById(R.id.tvTitle);

        for (int i = 0; i < mImages.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setTag(mImages.get(i).getImgURL());

            //图片摆放方式 ，充满控件窗口
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewsList.add(imageView);
            
            /*
            TextView textView = new TextView(context);
            //textView.setTextColor(R.color.white);
            //textView.setTag(mImages.get(i).getTitle());
            textView.setText(mImages.get(i).getTitle());
            textViewList.add(textView);*/

            ImageView dotView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 4;
            params.rightMargin = 4;
            dotLayout.addView(dotView,params);
            dotViewsList.add(dotView);
        }

        viewPager = (ViewPager) findViewById(R.id.vp_image_slide);
        viewPager.setFocusable(true);

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }

    /**
     * 填充ViewPager的页面适配器
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViewsList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView view = imageViewsList.get(position);

            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("你点击了图片", "路径" + mImages.get(position).getLinkURL());
                    jumpVideoView(position);
                }
            });


            bitmapUtils.display(view,mImages.get(position).getImgURL());
            container.addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }
    }

    private void jumpVideoView(int position){
        //Intent intent = new Intent(context,VideoViewActivity.class);
        //Bundle bundle = new Bundle();
        //bundle.putString("videoUrl", mImages.get(position).getLinkURL());
        //intent.putExtras(bundle);
        //context.startActivity(intent);
    }
    /**
     * ViewPager的监听器
     * 当ViewPager中页面的状态发生改变时调用
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case 1:// 手势滑动，空闲中
                    isAutoPlay = false;
                    break;
                case 2:// 界面切换中
                    isAutoPlay = true;
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
                    // 当前为最后一张，此时从右向左滑，则切换到第一张
                    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                        viewPager.setCurrentItem(0);
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
                        viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int pos) {
            currentItem = pos;
            tvTitle.setText(mImages.get(pos).getTitle());
            for (int i = 0; i < dotViewsList.size(); i++) {
                if (i == pos) {
                    ((View) dotViewsList.get(pos)).setBackgroundResource(R.mipmap.dot_focus);
                } else {
                    ((View) dotViewsList.get(i)).setBackgroundResource(R.mipmap.dot_blur);
                }
            }
        }

    }
}
