package eu.ammw.fatkaraoke;

import android.app.Application;
import android.util.Log;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import eu.ammw.fatkaraoke.dagger.ApplicationComponent;
import eu.ammw.fatkaraoke.dagger.DaggerApplicationComponent;
import eu.ammw.fatkaraoke.dagger.MainModule;
import eu.ammw.fatkaraoke.page.UpdateWorker;

public class FatKaraokeApplication extends Application implements HasAndroidInjector {
    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        MainModule.setContext(getApplicationContext());
        applicationComponent = DaggerApplicationComponent.builder().application(this).build();
        applicationComponent.inject(this);
        scheduleUpdate();
    }

    private void scheduleUpdate() {
        WorkRequest updateRequest = new OneTimeWorkRequest.Builder(UpdateWorker.class).build();
        WorkManager.getInstance(getApplicationContext()).enqueue(updateRequest);
        Log.d("FKA", "Scheduled update");
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }
}
