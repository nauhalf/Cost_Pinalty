package com.example.loanpinalty.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loanpinalty.R;
import com.example.loanpinalty.data.local.LocalData;
import com.example.loanpinalty.data.model.Borrowing;
import com.example.loanpinalty.ui.add.AddActivity;
import com.example.loanpinalty.ui.detail.DetailActivity;
import com.example.loanpinalty.ui.fee.FeeActivity;
import com.example.loanpinalty.utils.Tools;
import com.google.android.material.button.MaterialButton;
import com.orhanobut.hawk.Hawk;

public class MainActivity extends AppCompatActivity {

    private MaterialButton btnSetFee, btnAddBorrowing;
    private TextView tvFee, tvTotalPenalty;
    private RecyclerView rvBorrowing;
    private BorrowingAdapter mAdapter;


    //new way to use onActivityResult
    private final ActivityResultLauncher<Intent> mSetFeeLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                    //if getResultCode is RESULT_OK
                if (result.getResultCode() == Activity.RESULT_OK) {
                    //set new fee
                    setFee(LocalData.getFee());
                    refreshList();
                }
            });

    //new way to use onActivityResult
    private final ActivityResultLauncher<Intent> mBorrowingLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                //if getResultCode is RESULT_OK
                if (result.getResultCode() == Activity.RESULT_OK) {
                    //refresh list
                    refreshList();
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init Hawk for LocalData
        Hawk.init(this).build();

        setUp();
    }


    private void setUp() {

        btnSetFee = findViewById(R.id.btnSetFee);
        btnAddBorrowing = findViewById(R.id.btnAddBorrowing);
        tvFee = findViewById(R.id.tvFee);
        rvBorrowing = findViewById(R.id.rvBorrowing);
        tvTotalPenalty = findViewById(R.id.tvTotalPenalty);
        mAdapter = new BorrowingAdapter(borrowing -> {
            //on item click, go to detail activity
            goToDetail(borrowing);

        });

        //set adapter to recyclerview
        rvBorrowing.setAdapter(mAdapter);

        refreshList();
        //get fee
        Double fee = LocalData.getFee();

        setFee(fee);
        //onclick event
        btnSetFee.setOnClickListener(v -> {
            // get value from input
            goToSetFee();
        });

        btnAddBorrowing.setOnClickListener(v -> {
            goToAddBorrowing();
        });
    }

    private void setFee(Double fee) {
        //if fee is null
        if (fee == null) {
            //show message
            tvFee.setText("Belum diset");

            //disable button
            btnAddBorrowing.setEnabled(false);

        } else {
            //set fee to tvFee
            tvFee.setText(Tools.getPriceFormat(fee, true));

            //enable button
            btnAddBorrowing.setEnabled(true);
        }
    }

    private void goToSetFee() {
        //launch activity result to feeActivity and listen result
        Intent intent = new Intent(this, FeeActivity.class);
        mSetFeeLauncher.launch(intent);
    }

    private void goToDetail(Borrowing borrowing){
        //launch activity result to feeActivity and listen result
        Intent intent = new Intent(this, DetailActivity.class);

        //add data borrowing to send to DetailActivity
        intent.putExtra("borrowing", borrowing);
        mBorrowingLauncher.launch(intent);
    }

    private void goToAddBorrowing(){
        //launch activity result to feeActivity and listen result
        Intent intent = new Intent(this, AddActivity.class);
        mBorrowingLauncher.launch(intent);
    }

    private void refreshList() {
        mAdapter.setBorrowings(LocalData.getListBorrowing());
        tvTotalPenalty.setText(Tools.getPriceFormat(mAdapter.getTotalPenalty(), true));
    }
}