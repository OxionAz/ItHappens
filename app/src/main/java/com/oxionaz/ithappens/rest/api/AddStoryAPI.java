package com.oxionaz.ithappens.rest.api;

import com.oxionaz.ithappens.database.Story;

import java.util.List;
import retrofit.http.GET;
import rx.Observable;


/**
 * Created by Александр on 22.09.2015.
 */
public interface AddStoryAPI {
    @GET("/get?site=ideer.ru&name=ideer&num=50")
    Observable<List<Story>> loadStories();
}