package com.sam.daggerjavamvvm.di.factory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sam.daggerjavamvvm.di.usecases.LoadCatsUseCase;
import com.sam.daggerjavamvvm.viewmodels.MainActivityViewModel;

// This factory is what allows the viewModel to has it's dependencies
public class CatsViewModelFactory implements ViewModelProvider.Factory {

    private final LoadCatsUseCase loadCatsUseCase;


    public CatsViewModelFactory(LoadCatsUseCase loadCatsUseCase) {
        this.loadCatsUseCase = loadCatsUseCase;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(loadCatsUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}