package eu.ammw.fatkaraoke.dagger;

import android.app.Application;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import eu.ammw.fatkaraoke.FatKaraokeApplication;

@Component(modules = {AndroidSupportInjectionModule.class, MainModule.class, FragmentModule.class})
public interface ApplicationComponent extends AndroidInjector<FatKaraokeApplication> {

    void inject(FatKaraokeApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }
}
