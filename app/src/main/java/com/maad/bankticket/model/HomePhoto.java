package com.maad.bankticket.model;

public class HomePhoto {

    private String title;
    private int image;

    public HomePhoto(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

}
