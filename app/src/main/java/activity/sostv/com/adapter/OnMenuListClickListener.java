package activity.sostv.com.adapter;

import android.app.Activity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;

import activity.sostv.com.sostvapp.R;
import activity.sostv.com.ui.AboutUsActivity;
import activity.sostv.com.ui.FeedbackActivity;
import activity.sostv.com.ui.LifeBookActivity;
import activity.sostv.com.ui.VideoHomeActivity;
import activity.sostv.com.ui.WhiteBookActivity;
import activity.sostv.com.widght.DrawerMenuContent;
import io.bxbxbai.common.T;

/**
 *
 * 点击事件
 * @author bxbxbai
 */
public class OnMenuListClickListener implements AdapterView.OnItemClickListener {

    private Activity mActivity;
    private DrawerLayout mDrawerLayout;

    public OnMenuListClickListener(Activity activity, DrawerLayout drawerLayout) {
        mActivity = activity;
        mDrawerLayout = drawerLayout;
        T.init(mActivity);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DrawerMenuContent.DrawerItem item = (DrawerMenuContent.DrawerItem) view.getTag(R.id.key_data);

        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        }

        switch (item.id) {
            case R.id.menu_week_video :
                VideoHomeActivity.start(mActivity);
                break;

            case R.id.menu_white_book:
                WhiteBookActivity.start(mActivity);
                break;

            case R.id.menu_about_us :
                AboutUsActivity.start(mActivity);
                break;

            case R.id.menu_feedback :
                FeedbackActivity.start(mActivity);
                break;

            case R.id.menu_life_book :
                LifeBookActivity.start(mActivity);
                break;

            default:break;
        }
    }
}
