package com.obrasocialsjd.magicline.Announcements;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnnouncementDB {
    int _id;
    String _title;
    String _text;
    String _time;

    public AnnouncementDB(){}

    public AnnouncementDB(int id, String title, String text, String time){
        this._id = id;
        this._title = title;
        this._text = text;
        this._time = time;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_text() {
        return _text;
    }

    public void set_text(String _text) {
        this._text = _text;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

}
