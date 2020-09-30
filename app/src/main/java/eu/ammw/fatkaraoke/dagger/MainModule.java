package eu.ammw.fatkaraoke.dagger;

import android.content.Context;

import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import eu.ammw.fatkaraoke.MainActivity;
import eu.ammw.fatkaraoke.db.SongDatabase;
import eu.ammw.fatkaraoke.ui.searchresult.SearchResultActivity;
import eu.ammw.fatkaraoke.ui.searchresult.SearchResultViewModel;

@Module
public abstract class MainModule {
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(3);
    private static final SearchResultViewModel SEARCH_RESULT_VIEW_MODEL = new SearchResultViewModel();
    private static SongDatabase DB;
    private static Context CONTEXT;

    @Provides
    static ExecutorService executorService() {
        return EXECUTOR_SERVICE;
    }

    @Provides
    static SearchResultViewModel searchResultViewModel() {
        return SEARCH_RESULT_VIEW_MODEL;
    }

    @Provides
    public static SongDatabase songDatabase(Context context) {
        if (DB == null) {
            DB = Room.databaseBuilder(context, SongDatabase.class, "fat-song-database").build();
        }
        return DB;
    }

    @Provides
    static Context context() {
        return CONTEXT;
    }

    public static void setContext(Context context) {
        CONTEXT = context;
    }

    @ContributesAndroidInjector
    abstract SearchResultActivity searchResultActivity();

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();
}
