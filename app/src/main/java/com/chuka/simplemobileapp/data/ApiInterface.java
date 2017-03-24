package com.chuka.simplemobileapp.data;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Clement on 3/23/17.
 */

public interface ApiInterface {

    @GET("list")
    Observable<ItemResponse> getList();
}
