package org.thinway.lectorrss.model;

/**
 * Created by fdelgado on 18/1/18.
 */

public class News {

    private String imageUrl;
    private String title;
    private String description;

    public News(){}

    public News(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public News(String imageUrl, String title, String description) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return getTitle() + ": " + getDescription();
    }
}
