package com.oxionaz.ithappens.rest;

import android.content.Context;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oxionaz.ithappens.R;
import com.oxionaz.ithappens.rest.api.AddStoryAPI;
import io.realm.RealmObject;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class RestClient {

    private AddStoryAPI addStoryAPI;

    public RestClient(Context context){
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
                .setEndpoint(context.getString(R.string.base_url))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();

        addStoryAPI = restAdapter.create(AddStoryAPI.class);
    }

    public AddStoryAPI getAddStoryAPI(){return addStoryAPI;}
}