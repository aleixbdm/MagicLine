package com.obrasocialsjd.magicline.Announcements;

public class ListModel {

    private  String image="";
    private  String title="";
    private  String text="";
    private  String time="";

    /*********** Set Methods ******************/

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(String time) {
        this.time = time;
    }

    /*********** Get Methods ****************/

    public String getImage() {
        return this.image;
    }

    public String getTitle() {
        return this.title;
    }

    public String getText() {
        return this.text;
    }

    public String getTime() {
        return this.time;
    }
}
