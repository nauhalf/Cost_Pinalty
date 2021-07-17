package com.example.loanpinalty.ui.fee;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.loanpinalty.R;
import com.example.loanpinalty.data.local.LocalData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.orhanobut.hawk.Hawk;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FeeActivity extends AppCompatActivity {
    private MaterialButton btnSave;
    private TextInputEditText etFee;
    private TextInputLayout etLayoutFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee);

        Hawk.init(this).build();

        setUp();
    }


    private void setUp() {

        btnSave = findViewById(R.id.btnSave);
        etFee = findViewById(R.id.etFee);
        etLayoutFee = findViewById(R.id.etLayoutFee);

        if (LocalData.getFee() != null) {
            DecimalFormat format = new DecimalFormat("#");
            etFee.setText(format.format(LocalData.getFee()));
        }
        //onclick event
        btnSave.setOnClickListener(v -> {
            // get value from input
            String value = etFee.getText().toString();

            //if empty show toast message
            if (value.isEmpty()) {
                etLayoutFee.setError("Denda tidak boleh kosong");
                return;
            }

            LocalData.setFee(Double.parseDouble(value));
            Toast.makeText(this, "Denda berhasil disimpan", Toast.LENGTH_SHORT).show();
            setResult(Activity.RESULT_OK);
            finish();

        });
    }
}