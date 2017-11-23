package com.qulix.shestakaa.gifsearchermvp.model.API;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("search")
    Observable<Feed> getSearch(@Query("q") String request,
                               @Query("api_key") String api_key,
                               @Query("limit") int limit);

    @GET("trending")
    Observable<Feed> getTrendingNow(@Query("api_key") String api_key, @Query("limit") int limit);

}
