package com.sam.daggerjavamvvm.di.modules;

import com.sam.daggerjavamvvm.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = CatModule.class)
    abstract MainActivity bindLobbyActivity();

    // Add bindings for other sub-components here
}