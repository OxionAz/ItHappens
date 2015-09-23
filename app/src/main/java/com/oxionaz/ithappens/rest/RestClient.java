package com.oxionaz.ithappens.rest;

import com.oxionaz.ithappens.rest.api.AddStoryAPI;
import retrofit.RestAdapter;

/**
 * Created by Александр on 22.09.2015.
 */
public class RestClient {

    private static final String BASE_URL = "http://www.umori.li/api/";

    private AddStoryAPI addStoryAPI;

    RestClient(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .build();

        addStoryAPI = restAdapter.create(AddStoryAPI.class);
    }

    public AddStoryAPI getAddStoryAPI(){return addStoryAPI;}
}
