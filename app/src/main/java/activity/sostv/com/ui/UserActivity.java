package activity.sostv.com.ui;

import android.content.Context;
import android.content.Intent;
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

public class UserActivity extends BaseActivity {

    //,"设置","关于我们","意见反馈""我的缓存",
    private String titles[] = new String[]{"我的信息","我的收藏","设置"};
    private int images[] = new int[]{R.mipmap.sos_mygn,R.mipmap.sos_shoucang,R.mipmap.sos_shezhi};
    @ViewInject(R.id.user_gongneng_list)
    private ParallaxListView parallaxListView;

    public static void start(Context context){
        Intent intent = new Intent(context,UserActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ViewUtils.inject(this);
        initToolBar();
        setTitle(R.string.title_user_activity);

        final View headerView = View.inflate(this,R.layout.parallax_header_view,null);
        final ImageView imageView = (ImageView)headerView.findViewById(R.id.parallax_img);
        parallaxListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        parallaxListView.addHeaderView(headerView);
        parallaxListView.setAdapter(new GongNengListAdaper(this));

        headerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                parallaxListView.setParallaxImage(imageView);
                headerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    class GongNengListAdaper extends BaseAdapter {

        private Context mContext;
        private ImageView gnIcon;
        private TextView gnTitle;

        public GongNengListAdaper(Context mContext){
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Object getItem(int position) {
            return titles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.gnlist_item, parent, false);
                gnIcon = (ImageView) convertView.findViewById(R.id.gnIcon);
                gnTitle = (TextView) convertView.findViewById(R.id.gnTitle);
                gnIcon.setImageResource(images[position]);
                gnTitle.setText(titles[position]);
            }else{
                gnIcon = (ImageView) convertView.findViewById(R.id.gnIcon);
                gnTitle = (TextView) convertView.findViewById(R.id.gnTitle);
                gnIcon.setImageResource(images[position]);
                gnTitle.setText(titles[position]);
            }

            return convertView;
        }

    }
}
