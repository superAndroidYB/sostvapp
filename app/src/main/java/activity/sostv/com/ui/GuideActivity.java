package activity.sostv.com.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import activity.sostv.com.sostvapp.R;

public class GuideActivity extends BaseActivity {

    private ViewPager guideViewPager;
    private Button guideBtn;
    private LinearLayout pointView;
    private View vRedPoint;

    private int mPointWidth;

    private static final int[] mImageIds = new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
    private ArrayList<ImageView> guideViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initViewPager();
    }

    /**
     * 初始化页面
     */
    private void initViewPager(){
        guideViewPager = (ViewPager)findViewById(R.id.guide_viewpager);
        pointView = (LinearLayout)findViewById(R.id.ll_proint_view);
        vRedPoint = findViewById(R.id.v_red_point);
        guideBtn = (Button) findViewById(R.id.guide_button);

        guideViewList = new ArrayList<ImageView>();
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(mImageIds[i]);
            guideViewList.add(iv);
        }
        guideViewPager.setAdapter(new GuideAdapter());

        for (int i = 0; i < mImageIds.length; i++) {
            View v = new View(this);
            v.setBackgroundResource(R.drawable.guide_gray_point_shape);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15,15);
            if(i>0){
                params.leftMargin = 15;
            }
            v.setLayoutParams(params);
            pointView.addView(v);
        }



        //获取视图树
        pointView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            //当layout方法结束之后回调此方法
            @Override
            public void onGlobalLayout() {
                mPointWidth = pointView.getChildAt(1).getLeft() - pointView.getChildAt(0).getLeft();

                pointView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        guideViewPager.setOnPageChangeListener(new GuidePageListener());
        guideBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sp = getSharedPreferences("config", MODE_PRIVATE);
                sp.edit().putBoolean("is_guide_showed", true).commit();
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });
    }



    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(guideViewList.get(position));
            return guideViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

    }

    class GuidePageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {

            float len = mPointWidth*positionOffset + position*mPointWidth;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vRedPoint.getLayoutParams();//获取红点的当前布局参数
            params.leftMargin = (int) len;
            vRedPoint.setLayoutParams(params);
        }

        @Override
        public void onPageSelected(int position) {
            if(guideViewList.size()-1 == position){
                guideBtn.setVisibility(View.VISIBLE);
            }else{
                guideBtn.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
