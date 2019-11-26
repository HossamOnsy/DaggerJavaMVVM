package com.sam.daggerjavamvvm.repositories;

import com.sam.daggerjavamvvm.service.CatApi;
import com.sam.daggerjavamvvm.models.CatModel;

import java.util.ArrayList;

import io.reactivex.Observable;

import static com.sam.daggerjavamvvm.utils.NetworkUtils.createWebService;

public class CatRepository {

    private CatApi catApi  = createWebService();


    public Observable<ArrayList<CatModel>> getCats(int limit)  {

        return catApi.getCats(limit);
    }


}
