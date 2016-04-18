package com.example.atik.billcontrolapp;

import android.content.Context;

public class BillManager {
    private static BillManager instance = null;
    private Context mContext;
    private BillControlDB db;

    private BillManager(Context context) {
        mContext = context;
        db = new BillControlDB(mContext);
    }

    public static BillManager getInstance(Context context) {
        if (instance == null) {
            instance = new BillManager(context.getApplicationContext());
        }
        return instance;
    }

    public void insert(Bill bill) {
        long billId = db.insertBill(bill);
        bill.setId(billId);
    }

    public void delete(Bill bill) {
        db.deleteBill(bill.getId());
    }
    public void deleteAll(){db.deleteAllBills();}

    public BillControlDB.BillCursor queryAllBills() {
        return db.queryBills();
    }

}