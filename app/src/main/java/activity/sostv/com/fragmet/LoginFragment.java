package activity.sostv.com.fragmet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import activity.sostv.com.global.ACache;
import activity.sostv.com.global.Constants;
import activity.sostv.com.global.WebServiceHelper;
import activity.sostv.com.model.SosUser;
import activity.sostv.com.model.SostvRequest;
import activity.sostv.com.model.SostvResponse;
import activity.sostv.com.sostvapp.R;
import activity.sostv.com.ui.SostvApplication;
import io.bxbxbai.common.T;

/**
 * Created by Administrator on 2016/6/23.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private static final String LOGIN = "LOGIN";
    private static final String REGISTER = "REGISTER";

    @ViewInject(R.id.login_edtId)
    private EditText editTextId;
    @ViewInject(R.id.login_edtPwd)
    private EditText editTextPwd;
    @ViewInject(R.id.login_btnLogin)
    private Button loginButton;

    private SostvApplication application;
    private ACache mCache;

    public static LoginFragment newInstance() {
        LoginFragment lf = new LoginFragment();
        return lf;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        ViewUtils.inject(this, v);
        loginButton.setOnClickListener(this);

        application = (SostvApplication) getActivity().getApplication();
        mCache = application.getmCache();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void login() {
        String username = editTextId.getText().toString();
        String password = editTextPwd.getText().toString();
        if ("".equals(username) || username == null) {
            T.showToast("请输入用户名或邮箱");
            return;
        }
        if ("".equals(password) || password == null) {
            T.showToast("请输入密码");
            return;
        }

        SosUser user = new SosUser();
        user.setUserName(username);
        user.setLoginOrReg(LOGIN);
        user.setUserPassword(password);

        Gson gson = new Gson();
        String data = gson.toJson(user);
        SostvRequest request = new SostvRequest();
        request.setProperty(0, Constants.LOGINREG);
        request.setProperty(1, "LoginFragment");
        request.setProperty(2, data);

        LoginService loginService = new LoginService();
        loginService.execute(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btnLogin:
                login();
                break;
            default:
                break;
        }
    }

    private class LoginService extends WebServiceHelper {
        @Override
        protected void onPostExecute(SostvResponse result) {
            if (result == null) {
                return;
            }
            if (!Constants.CODE.equals(result.getReturnCode())) {
                T.showToast("服务器内部错误,请稍后重试");
                return;
            }
            if ("LOGIN_FAILURE".equals(result.getReturnData())) {
                T.showToast("登录失败");
            } else {
                Gson gson = new Gson();
                SosUser user = gson.fromJson(result.getReturnData(), SosUser.class);
                application.setUser(user);
                application.setIsLogin(true);
                mCache.put("app_user", user, 60 * 60 * 24 * 30);
                getActivity().finish();
            }

        }
    }
}
