package com.example.myapplication;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "BBCNewsReaders";
    public final static String TABLE_NAME = "Favourite_Table";
    public final static String COL_ID = "_id";
    public final static String COL_TITLE = "Title";
    public final static String COL_DESCRIPTION = "Description";
    public final static String COL_LINK = "Link";
    public final static String COL_DATE = "Date";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_TITLE + " text,"
                + COL_DESCRIPTION + " text,"
                + COL_LINK + " text,"
                + COL_DATE + " text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertNews(fav news) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TITLE, news.title);
        contentValues.put(COL_DESCRIPTION, news.description);
        contentValues.put(COL_LINK, news.link);
        contentValues.put(COL_DATE, news.date);
        db.insert(TABLE_NAME, null, contentValues);
    }


    public void deleteNews(fav news) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME + " where _id='" + news.id + "';");
    }

    public ArrayList<fav> getFavNews() {
        ArrayList<fav> favList = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME +" order by _id desc;", null);
        res.moveToFirst();
        int idIndex = res.getColumnIndex(COL_ID);
        int titleIndex = res.getColumnIndex(COL_TITLE);
        int desIndex = res.getColumnIndex(COL_DESCRIPTION);
        int linkIndex = res.getColumnIndex(COL_LINK);
        int dateIndex = res.getColumnIndex(COL_DATE);

        while (!res.isAfterLast()) {
            int id = res.getInt(idIndex);
            String title = res.getString(titleIndex);
            String des= res.getString(desIndex);
            String link = res.getString(linkIndex);
            String date= res.getString(dateIndex);
            favList.add(new fav(id,title,des,link,date));
            res.moveToNext();
        }
        res.close();
        return favList;
    }

}
