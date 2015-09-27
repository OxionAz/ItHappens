package com.oxionaz.ithappens.rest;

import com.oxionaz.ithappens.database.Story;
import java.util.List;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Александр on 23.09.2015.
 */
public class RestService {

    RestClient restClient;

    public RestService(){
        restClient = new RestClient();
    }

    public Observable<List<Story>> addStory() {
        return restClient.getAddStoryAPI().loadStories().subscribeOn(Schedulers.io());
    }
}
