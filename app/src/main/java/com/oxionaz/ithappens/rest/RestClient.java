package com.oxionaz.ithappens.rest;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oxionaz.ithappens.rest.api.AddStoryAPI;
import io.realm.RealmObject;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Александр on 22.09.2015.
 */
public class RestClient {

    private static final String BASE_URL = "http://www.umori.li/api/";
    private AddStoryAPI addStoryAPI;

    public RestClient(){
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

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();

        addStoryAPI = restAdapter.create(AddStoryAPI.class);
    }

    public AddStoryAPI getAddStoryAPI(){return addStoryAPI;}
}
