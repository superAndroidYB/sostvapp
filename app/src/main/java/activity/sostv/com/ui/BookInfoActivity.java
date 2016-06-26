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

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import activity.sostv.com.sostvapp.R;
import activity.sostv.com.widght.SostvCollapsibleTextView;

public class BookInfoActivity extends BaseActivity {

    @ViewInject(R.id.chapter_listview)
    private ListView chapterListview;
    @ViewInject(R.id.book_desc_textview)
    private SostvCollapsibleTextView descTextView;

    private String titles[] = new String[87];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        initToolBar();
        setTitle("历代愿望");
        ViewUtils.inject(this);

        String str = "\u3000\u3000本书讲述耶稣的生平故事，对应《圣经·新约》的前4卷。共分九篇，内容包括：耶稣降生、初试锋芒、耶稣传道的故事、被拒绝的救世主、被钉十字架等.本书讲述耶稣的生平故事，对应《圣经·新约》的前4卷。共分九篇，内容包括：耶稣降生、初试锋芒、耶稣传道的故事、被拒绝的救世主、被钉十字架等.本书讲述耶稣的生平故事，对应《圣经·新约》的前4卷。共分九篇，内容包括：耶稣降生、初试锋芒、耶稣传道的故事、被拒绝的救世主、被钉十字架等";
        descTextView.setDesc(str, TextView.BufferType.NORMAL);

        init();
    }

    private void init() {
        for (int i = 0; i < 87; i++) {
            titles[i] = "第" + (i + 1) + "章";
        }
        chapterListview.setAdapter(new ChapterListAdaper(this));
        //chapterListview.setOnItemClickListener(this);
    }

    class ChapterListAdaper extends BaseAdapter {

        private Context mContext;
        private ImageView gnIcon;
        private TextView gnTitle;

        public ChapterListAdaper(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.chapter_list_item, parent, false);
                gnIcon = (ImageView) convertView.findViewById(R.id.gnIcon);
                gnTitle = (TextView) convertView.findViewById(R.id.gnTitle);
                gnIcon.setImageResource(R.mipmap.content_flag);
                gnTitle.setText(titles[position]);
            } else {
                gnIcon = (ImageView) convertView.findViewById(R.id.gnIcon);
                gnTitle = (TextView) convertView.findViewById(R.id.gnTitle);
                gnIcon.setImageResource(R.mipmap.content_flag);
                gnTitle.setText(titles[position]);
            }

            return convertView;
        }

    }
}
