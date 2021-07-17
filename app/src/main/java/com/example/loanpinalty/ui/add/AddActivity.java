package com.example.loanpinalty.ui.add;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loanpinalty.R;
import com.example.loanpinalty.data.local.LocalData;
import com.example.loanpinalty.data.model.Borrowing;
import com.example.loanpinalty.utils.Tools;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.orhanobut.hawk.Hawk;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {
    private MaterialButton btnSave;
    private TextInputEditText etName, etBookName;
    private TextInputLayout etLayoutName, etLayoutBookName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Hawk.init(this).build();
        setUp();
    }

    private void setUp() {
        btnSave = findViewById(R.id.btnSave);
        etName = findViewById(R.id.etName);
        etBookName = findViewById(R.id.etBookName);
        etLayoutName = findViewById(R.id.etLayoutName);
        etLayoutBookName = findViewById(R.id.etLayoutBookName);

        btnSave.setOnClickListener(v -> {
            //get etName value
            String name = etName.getText().toString();

            //get bookName value
            String bookName = etBookName.getText().toString();

            //if validate input success
            if (validate(name, bookName)) {

                //create borrowing oject
                Borrowing borrowing = new Borrowing();
                borrowing.setId(Tools.generateUUID());
                borrowing.setName(name);
                borrowing.setBookName(bookName);

                final Calendar calendar = Calendar.getInstance(new Locale("in", "ID"));

/*                //custom: change value to substract date form today
                List<Borrowing> list = LocalData.getListBorrowing();
                int total = list==null ? 0 : list.size();
                calendar.add(Calendar.DATE, -(total+7));*/

                final Date borrowingDate = calendar.getTime();

                borrowing.setCreatedAt(borrowingDate);

                //add borrowing to shared preferences
                LocalData.addBorrowing(borrowing);

                Toast.makeText(this, "Peminjaman berhasil dibuat", Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

    }

    private boolean validate(String name, String bookName) {
        int errorCount = 0;

        //if name is empty
        if (name.isEmpty()) {
            //show error
            etLayoutName.setError("Nama tidak boleh kosong");
            errorCount++;
        }

        //if bookname is empty
        if (bookName.isEmpty()) {
            //show error
            etLayoutBookName.setError("Nama Buku tidak boleh kosong");
            errorCount++;
        }

        //if errorCount == 0, then validate success, else validate failed
        return errorCount == 0;
    }
}