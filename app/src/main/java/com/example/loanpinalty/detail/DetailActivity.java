package com.example.loanpinalty.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.loanpinalty.R;
import com.example.loanpinalty.data.local.LocalData;
import com.example.loanpinalty.data.model.Loan;
import com.example.loanpinalty.databinding.ActivityDetailBinding;
import com.example.loanpinalty.main.MainActivity;
import com.example.loanpinalty.utils.Tools;
import com.orhanobut.hawk.Hawk;

import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private Loan loan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

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
        }
        setUp();
    }

    private void setUp(){

        //set onclick event
        binding.btnPay.setOnClickListener(v -> {

            //delete loan
            LocalData.deleteLoan();

            //back to main activity
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });


        //show loan value
        binding.tvCostValue.setText(Tools.getPriceFormat(loan.getValue(), true));

        //show loan created date
        binding.tvCreatedDateValue.setText(Tools.formatDate(loan.getCreatedAt()));

        //calculate differences days between today and loan created at
        int diff = Tools.getDiffDay(new Date(), loan.getCreatedAt());

        //show differences days as penalty day
        binding.tvPinaltyDayValue.setText(getString(R.string.pinalty_days, diff));

        //calculate pinalty cost based on pinalty day times loan cost
        double pinaltyCost = loan.getValue() * diff;

        //calculate total cost baased on pinalty cost plus loan cost
        double totalCost = loan.getValue() + pinaltyCost;

        //show pinalty cost value
        binding.tvPenaltyCostValue.setText(Tools.getPriceFormat(pinaltyCost, true));

        //show total loan
        binding.tvTotalValue.setText(Tools.getPriceFormat(totalCost, true));




    }
}