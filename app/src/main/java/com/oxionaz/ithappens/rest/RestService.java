package com.oxionaz.ithappens.rest;

import com.oxionaz.ithappens.rest.model.StoryModel;

import java.util.List;

/**
 * Created by Александр on 23.09.2015.
 */
public class RestService {

//    private static final String SITE = "ithappens.me";
//    private static final String NAME = "bash";
//    private static final String NUM = "1";

    RestClient restClient;

    public RestService(){
        restClient = new RestClient();
    }

    public List<StoryModel> addStory() {
        return restClient.getAddStoryAPI().addStories();
    }
}
