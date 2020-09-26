package eu.ammw.fatkaraoke.dagger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import eu.ammw.fatkaraoke.MainActivity;
import eu.ammw.fatkaraoke.ui.searchresult.SearchResultActivity;
import eu.ammw.fatkaraoke.ui.searchresult.SearchResultViewModel;

@Module
abstract class MainModule {
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(3);
    private static final SearchResultViewModel SEARCH_RESULT_VIEW_MODEL = new SearchResultViewModel();

    @Provides
    static ExecutorService executorService() {
        return EXECUTOR_SERVICE;
    }

    @Provides
    static SearchResultViewModel searchResultViewModel() {
        return SEARCH_RESULT_VIEW_MODEL;
    }

    @ContributesAndroidInjector
    abstract SearchResultActivity searchResultActivity();

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();
}
