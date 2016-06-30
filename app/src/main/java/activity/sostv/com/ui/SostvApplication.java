package activity.sostv.com.ui;

import android.app.Application;

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
    }

    private void loadSosUser() {
        user = (SosUser) mCache.getAsObject("app_user");
        if (user != null) {
            isLogin = true;
        }
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
