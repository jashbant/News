package grab.com.application.src.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import grab.com.application.src.base.MainActivity;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = {MainFragmentBindingModule.class})
    abstract MainActivity bindMainActivity();
}