package com.oxionaz.ithappens.rest;

import android.content.Context;
import android.widget.Toast;
import com.oxionaz.ithappens.database.Story;
import com.oxionaz.ithappens.util.RealmService;
import java.util.List;
import rx.Observable;
import rx.functions.Action1;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.res.StringRes;

@EBean
public class Queries {

    private Context context;
    private UpdaterCallBack updaterCallBack;

    @StringRes
    String unknown_error, success;

    public Queries(Context context){
        this.context = context;
    }

    @Background
    public void loadStories(){
        RestClient restClient = new RestClient(context);
        Observable<List<Story>> storyModelObservable = restClient.getAddStoryAPI().loadStories();
        storyModelObservable.subscribe(new Action1<List<Story>>() {
                    @Override
                    public void call(List<Story> storyModels) {
                        if (!storyModels.isEmpty()){
                            RealmService.saveAll(storyModels, context);
                            success();
                            updaterCallBack.callBackSuccess();
                        } else {
                            unknownError();
                            updaterCallBack.callBackSuccess();
                        }
                    }
                });
    }

    @UiThread
    public void unknownError(){
        Toast.makeText(context, unknown_error, Toast.LENGTH_SHORT).show();
    }

    @UiThread
    public void success(){
        Toast.makeText(context, success, Toast.LENGTH_SHORT).show();
    }

    public void registerCallBack(UpdaterCallBack register){
        this.updaterCallBack = register;
    }

    public interface UpdaterCallBack{
        void callBackSuccess();
    }
}