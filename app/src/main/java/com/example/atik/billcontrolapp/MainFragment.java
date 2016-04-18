package com.example.atik.billcontrolapp;


import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;


public class MainFragment extends ListFragment {
    private BillControlDB.BillCursor cursor;
    private BillCursorAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        try {
            BillManager manager = BillManager.getInstance(getActivity());
            cursor = manager.queryAllBills();
            adapter = new BillCursorAdapter(getActivity(), cursor);
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            Message.message(getActivity(),"Cursor error");
        }
        setListAdapter(adapter);
    }

}

