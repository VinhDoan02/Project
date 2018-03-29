package doan.vinh.workit.ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import doan.vinh.workit.R;
import doan.vinh.workit.ui.mainpage.MainActivity;
import io.reactivex.Observable;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btn_guest_login)
    Button btn_guest_login;
    @BindView(R.id.btn_sign_in)
    Button btn_signin;
    @BindView(R.id.btn_signup)
    Button btn_signup;

    Observable<Object>   buttonObservable;

    String TAG = "Login Activity";

    @Inject
    LoginViewModel mLoginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AndroidInjection.inject(this);
        ButterKnife.bind(this);

        mLoginViewModel.getNavigationData().observe(this,response ->processNavigation(response));
        initView();
    }

    public void initView()
    {

        buttonObservable = RxView.clicks(btn_guest_login).

            throttleFirst(500,TimeUnit.MILLISECONDS);

        buttonObservable.subscribe(s -> {
            Log.d(TAG, "clicked");
            mLoginViewModel.guestLogin();
        });
    }

    public void processNavigation(Boolean response)
    {
        if(response)
        {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

}
