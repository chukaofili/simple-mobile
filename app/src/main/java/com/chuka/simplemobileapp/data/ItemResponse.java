package com.chuka.simplemobileapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Clement on 3/24/17.
 */

public class ItemResponse implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public class Item {

        @SerializedName("id")
        @Expose
        private Integer id;

        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("description")
        @Expose

        private String description;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }
}
