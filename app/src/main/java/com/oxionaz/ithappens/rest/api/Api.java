package com.oxionaz.ithappens.rest.api;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oxionaz.ithappens.database.Story;
import java.util.List;
import io.realm.RealmObject;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Александр on 24.09.2015.
 */
public class Api {
    private static String URL = "http://www.umori.li/api/";

    private final RestAdapter restAdapter;

    public Api() {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(URL)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setConverter(new GsonConverter(gson))
                .build();
    }

    public Observable<List<Story>> loadStories() {
        ApiService apiService = restAdapter.create(ApiService.class);
        return apiService.loadStories()
                .subscribeOn(Schedulers.io());
    }

    public interface ApiService {
        @GET("/get?site=ithappens.me&name=bash&num=10")
        Observable<List<Story>> loadStories();
    }
}
