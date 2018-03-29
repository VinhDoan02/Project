package doan.vinh.workit.ui.login;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Doand on 3/15/2018.
 */
@Module
public class LoginActivityModule {

    @Provides
    LoginViewModel providesLoginActivityViewModel()
    {
        return new LoginViewModel();
    }


}
