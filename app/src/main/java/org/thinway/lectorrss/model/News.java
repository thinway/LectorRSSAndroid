package org.thinway.lectorrss.model;

/**
 * Created by fdelgado on 18/1/18.
 */

public class News {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
