package com.sam.daggerjavamvvm.di.modules;

import android.content.Context;

import com.sam.daggerjavamvvm.MyApplication;
import com.sam.daggerjavamvvm.repositories.CatRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    Context provideContext(MyApplication application) {
        return application.getApplicationContext();
    }
}