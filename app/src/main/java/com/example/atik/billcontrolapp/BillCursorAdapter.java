package com.example.atik.billcontrolapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;



public class BillCursorAdapter extends CursorAdapter {
    Context mContext;

    public BillCursorAdapter(Context context, BillControlDB.BillCursor cursor) {
        super(context, cursor, 0);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.bill_list_item, parent, false);
    }

    @Override
    public void bindView(final View view, final Context context, Cursor cursor) {
        BillControlDB.BillCursor billCursor = (BillControlDB.BillCursor) cursor;
        final Bill bill = billCursor.getBill();
        TextView tvType = (TextView) view.findViewById(R.id.item_type);
        TextView tvDeadline = (TextView)view.findViewById(R.id.item_deadline);
        tvType.setText(bill.getType());
        tvDeadline.setText(bill.getDeadline());
        view.setClickable(true);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("Are sure you want to delete?");
                dialog.setTitle("Delete");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BillManager.getInstance(context).delete(bill);
                        Message.message(context, "Deleted");

                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Message.message(context, "Cancelled");
                    }
                });
                dialog.show();

                return true;
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Details of "+bill.getType()+" debt");
                dialog.setMessage("Amount to be paid : "+bill.getPrice()+"\nDescription : "+bill.getDescription());
                dialog.show();
            }
        });

    }
}