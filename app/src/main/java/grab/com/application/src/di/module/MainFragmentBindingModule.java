package grab.com.application.src.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import grab.com.application.ListFragment;

@Module
public abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract ListFragment provideListFragment();

    @ContributesAndroidInjector
    abstract DetailsFragment provideDetailsFragment();
}
