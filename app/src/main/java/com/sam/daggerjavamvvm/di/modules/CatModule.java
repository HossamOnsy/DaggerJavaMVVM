package com.sam.daggerjavamvvm.di.modules;

import com.sam.daggerjavamvvm.di.factory.CatsViewModelFactory;
import com.sam.daggerjavamvvm.di.usecases.LoadCatsUseCase;
import com.sam.daggerjavamvvm.repositories.CatRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class CatModule {

    @Provides
    CatRepository provideCatRepository() {
        return new CatRepository();
    }

    @Provides
    CatsViewModelFactory provideLobbyViewModelFactory(LoadCatsUseCase loadCatsUseCase) {
        return new CatsViewModelFactory(loadCatsUseCase);
    }
}