package com.example.loanpinalty.detail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loanpinalty.R;
import com.example.loanpinalty.data.local.LocalData;
import com.example.loanpinalty.data.model.Loan;
import com.example.loanpinalty.main.MainActivity;
import com.example.loanpinalty.utils.Tools;
import com.google.android.material.button.MaterialButton;
import com.orhanobut.hawk.Hawk;

import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    private Loan loan;
    private MaterialButton btnPay;
    private TextView tvCostValue, tvCreatedDateValue, tvPinaltyDayValue, tvPenaltyCostValue, tvTotalValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //init Hawk for LocalData
        Hawk.init(this).build();

        //Get Loan data from local Data
        loan = LocalData.getLoan();

        //if loan is null
        if(loan == null){
            //back to main activity
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        setUp();
    }

    private void setUp(){

        btnPay = findViewById(R.id.btnPay);
        tvCostValue = findViewById(R.id.tvCostValue);
        tvCreatedDateValue = findViewById(R.id.tvCreatedDateValue);
        tvPinaltyDayValue = findViewById(R.id.tvPinaltyDayValue);
        tvPenaltyCostValue = findViewById(R.id.tvPenaltyCostValue);
        tvTotalValue =  findViewById(R.id.tvTotalValue);
        //set onclick event
        btnPay.setOnClickListener(v -> {

            //delete loan
            LocalData.deleteLoan();

            //back to main activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });


        //show loan value
        tvCostValue.setText(Tools.getPriceFormat(loan.getValue(), true));

        //show loan created date
        tvCreatedDateValue.setText(Tools.formatDate(loan.getCreatedAt()));

        //calculate differences days between today and loan created at
        int diff = Tools.getDiffDay(new Date(), loan.getCreatedAt());

        //show differences days as penalty day
        tvPinaltyDayValue.setText(getString(R.string.pinalty_days, diff));

        //calculate pinalty cost based on pinalty day times loan cost
        double pinaltyCost = loan.getValue() * diff;

        //calculate total cost baased on pinalty cost plus loan cost
        double totalCost = loan.getValue() + pinaltyCost;

        //show pinalty cost value
        tvPenaltyCostValue.setText(Tools.getPriceFormat(pinaltyCost, true));

        //show total loan
        tvTotalValue.setText(Tools.getPriceFormat(totalCost, true));




    }
}