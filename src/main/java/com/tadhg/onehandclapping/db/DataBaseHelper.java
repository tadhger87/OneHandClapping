package com.tadhg.onehandclapping.db;

/**
 * Created by Tadhg on 15/01/2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "clapdb";
    private static final int DATABASE_VERSION = 1;

    public static final String CLAP_TABLE = "clap";

    public static final String ID_COLUMN = "id";
    public static final String CLAP_NAME_COLUMN = "name";
    public static final String CLAP_DATE = "date";
    public static final String AUDIO_REF = "audio";
    public static final String PICTURE_REF = "picture";

    public static final String CREATE_CLAP_TABLE = "CREATE TABLE "
            + CLAP_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
            + CLAP_NAME_COLUMN + " TEXT, " + AUDIO_REF + " TEXT, "
            + CLAP_DATE + " TEXT, " + PICTURE_REF + " TEXT" + ")";

    private static DataBaseHelper instance;

    public static synchronized DataBaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DataBaseHelper(context);
        return instance;
    }

    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLAP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
