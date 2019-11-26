package com.sam.daggerjavamvvm.di.factory.components;

import com.sam.daggerjavamvvm.MyApplication;
import com.sam.daggerjavamvvm.di.modules.AppModule;
import com.sam.daggerjavamvvm.di.modules.BuildersModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        BuildersModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(MyApplication application);
        AppComponent build();
    }
    void inject(MyApplication app);
}