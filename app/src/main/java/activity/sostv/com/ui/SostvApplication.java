package activity.sostv.com.ui;

import android.app.Application;

import activity.sostv.com.model.SosUser;
import io.bxbxbai.common.T;

public class SostvApplication extends Application {

	private SosUser user;
	private Boolean isLogin;


	@Override
	public void onCreate() {
		super.onCreate();
		T.init(this);
	}
	public SosUser getUser() {
		return user;
	}

	public void setUser(SosUser user) {
		this.user = user;
	}

	public Boolean getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(Boolean isLogin) {
		this.isLogin = isLogin;
	}
}
