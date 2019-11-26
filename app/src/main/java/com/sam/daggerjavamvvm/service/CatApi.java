package com.sam.daggerjavamvvm.service;

import com.sam.daggerjavamvvm.models.CatModel;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CatApi {

        /* Get route used to retrieve cat images, limit is the number of cats item */
        @GET ("images/search")
        public Observable<ArrayList<CatModel>> getCats(@Query("limit") int limit);

}
