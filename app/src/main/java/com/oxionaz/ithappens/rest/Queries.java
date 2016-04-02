package com.oxionaz.ithappens.rest;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import com.oxionaz.ithappens.database.Story;
import com.oxionaz.ithappens.util.RealmService;
import com.oxionaz.ithappens.util.UpdaterCallBack;
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
    private View fragmentView = null;
    private UpdaterCallBack updaterCallBack;

    @StringRes
    String unknown_error, success;

    public Queries(Context context){
        this.context = context;
    }

    public void setFragmentView(View fragmentView){
        this.fragmentView = fragmentView;
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
                            if(fragmentView != null) success();
                            updaterCallBack.callBackSuccess();
                        } else {
                            if(fragmentView != null) unknownError();
                            updaterCallBack.callBackSuccess();
                        }
                    }
                });
    }

    @UiThread
    public void unknownError(){
        Snackbar.make(fragmentView, unknown_error, Snackbar.LENGTH_SHORT).show();
    }

    @UiThread
    public void success(){
        Snackbar.make(fragmentView, success, Snackbar.LENGTH_SHORT).show();
    }

    public void registerCallBack(UpdaterCallBack register){
        this.updaterCallBack = register;
    }
}