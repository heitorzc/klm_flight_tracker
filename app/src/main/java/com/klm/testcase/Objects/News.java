package com.klm.testcase.Objects;

import java.io.Serializable;

/**
 * Created by Heitor Zanetti
 * 11/03/2016
 */
public class News implements Serializable {

    String title;
    String author;
    String url;

    public News(String title, String author, String url) {
        this.title = title;
        this.author = author;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }
}
