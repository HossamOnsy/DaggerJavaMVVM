package com.sam.daggerjavamvvm;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CatApi {

        /* Get route used to retrieve cat images, limit is the number of cats item */
        @GET ("images/search")
        public Observable<ArrayList<CatModel>> getCats(@Query("limit") int limit);

}
