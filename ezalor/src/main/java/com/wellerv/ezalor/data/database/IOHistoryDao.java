package com.wellerv.ezalor.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.wellerv.ezalor.Env;
import com.wellerv.ezalor.IORecord;

import java.io.File;

/**
 * Created by huwei on 17-12-22.
 */

public class IOHistoryDao {
    public static final String TABLE_NAME = "iohistory";

    private Context mContext;
    private DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase mDB;

    public IOHistoryDao(Context context) {
        this.mContext = context;

        File parentFile = new File(Env.DBDIR);
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        mDataBaseHelper = new DataBaseHelper(context);

        mDB = mDataBaseHelper.getWritableDatabase();
    }

    public synchronized void insert(IORecord ioRecord) {
        mDB.beginTransaction();

        mDB.insert(TABLE_NAME, null, ioRecord.toContentValues());

        mDB.setTransactionSuccessful();

        mDB.endTransaction();
    }
}
