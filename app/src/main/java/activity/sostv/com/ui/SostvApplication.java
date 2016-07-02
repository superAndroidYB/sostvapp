package activity.sostv.com.ui;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import activity.sostv.com.global.ACache;
import activity.sostv.com.model.SosUser;
import io.bxbxbai.common.T;

public class SostvApplication extends Application {

    private SosUser user = null;
    private boolean isLogin = false;
    private ACache mCache;

    @Override
    public void onCreate() {
        super.onCreate();
        T.init(this);
        mCache = ACache.get(this);
        loadSosUser();
        regPush();
    }

    private void loadSosUser() {
        user = (SosUser) mCache.getAsObject("app_user");
        if (user != null) {
            isLogin = true;
        }
    }

    private void regPush(){
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.enable(new IUmengRegisterCallback() {
            @Override
            public void onRegistered(final String device_token) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("====device_token=====", device_token);
                    }
                });
            }
        });
    }


    public SosUser getUser() {
        return user;
    }

    public void setUser(SosUser user) {
        this.user = user;
    }

    public boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public ACache getmCache() {
        return mCache;
    }

    public void setmCache(ACache mCache) {
        this.mCache = mCache;
    }
}
