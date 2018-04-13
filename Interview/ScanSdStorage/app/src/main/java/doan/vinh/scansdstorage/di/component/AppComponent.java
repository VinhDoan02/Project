package doan.vinh.scansdstorage.di.component;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import doan.vinh.scansdstorage.App;
import doan.vinh.scansdstorage.di.ActivityBuilder;
import doan.vinh.scansdstorage.di.module.AppModule;


/**
 * Created by Doand on 3/15/2018.
 */


@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(App app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(App application);

        AppComponent build();
    }


}