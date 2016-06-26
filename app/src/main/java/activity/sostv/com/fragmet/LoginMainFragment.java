package activity.sostv.com.fragmet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import activity.sostv.com.sostvapp.R;

/**
 * Created by Administrator on 2016/6/22.
 */
public class LoginMainFragment extends Fragment implements View.OnClickListener {

    private static final String LOGIN = "LOGIN";
    private static final String REGISTER = "REGISTER";

    @ViewInject(R.id.third_qq_login)
    private ImageView btnQQ;
    @ViewInject(R.id.third_weixin_login)
    private ImageView btnWeixin;
    @ViewInject(R.id.third_weibo_login)
    private ImageView btnWeibo;
    @ViewInject(R.id.register_btn)
    private Button btnReg;
    @ViewInject(R.id.login_btn)
    private Button btnLogin;

    public static LoginMainFragment newInstance() {
        LoginMainFragment fragment = new LoginMainFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login_main, container, false);
        ViewUtils.inject(this, v);

        btnQQ.setOnClickListener(this);
        btnWeixin.setOnClickListener(this);
        btnWeibo.setOnClickListener(this);

        btnReg.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.third_qq_login:
                break;
            case R.id.third_weixin_login:
                break;
            case R.id.third_weibo_login:
                break;
            case R.id.register_btn:
                break;
            case R.id.login_btn:
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.login_frame_layout, LoginFragment.newInstance()).commit();
                break;
        }
    }
}
