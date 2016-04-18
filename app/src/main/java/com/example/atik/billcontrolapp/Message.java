package com.example.atik.billcontrolapp;

import android.content.Context;
import android.widget.Toast;


public class Message {
    Context mContext;
    public Message(Context context){
        mContext = context;
    }
    public static void message(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}