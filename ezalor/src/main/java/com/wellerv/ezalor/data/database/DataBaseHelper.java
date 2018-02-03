package com.wellerv.ezalor.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wellerv.ezalor.util.Utils;

import static com.wellerv.ezalor.Env.DBDIR;

/**
 * Created by huwei on 17-12-22.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = Utils.getDBNameByProcess(Utils.getProcessName());
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME_IOHISTORY = "iohistory";

    private static final String CREATETABLE_SQL = "create table if not exists " + TABLE_NAME_IOHISTORY + "(\n" +
            "     path text ,\n" +
            "     process text ,\n" +
            "     thread text ,\n" +
            "     processId ,\n" +
            "     threadId , \n" +
            "     readCount integer ,\n" +
            "     readBytes integer ,\n" +
            "     readTime integer ,\n" +
            "     writeCount integer ,\n" +
            "     writeBytes integer ,\n" +
            "     writeTime integer ,\n" +
            "     stacktrace text ,\n" +
            "     openTime integer ,\n" +
            "     closeTime integer \n" +
            ")";

    public DataBaseHelper(Context context) {
        this(context, DBDIR + "/" + DB_NAME);
    }

    public DataBaseHelper(Context context, String dbPath) {
        super(context, dbPath, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATETABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
