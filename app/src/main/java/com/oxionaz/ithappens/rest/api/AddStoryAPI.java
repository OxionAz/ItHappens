package com.oxionaz.ithappens.rest.api;

import com.oxionaz.ithappens.database.Story;
import java.util.List;
import retrofit.http.GET;
import rx.Observable;


/**
 * Created by Александр on 22.09.2015.
 */
public interface AddStoryAPI {
    @GET("/get?site=ithappens.me&name=ithappens&num=10")
    Observable<List<Story>> loadStories();
}
