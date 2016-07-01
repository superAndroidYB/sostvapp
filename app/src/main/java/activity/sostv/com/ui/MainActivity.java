package activity.sostv.com.ui;

import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.reflect.Field;
import java.util.List;

import activity.sostv.com.adapter.MenuAdapter;
import activity.sostv.com.adapter.OnMenuListClickListener;
import activity.sostv.com.fragmet.NewsContentListFragment;
import activity.sostv.com.global.Constants;
import activity.sostv.com.global.WebServiceHelper;
import activity.sostv.com.model.SosBooks;
import activity.sostv.com.model.SosUser;
import activity.sostv.com.model.SostvRequest;
import activity.sostv.com.model.SostvResponse;
import activity.sostv.com.sostvapp.R;
import activity.sostv.com.widght.DrawerMenuContent;
import activity.sostv.com.widght.RoundImageView;
import io.bxbxbai.common.T;
import io.bxbxbai.common.utils.GlobalExecutor;
import io.bxbxbai.common.utils.PrefUtils;

public class MainActivity extends BaseActivity {

    @ViewInject(R.id.drawerLayout)
    private DrawerLayout mDrawerLayout;
    @ViewInject(R.id.nv_header)
    private NavigationView navigationView;
    private boolean isDrawerOpened;
    @ViewInject(R.id.drawer_list)
    private ListView listView;
    private RoundImageView imageView;
    private TextView textView;

    private BitmapUtils bitmapUtils;

    private SostvApplication mApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        mApplication = (SostvApplication)getApplication();
        bitmapUtils = new BitmapUtils(this);

        initToolBar();
        initToolbarAndDrawer();

        View headerView = navigationView.getHeaderView(0);
        imageView = (RoundImageView)headerView.findViewById(R.id.header_image_view);
        textView = (TextView)headerView.findViewById(R.id.header_text_view);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mApplication.getIsLogin()){
                    UserActivity.start(MainActivity.this);
                }else{
                    LoginActivity.start(MainActivity.this);
                }
            }
        });

        DrawerMenuContent content = new DrawerMenuContent(this);
        listView.setAdapter(new MenuAdapter(this, content.getItems()));
        listView.setOnItemClickListener(new OnMenuListClickListener(this, mDrawerLayout));

        getSupportFragmentManager().beginTransaction().add(R.id.container,
                NewsContentListFragment.newInstance()).commit();
        setOverflowShowAlways();

        //第一次启动，会打开抽屉菜单
        GlobalExecutor.get().postOnWorkThread(new Runnable() {
            @Override
            public void run() {
                if ((boolean) PrefUtils.getValue(MainActivity.this, PrefUtils.KEY_FIRST_ENTER, true)) {
                    //loadChildData();
                    GlobalExecutor.MAIN_HANDLER.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            openDrawer();
                        }
                    }, 1500);
                    PrefUtils.setValue(MainActivity.this, PrefUtils.KEY_FIRST_ENTER, false);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showUserInfo();
    }

    private void showUserInfo(){
        SosUser user = mApplication.getUser();
        if(user != null){
            textView.setText(user.getUserCname());
            bitmapUtils.display(imageView,user.getImageUrl());
        }

    }

    private void loadChildData(){

        LoadBooksData loadBookTool = new LoadBooksData();
        SostvRequest request = new SostvRequest();
        request.setProperty(0, Constants.WHITE_BOOKS);
        request.setProperty(1, "FragmentBooks_WhitBook");
        loadBookTool.execute(request);
    }

    private class LoadBooksData extends WebServiceHelper {

        @Override
        protected void onPostExecute(SostvResponse result) {
            if(result == null) {
                return;
            }
            if(!Constants.CODE.equals(result.getReturnCode())){
                T.showToast("服务器内部错误,请稍后重试");
                return;
            }
            String data = result.getReturnData();
            Gson gson = new Gson();
            List<SosBooks> list = gson.fromJson(data, new TypeToken<List<SosBooks>>(){}.getType());
            try{
                db.saveAll(list);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            //貌似mDrawerLayout不能适应沉浸式通知栏的fitSystemWindow属性，必须手动设置它的topMargin值
            SystemBarTintManager.SystemBarConfig config = mTintManager.getConfig();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mDrawerLayout.getLayoutParams();
            params.topMargin = config.getStatusBarHeight();
        }
    }


    private boolean openDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    private void setOverflowShowAlways() {
        ViewConfiguration conf = ViewConfiguration.get(this);
        try {
            Field menuKeyField = conf.getClass().getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(conf, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initToolbarAndDrawer() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (materialMenu.getState().equals(MaterialMenuDrawable.IconState.BURGER)) {
                    materialMenu.animatePressedState(MaterialMenuDrawable.IconState.ARROW);
                    openDrawer();
                } else {
                    materialMenu.animatePressedState(MaterialMenuDrawable.IconState.BURGER);
                    closeDrawer();
                }
            }
        });

        mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                materialMenu.setTransformationOffset(MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        isDrawerOpened ? 2 - slideOffset : slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                isDrawerOpened = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawerOpened = false;
            }

        });
        materialMenu.setState(MaterialMenuDrawable.IconState.BURGER);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        closeDrawer();
        // Otherwise, it may return to the previous fragment stack
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            // Lastly, it will rely on the system behavior for back
            super.onBackPressed();
        }
    }

    public boolean closeDrawer() {
        // If the drawer is open, back will close it
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
            return true;
        }
        return false;
    }
}
