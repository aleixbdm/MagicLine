package com.obrasocialsjd.magicline.Announcements;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAnnouncements extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "announcementsManager";

    // Messages table name
    public static final String TABLE_ANNOUNCEMENTSDOWNLOADED = "announcementsDownloaded";

    // Messages Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_TEXT = "text";
    private static final String KEY_TIME = "time";


    public DatabaseAnnouncements(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EVENTSDOWNLOADED_TABLE = "CREATE TABLE " + TABLE_ANNOUNCEMENTSDOWNLOADED + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_TEXT + " TEXT,"
                + KEY_TIME + " TEXT"+ ")";
        db.execSQL(CREATE_EVENTSDOWNLOADED_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANNOUNCEMENTSDOWNLOADED);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    public String addAnnouncement(AnnouncementDB announcementDB, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, announcementDB.get_title());
        values.put(KEY_TEXT, announcementDB.get_text());
        values.put(KEY_TIME, announcementDB.get_time());

        // Inserting Row
        long id=db.insert(tableName, null, values);
        db.close(); // Closing database connection
        return String.valueOf(id);
    }

    boolean containsAnnouncement(String id, String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tableName, new String[] { KEY_ID }, KEY_ID + "=?",
                new String[] { id }, null, null, null, null);

        boolean ret = cursor.getCount()>0;
        cursor.close();
        return ret;
    }


    AnnouncementDB getAnnouncement(int id, String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tableName, new String[] { KEY_ID }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        AnnouncementDB announcementDB = new AnnouncementDB(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        cursor.close();
        return announcementDB;
    }

    public List<AnnouncementDB> getAllAnnouncement(String tableName) {
        List<AnnouncementDB> announcementDBList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + tableName;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AnnouncementDB announcementDB = new AnnouncementDB();
                announcementDB.set_id(Integer.parseInt(cursor.getString(0)));
                announcementDB.set_title(cursor.getString(1));
                announcementDB.set_text(cursor.getString(2));
                announcementDB.set_time(cursor.getString(3));
                // Adding contact to list
                announcementDBList.add(announcementDB);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return contact list
        return announcementDBList;
    }

    public int updateAnnouncement(AnnouncementDB announcementDB, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, announcementDB.get_title());
        values.put(KEY_TEXT, announcementDB.get_text());
        values.put(KEY_TIME, announcementDB.get_time());

        // updating row
        int ret = db.update(tableName, values, KEY_ID + " = ?",
                new String[] { String.valueOf(announcementDB.get_id()) });
        db.close();
        return ret;
    }

    public void deleteAnnouncement(String id, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, KEY_ID + " = ?",
                new String[] { id });
        db.close();
    }

    public void dropTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANNOUNCEMENTSDOWNLOADED);

        onCreate(db);
    }

    public boolean existsAnnouncement(String key) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_ANNOUNCEMENTSDOWNLOADED, new String[] {}, KEY_ID + "=?",
                new String[] { String.valueOf(key) }, null, null, null);
        if (!cursor.moveToFirst()){
            return false;
        }

        cursor.close();
        return true;
    }

    public int getCountAnnouncements() {
        String countQuery = "SELECT  * FROM " + TABLE_ANNOUNCEMENTSDOWNLOADED;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int size = cursor.getCount();
        cursor.close();
        return size;
    }

}
