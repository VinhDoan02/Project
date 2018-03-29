package doan.vinh.workit.ui.landing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import doan.vinh.workit.R;
import doan.vinh.workit.ui.login.LoginActivity;
import doan.vinh.workit.ui.mainpage.MainActivity;

public class LandingActivity extends AppCompatActivity {

    @BindView(R.id.btn_guest_login) Button btn_guest_login;

    @Inject
    LandingViewModel mLandingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        AndroidInjection.inject(this);
        ButterKnife.bind(this);

        mLandingViewModel.checkExercise();
        mLandingViewModel.checkUser();
        mLandingViewModel.getNavigationData().observe(this,response ->processNavigation(response));
    }

    public void processNavigation(Boolean navigationData)
    {
        Intent intent;

        if(navigationData)
        {
            intent = new Intent(LandingActivity.this, MainActivity.class);

        }else
        {
            intent = new Intent(LandingActivity.this, LoginActivity.class);
        }



        startActivity(intent);
    }

}
