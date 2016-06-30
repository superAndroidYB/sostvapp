package activity.sostv.com.widght;

import android.content.Context;
import android.support.annotation.DrawableRes;

import java.util.ArrayList;
import java.util.List;

import activity.sostv.com.sostvapp.R;
import activity.sostv.com.ui.AboutUsActivity;
import activity.sostv.com.ui.VideoHomeActivity;

/**
 * Created by baia on 15/3/28.
 *
 * @author bxbxbai
 */
public class DrawerMenuContent {

    public static final int DRAWER_MENU_COUNT = 5;

    private static final String FIELD_TITLE = "title";
    private static final String FIELD_ICON = "icon";

    private List<DrawerItem> items;
    private Class[] activities;

    public DrawerMenuContent(Context context) {
        activities = new Class[DRAWER_MENU_COUNT];
        items = new ArrayList<>(DRAWER_MENU_COUNT);

        activities[0] = VideoHomeActivity.class;
        items.add(new DrawerItem(R.id.menu_week_video, context.getString(R.string.videoHome),
                R.mipmap.menu_icon_shiping));

        activities[1] = AboutUsActivity.class;
        items.add(new DrawerItem(R.id.menu_feedback, context.getString(R.string.feedBack),
                R.mipmap.menu_icon_yijian));

        activities[2] = AboutUsActivity.class;
        items.add(new DrawerItem(R.id.menu_about_us, context.getString(R.string.aboutUs),
                R.mipmap.menu_icon_women));

        /*activities[3] = WhiteBookActivity.class;
        items.add(new DrawerItem(R.id.menu_white_book, context.getString(R.string.whiteBook),
                R.drawable.btn_tab_shuji_selector));

        activities[4] = AboutUsActivity.class;
        items.add(new DrawerItem(R.id.menu_life_book, context.getString(R.string.lifeBook),
                R.drawable.btn_tab_shuji_selector));*/


    }

    public List<DrawerItem> getItems() {
        return items;
    }

    public Class getActivity(int pos) {
        if (0 <= pos && pos < activities.length) {
            return activities[pos];
        }
        return null;
    }


    public int getPosition(Class clazz) {
        for (int i = 0; i < activities.length; i++) {
            if (activities[i].equals(clazz)) {
                return i;
            }
        }
        return -1;
    }

    public static class DrawerItem {
        public int id;
        public String title;
        public int icon;

        private DrawerItem(int id, String title, @DrawableRes int icon) {
            this.id = id;
            this.title = title;
            this.icon = icon;
        }
    }
}
