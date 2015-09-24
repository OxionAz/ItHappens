package com.oxionaz.ithappens.database;

import io.realm.RealmObject;

/**
 * Created by Александр on 22.09.2015.
 */
public class Story extends RealmObject {

    private String site;
    private String name;
    private String desc;
    private String link;
    private String elementPureHtml;

    public String getSite() {
        return site;
    }
    public void setSite(String site) {
        this.site = site;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }

    public String getElementPureHtml() {
        return elementPureHtml;
    }
    public void setElementPureHtml(String elementPureHtml) {
        this.elementPureHtml = elementPureHtml;
    }
}
