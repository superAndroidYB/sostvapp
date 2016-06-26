package activity.sostv.com.ui;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;
import com.lidroid.xutils.DbUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import activity.sostv.com.global.WebServiceHelper;
import activity.sostv.com.sostvapp.R;
import butterknife.ButterKnife;
import io.bxbxbai.common.StopWatch;

/**
 * Created by Administrator on 2016/6/3.
 */
public class BaseActivity extends AppCompatActivity{

    public SharedPreferences sp = null;
    protected DbUtils db;

    protected Toolbar toolbar;
    protected MaterialMenuIconToolbar materialMenu;
    protected SystemBarTintManager mTintManager;

    protected WebServiceHelper taskTool;

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initToolBar();
    }

    protected void initToolBar() {
        this.toolbar = ButterKnife.findById(this, R.id.toolbar);
        if (toolbar != null) {
            this.setSupportActionBar(this.toolbar);
            this.materialMenu = new MaterialMenuIconToolbar(this, -1, MaterialMenuDrawable.Stroke.REGULAR) {
                public int getToolbarViewId() {
                    return R.id.toolbar;
                }
            };
            this.materialMenu.setState(MaterialMenuDrawable.IconState.ARROW);
            this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    BaseActivity.this.finish();
                }
            });
        }
        db = DbUtils.create(this);
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.setTranslucentStatus(true);
        this.mTintManager = new SystemBarTintManager(this);
        this.mTintManager.setStatusBarTintEnabled(true);
        this.mTintManager.setTintColor(this.getResources().getColor(R.color.RoyalBlue));
    }

    protected void setTitle(String title) {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public void setTitle(@StringRes int resId) {
        this.setTitle(this.getString(resId));
    }


    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window w = this.getWindow();
        WindowManager.LayoutParams params = w.getAttributes();
        if (on) {
            params.flags |= 67108864;
        } else {
            params.flags &= -67108865;
        }

        w.setAttributes(params);
    }

    protected void onDestroy() {
        super.onDestroy();
        if (taskTool != null && taskTool.getStatus() != AsyncTask.Status.FINISHED)
            taskTool.cancel(true);
    }


    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        StopWatch.log("level: " + level);
    }
}
