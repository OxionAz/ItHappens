package com.oxionaz.ithappens.rest.api;

import com.oxionaz.ithappens.database.Story;
import java.util.List;
import retrofit.http.GET;
import rx.Observable;

public interface AddStoryAPI {
    @GET("/get?site=ideer.ru&name=ideer&num=50")
    Observable<List<Story>> loadStories();
}