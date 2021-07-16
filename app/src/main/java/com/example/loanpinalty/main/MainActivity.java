package com.example.loanpinalty.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loanpinalty.R;
import com.example.loanpinalty.data.local.LocalData;
import com.example.loanpinalty.data.model.Loan;
import com.example.loanpinalty.detail.DetailActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.orhanobut.hawk.Hawk;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private MaterialButton btnSave;
    private TextInputEditText etValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init Hawk for LocalData
        Hawk.init(this).build();


        setUp();

        //if loan already input, go to detail activity
        if (LocalData.getLoan() != null) {
            goToDetail();
        }
    }


    private void setUp() {

        btnSave = findViewById(R.id.btnSave);
        etValue = findViewById(R.id.etValue);
        //onclick event
        btnSave.setOnClickListener(v -> {
            // get value from input
            String value = etValue.getText().toString();

            //if empty show toast message
            if (value.isEmpty()) {
                Toast.makeText(this, "Input tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            //create object loan
            Loan loan = new Loan();

            //set loan value cost
            loan.setValue(Double.parseDouble(value));

            final Calendar calendar = Calendar.getInstance(new Locale("in", "ID"));
            /*
            //custom: change value to substract date form today
            calendar.add(Calendar.DATE, -1);
            */
            final Date loanDate = calendar.getTime();

            //set loan date
            loan.setCreatedAt(loanDate);

            //set
            LocalData.setLoan(loan);
            goToDetail();
        });
    }

    private void goToDetail() {
        Intent intent = new Intent(this, DetailActivity.class);
        startActivity(intent);
        finish();
    }
}