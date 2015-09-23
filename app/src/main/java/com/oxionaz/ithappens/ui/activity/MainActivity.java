package com.oxionaz.ithappens.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.oxionaz.ithappens.R;
import com.oxionaz.ithappens.database.StoryS;
import com.oxionaz.ithappens.ui.fragments.StoryFragment_;
import com.oxionaz.ithappens.database.Story;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import io.realm.Realm;

@EActivity(R.layout.activity_main)
    public class MainActivity extends AppCompatActivity {

    @ViewById
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new StoryFragment_()).commit();
        }
    }

    @AfterViews
    void ready(){
        setSupportActionBar(toolbar);
        setNews();
    }

    public void setNews(){
        Realm realm = Realm.getInstance(this);
        realm.beginTransaction();
        //... add or update objects here ...
        StoryS story = realm.createObject(StoryS.class); // Create a new object
        story.setName("John");
        story.setDate("john@corporation.com");
        realm.commitTransaction();
    }
}
