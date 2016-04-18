package com.example.atik.billcontrolapp;


import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class AddBillFragment extends Fragment implements View.OnClickListener {
    Spinner billTypeSpinner;
    EditText priceEditText;
    Button deadlineButton;
    EditText descriptionEditText;
    Calendar mCalendar = Calendar.getInstance();
    int mYear = mCalendar.get(Calendar.YEAR);
    int mMonth = mCalendar.get(Calendar.MONTH);
    int mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
    int mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
    int mMin = mCalendar.get(Calendar.MINUTE);
    String timeSet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_bill, container, false);
        billTypeSpinner = (Spinner) view.findViewById(R.id.spinner_type);
        priceEditText = (EditText) view.findViewById(R.id.edittext_price);
        deadlineButton = (Button) view.findViewById(R.id.button_deadline);
        descriptionEditText = (EditText) view.findViewById(R.id.edittext_description);
        String[] types = {"Electricity", "Water", "Gas", "ADSL", "Credit Card", "Telephone", "Others"};
        billTypeSpinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, types));
        deadlineButton.setOnClickListener(this);
        return view;
    }

    public void onClick(View v) {
        TimePickerDialog dialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMin = minute;
                timeSet = mHour+":"+mMin;
                deadlineButton.setText(timeSet);
                Message.message(getActivity(),timeSet);
            }
        },mHour,mMin,true);
        dialog.show();
    }
}