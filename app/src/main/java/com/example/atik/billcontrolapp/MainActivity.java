package com.example.atik.billcontrolapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final int REQ_CODE = 100;
    BillControlDB db;
    List<Bill> mBills;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            getFragmentManager().beginTransaction().add(R.id.activity_main, new MainFragment()).commit();
            Message.message(this, "Main Fragment attached");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Message.message(this, "onActivityResult is called");
        if (requestCode == REQ_CODE && resultCode == RESULT_OK) {
            try {
                String type = data.getStringExtra("type");
                double price = data.getDoubleExtra("price", 0.0);
                final String deadline = data.getStringExtra("deadline");
                String description = data.getStringExtra("description");
                BillManager manager = BillManager.getInstance(this);
                manager.insert(new Bill(type, price, deadline, description));
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Set timer");
                dialog.setMessage("Would you like to set the timer to " + deadline);
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancelAlarm();
                    }
                });
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setAlarm(deadline);
                    }
                });
                dialog.show();
                getFragmentManager().beginTransaction().replace(R.id.activity_main, new MainFragment()).commit();
            } catch (Exception e) {
                Message.message(this, "Error inserting data to database");
            }

        }
    }

    private void setAlarm(String deadline) {
        String[] text = deadline.split(":");
        int hour = Integer.parseInt(text[0]);
        int min = Integer.parseInt(text[1]);
        Calendar targetCal = Calendar.getInstance();
        targetCal.set(Calendar.HOUR_OF_DAY,hour);
        targetCal.set(Calendar.MINUTE,min);
        targetCal.set(Calendar.SECOND,0);
        targetCal.set(Calendar.MILLISECOND,0);
        Message.message(this,"Alarm is set : "+targetCal.getTime());
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), PendingIntent.FLAG_CANCEL_CURRENT, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getBaseContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }
    private void cancelAlarm(){

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), PendingIntent.FLAG_UPDATE_CURRENT, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        Message.message(this,"Alarm is cancelled");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_add_bill:
                //Open add bill activity
                Intent intent = new Intent(this, AddBillActivity.class);
                startActivityForResult(intent, REQ_CODE);
                break;
            case R.id.action_delete_all:
                BillManager.getInstance(this).deleteAll();
                Message.message(this,"All bills deleted");
        }

        return super.onOptionsItemSelected(item);
    }


}
