package com.sam.daggerjavamvvm.di.usecases;

import com.sam.daggerjavamvvm.models.CatModel;
import com.sam.daggerjavamvvm.repositories.CatRepository;
import com.sam.daggerjavamvvm.ui.CatAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class LoadCatsUseCase {
    private final CatRepository catRepository;

    @Inject
    public LoadCatsUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Observable<ArrayList<CatModel>> execute() {
        return catRepository.getCats(3);
    }
}