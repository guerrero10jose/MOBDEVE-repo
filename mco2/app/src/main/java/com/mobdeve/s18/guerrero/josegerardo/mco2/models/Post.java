package com.mobdeve.s18.guerrero.josegerardo.mco2.models;

public class Post {
    private int imageId, userImageId, likes, comments;
    private String caption, task, username, modelname, postid;
    private boolean liked;

    public Post(int imageId, int likes, int comments, String caption, String task, boolean liked, String username, int userImageId) {
        this.imageId = imageId;
        this.likes = likes;
        this.comments = comments;
        this.caption = caption;
        this.task = task;
        this.liked = liked;
        this.username = username;
        this.userImageId = userImageId;
    }

    public Post(String imageId, int likes, int comments, String caption, String task, boolean liked, String username, int userImageId,
                String postid) {
        this.postid = postid;
        this.modelname = imageId;
        this.likes = likes;
        this.comments = comments;
        this.caption = caption;
        this.task = task;
        this.liked = liked;
        this.username = username;
        this.userImageId = userImageId;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getModelname() { return modelname;}

    public int getImageId() {
        return imageId;
    }

    public int getUserImageId() {
        return userImageId;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }

    public String getCaption() {
        return caption;
    }

    public String getTask() {
        return task;
    }

    public String getUsername() {
        return username;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
