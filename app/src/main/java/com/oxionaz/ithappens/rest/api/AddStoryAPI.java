package com.oxionaz.ithappens.rest.api;

import com.oxionaz.ithappens.rest.model.StoryModel;

import java.util.List;

import retrofit.http.GET;


/**
 * Created by Александр on 22.09.2015.
 */
public interface AddStoryAPI {

    @GET("/get?site=ithappens.me&name=bash&num=1")
    List<StoryModel> addStories();
}
