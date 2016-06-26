package activity.sostv.com.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import activity.sostv.com.fragmet.LoginMainFragment;
import activity.sostv.com.sostvapp.R;

public class LoginActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		if(Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT){
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}

		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.login_frame_layout, LoginMainFragment.newInstance());
		transaction.addToBackStack(null);
		transaction.commit();
	}

	public static void start(Context context){
		Intent intent = new Intent(context,LoginActivity.class);
		context.startActivity(intent);
	}

}
