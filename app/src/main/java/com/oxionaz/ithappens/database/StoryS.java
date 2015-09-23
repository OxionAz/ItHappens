package com.oxionaz.ithappens.database;

import io.realm.RealmObject;

/**
 * Created by Александр on 22.09.2015.
 */
public class StoryS extends RealmObject {

    private String name;
    private String date;

   public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String desc) {
        this.date = desc;
    }
}
