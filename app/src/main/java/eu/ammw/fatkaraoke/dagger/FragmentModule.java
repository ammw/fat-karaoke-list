package eu.ammw.fatkaraoke.dagger;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import eu.ammw.fatkaraoke.ui.searchresult.SearchResultFragment;

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract SearchResultFragment bindSearchResultFragment();
}
