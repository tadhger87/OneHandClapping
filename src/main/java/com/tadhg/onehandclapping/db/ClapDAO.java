package com.tadhg.onehandclapping.db;

/**
 * Created by Tadhg on 15/01/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import com.tadhg.onehandclapping.model.ClapItem;

public class ClapDAO extends ClapDBDAO {

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);
    private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN + " =?";

    public ClapDAO(Context context) {
        super(context);
    }


    public long save(ClapItem clapItem) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.CLAP_NAME_COLUMN, clapItem.getClapName());
        values.put(DataBaseHelper.CLAP_DATE, clapItem.getClapDate());
        values.put(DataBaseHelper.AUDIO_REF, clapItem.getAudioRef());
       // values.put(DataBaseHelper.PICTURE_REF, clapItem.getPictureRef());

        return database.insert(DataBaseHelper.CLAP_TABLE, null, values);
    }



    public int deleteClap(ClapItem clapItem) {
        return database.delete(DataBaseHelper.CLAP_TABLE, WHERE_ID_EQUALS,
                new String[]{clapItem.getId() + ""});
    }

    public ArrayList<ClapItem> getClaps() {
        ArrayList<ClapItem> clapItems = new ArrayList<ClapItem>();

        Cursor cursor = database.query(DataBaseHelper.CLAP_TABLE,
                new String[] { DataBaseHelper.ID_COLUMN,
                        DataBaseHelper.CLAP_NAME_COLUMN,
                        DataBaseHelper.CLAP_DATE,
                        DataBaseHelper.AUDIO_REF }, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            ClapItem clap = new ClapItem();
            clap.setId(cursor.getInt(0));
            clap.setClapName(cursor.getString(1));
            clap.setClapDate(cursor.getString(2));
            clap.setAudioRef(cursor.getString(3));

            clapItems.add(clap);
        }
        return clapItems;
    }

    //Retrieves a single employee record with the given id
    public ClapItem getClap(long id) {
        ClapItem clapItem = null;

        String sql = "SELECT * FROM " + DataBaseHelper.CLAP_TABLE
                + " WHERE " + DataBaseHelper.ID_COLUMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            clapItem = new ClapItem();
            clapItem.setId(cursor.getInt(0));
            clapItem.setClapName(cursor.getString(1));
            clapItem.setClapDate(cursor.getString(2));
            clapItem.setAudioRef(cursor.getString(3));
        }
        return clapItem;


    }
}
