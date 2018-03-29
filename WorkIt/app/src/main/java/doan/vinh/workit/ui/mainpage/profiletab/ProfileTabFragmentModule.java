package doan.vinh.workit.ui.mainpage.profiletab;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Doand on 3/23/2018.
 */

@Module
public class ProfileTabFragmentModule {


    @Provides
    ProfileTabViewModel provideProfileViewModel(){return new ProfileTabViewModel();}

}
