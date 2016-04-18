package com.example.atik.billcontrolapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillControlDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "billControl.db";
    private static final int DB_VERSION = 9;
    private static final String DB_TABLE = "billsTable";

    public static final String KEY_ROWID = "_id";
    public static final String KEY_TYPE = "type";
    public static final String KEY_PRICE = "price";
    public static final String KEY_DEADLINE = "deadline";
    public static final String KEY_DESCRIPTION = "description";
    public static final String DATABASE_CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + DB_TABLE
            + " (" + KEY_ROWID + " integer primary key autoincrement, "
            + KEY_TYPE + " text not null, "
            + KEY_PRICE + " real not null, "
            + KEY_DEADLINE + " text not null, "
            + KEY_DESCRIPTION + " text);";
    public static final String[] ALL_KEYS = new String[]{KEY_ROWID, KEY_TYPE, KEY_PRICE, KEY_DEADLINE, KEY_DESCRIPTION};
    private Context mContext;


    public BillControlDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DATABASE_CREATE_SQL);
            Message.message(mContext, "onCreate is called");
        } catch (Exception e) {
            Message.message(mContext, "" + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Message.message(mContext, "onUpgrade is called");
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
        } catch (Exception e) {
            Message.message(mContext, "" + e);
        }
    }

    public long insertBill(Bill bill) {

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, bill.getType());
        values.put(KEY_PRICE, bill.getPrice());
        values.put(KEY_DEADLINE, bill.getDeadline());
        values.put(KEY_DESCRIPTION, bill.getDescription());
        return getWritableDatabase().insert(DB_TABLE, null, values);
    }


    public void deleteAllBills() {
        getWritableDatabase().delete(DB_TABLE, null, null);
    }

    public boolean deleteBill(long rowId) {
        String where = KEY_ROWID + " = " + rowId;
        return getWritableDatabase().delete(DB_TABLE, where, null) != 0;
    }

    public BillCursor queryBills() {
        Cursor cursor = getReadableDatabase().query(DB_TABLE, null, null, null, null, null, null);
        return new BillCursor(cursor);
    }


    public static class BillCursor extends CursorWrapper {
        public BillCursor(Cursor cursor) {
            super(cursor);
        }

        public Bill getBill() {
            if (isBeforeFirst() || isAfterLast()) {
                return null;
            }
            Bill bill = new Bill();
            bill.setId(getLong(getColumnIndex(KEY_ROWID)));
            bill.setType(getString(getColumnIndex(KEY_TYPE)));
            bill.setPrice(getDouble(getColumnIndex(KEY_PRICE)));
            bill.setDeadline(getString(getColumnIndex(KEY_DEADLINE)));
            bill.setDescription(getString(getColumnIndex(KEY_DESCRIPTION)));
            return bill;
        }
    }


}