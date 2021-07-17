package com.example.loanpinalty.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loanpinalty.R;
import com.example.loanpinalty.data.local.LocalData;
import com.example.loanpinalty.data.model.Borrowing;
import com.example.loanpinalty.ui.main.MainActivity;
import com.example.loanpinalty.utils.Tools;
import com.google.android.material.button.MaterialButton;
import com.orhanobut.hawk.Hawk;

import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    private Borrowing borrowing;
    private MaterialButton btnPay;
    private TextView tvNameValue, tvBookNameValue, tvCreatedDateValue, tvPinaltyDayValue, tvTotalValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //if intent doesn't have extra borrowing
        if (!getIntent().hasExtra("borrowing")) {
            //close activity
            finish();
            return;
        }

        //get borrowing serializable data from intent and cast to Borrowing class
        borrowing = (Borrowing) getIntent().getSerializableExtra("borrowing");

        //init Hawk for LocalData
        Hawk.init(this).build();

        setUp();
    }

    private void setUp() {

        btnPay = findViewById(R.id.btnPay);
        tvNameValue = findViewById(R.id.tvNameValue);
        tvBookNameValue = findViewById(R.id.tvBookNameValue);
        tvCreatedDateValue = findViewById(R.id.tvCreatedDateValue);
        tvPinaltyDayValue = findViewById(R.id.tvPinaltyDayValue);
        tvTotalValue = findViewById(R.id.tvTotalValue);
        //set onclick event
        btnPay.setOnClickListener(v -> {

            //remove borrowing
            LocalData.removeBorrowing(borrowing);
            Toast.makeText(this, "Buku berhasil dikembalikan", Toast.LENGTH_SHORT).show();

            //set result OK
            setResult(RESULT_OK);

            //close activity
            finish();
        });


        tvNameValue.setText(borrowing.getName());
        tvBookNameValue.setText(borrowing.getBookName());

        //show loan created date
        tvCreatedDateValue.setText(Tools.formatDate(borrowing.getCreatedAt()));

        //calculate differences days between today and loan created at
        int diff = Tools.getDiffDay(new Date(), borrowing.getCreatedAt());

        //show differences days as penalty day
        tvPinaltyDayValue.setText(getString(R.string.pinalty_days, diff));

        //calculate totalpenalty cost based on pinalty day times loan cost
        double totalCost = LocalData.getFee() * diff;

        //show total loan
        tvTotalValue.setText(Tools.getPriceFormat(totalCost, true));

    }
}