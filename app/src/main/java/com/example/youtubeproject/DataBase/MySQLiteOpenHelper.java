package com.example.youtubeproject.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.youtubeproject.Model.YoutubePlayerList;

import java.util.ArrayList;

import static com.example.youtubeproject.Activity.MainActivity.database;
import static com.example.youtubeproject.Activity.MainActivity.mySQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String viedosTableName = "videos";
    private static final String favoritesTableName = "favorites";

    public MySQLiteOpenHelper(@Nullable Context context, @Nullable String name,
                              @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableVedioSql = "create table if not exists videos" +
                "(" +
                "      url text PRIMARY KEY NOT NULL, " +
                "      viewtype Integer, " +
                "      title text, " +
                "      id text, " +
                "      duration text, " +
                "      state boolean, " +
                "      favoritesState boolean " +
                ")";

        Log.d("onCreate", "데이터베이스 and 테이블오픈");
        db.execSQL(createTableVedioSql);

        /*
        String createTableFavoritesSql = "create table if not exists favorites" +
                "(" +
                "      url text PRIMARY KEY NOT NULL " +
                ")";

        db.execSQL(createTableFavoritesSql);
        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //--------------------------------videosTable-----------------------------------

    public static void videosInsert(String url,int viewtype, String title, String id, String duration, boolean state, boolean favoritesState) {
        database = mySQLiteOpenHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("url", url);
        values.put("viewtype", viewtype);
        values.put("title", title);
        values.put("id", id);
        values.put("duration", duration);
        values.put("state", state);
        values.put("favoritesState",favoritesState);

        database.insert(viedosTableName, null, values);
    }

    public static ArrayList<YoutubePlayerList> videosSelect() {
        database = mySQLiteOpenHelper.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT *FROM videos ORDER BY title ASC ", null);
        ArrayList<YoutubePlayerList> items = new ArrayList<>();

        while (c.moveToNext()) {
            String url = c.getString(c.getColumnIndex("url"));
            int viewtype = c.getInt(c.getColumnIndex("viewtype"));
            String title = c.getString(c.getColumnIndex("title"));
            String id = c.getString(c.getColumnIndex("id"));
            String duration = c.getString(c.getColumnIndex("duration"));
            boolean state = (c.getInt(c.getColumnIndex("state")) == 1);
            boolean favoritesState = (c.getInt(c.getColumnIndex("favoritesState")) == 1);

            items.add(new YoutubePlayerList(viewtype,title, url, id, duration, state, favoritesState));
        }

        c.close();

        return items;
    }

    public static ArrayList<YoutubePlayerList> favoritesSelect() {
        database = mySQLiteOpenHelper.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT *FROM videos WHERE favoritesState = 1 ORDER BY title ASC ", null);
        ArrayList<YoutubePlayerList> items = new ArrayList<>();

        while (c.moveToNext()) {
            String url = c.getString(c.getColumnIndex("url"));
            int viewtype = c.getInt(c.getColumnIndex("viewtype"));
            String title = c.getString(c.getColumnIndex("title"));
            String id = c.getString(c.getColumnIndex("id"));
            String duration = c.getString(c.getColumnIndex("duration"));
            boolean state = (c.getInt(c.getColumnIndex("state")) == 1);
            boolean favoritesState = (c.getInt(c.getColumnIndex("favoritesState")) == 1);

            items.add(new YoutubePlayerList(viewtype,title, url, id, duration, state, favoritesState));
        }
        c.close();

        return items;
    }

    public static void favoritesUpdate(String url, boolean favoritesState){
        database = mySQLiteOpenHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("favoritesState",favoritesState);

        database.update("videos",values,"url=?",new String[]{url});
    }

    /*
    //--------------------------------favoritesTable-----------------------------------

    public static void favoritesInsert(String url) {
        database = mySQLiteOpenHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("url", url);

        database.insert(favoritesTableName, null, values);

    }

    public static void favoritesSelect(){
        database = mySQLiteOpenHelper.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT *FROM favorites ",null);

        while (c.moveToNext()) {
            String url = c.getString(c.getColumnIndex("url"));

            Log.d("favoritesSelect",url);
        }
    }


    public static void favoritesDelete(String url) {
        database = mySQLiteOpenHelper.getWritableDatabase();
        database.delete(favoritesTableName, "url=?", new String[]{url});
    }

    */
}
