package com.sam.daggerjavamvvm.repositories;

import com.sam.daggerjavamvvm.service.CatApi;
import com.sam.daggerjavamvvm.models.CatModel;

import java.util.ArrayList;

import io.reactivex.Observable;

public class CatRepository {

    CatApi catApi ;

    public Observable<ArrayList<CatModel>> getCats(int limit)  {

        return catApi.getCats(limit);
    }


    public CatRepository(CatApi catApi) {
        this.catApi = catApi;
    }
}
