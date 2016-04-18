package com.example.atik.billcontrolapp;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddBillActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        getFragmentManager().beginTransaction().add(R.id.activity_add_bill,new AddBillFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_bill,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done_add){
            //Return to main activity
            Spinner typeSpinner = (Spinner)findViewById(R.id.spinner_type);
            EditText priceEditText = (EditText)findViewById(R.id.edittext_price);
            EditText descEditText = (EditText)findViewById(R.id.edittext_description);
            Button timeButton = (Button)findViewById(R.id.button_deadline);
            Intent intent = getIntent();
            intent.putExtra("type",typeSpinner.getSelectedItem().toString());
            intent.putExtra("price", Double.parseDouble(priceEditText.getText().toString()));
            intent.putExtra("deadline",timeButton.getText().toString());
            intent.putExtra("description", descEditText.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
            Message.message(this,"setResult() called");
        }
        return super.onOptionsItemSelected(item);
    }
}