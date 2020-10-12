package com.example.aplikasisqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplikasisqlite.helper.DbHelper;

public class AddEdit extends AppCompatActivity {

    EditText etId, etName, etAddress;
    Button btnSubmit, btnCancel;
    DbHelper SQlite = new DbHelper(this);
    String id, name, address;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bundle = getIntent().getExtras();
        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etNama);
        etAddress = findViewById(R.id.etAddress);
        btnSubmit = findViewById(R.id.btn_submit);
        btnCancel = findViewById(R.id.btn_cancel);

        if (getIntent().hasExtra(MainActivity.TAG_ID)&&getIntent().hasExtra(MainActivity.TAG_NAME)&&getIntent().hasExtra(MainActivity.TAG_ADDRESS)) {
            id = bundle.getString(MainActivity.TAG_ID);
            name = bundle.getString(MainActivity.TAG_NAME);
            address = bundle.getString(MainActivity.TAG_ADDRESS);
            setTitle("Edit Data");
            etId.setText(id);
            etName.setText(name);
            etAddress.setText(address);
        }else {
            setTitle("Add Data");
        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (etId.getText().toString().equals("")) {
                        save();
                    } else {
                        edit();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blank();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                blank();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void blank() {
        etName.requestFocus();
        etId.setText(null);
        etName.setText(null);
        etAddress.setText(null);
    }
    public void save(){
//        if (String.valueOf(etName.getText()).equals(null) ||  String.valueOf(etAddress.getText()).equals(null)
//                || String.valueOf(etName.getText()).equals("") ||  String.valueOf(etAddress.getText()).equals("")){
        if (TextUtils.isEmpty(etName.getText()) || TextUtils.isEmpty(etAddress.getText())){
            Toast.makeText(this, "Input Nama dan Alamat, Masih Kosong", Toast.LENGTH_SHORT).show();
        }else{
            SQlite.insert(etName.getText().toString().trim(),etAddress.getText().toString().trim());
            blank();
            finish();
        }
    }
    public void edit(){
        if (TextUtils.isEmpty(etName.getText()) || TextUtils.isEmpty(etAddress.getText())){
            Toast.makeText(this, "Input Nama dan Alamat, Masih Kosong", Toast.LENGTH_SHORT).show();
        }else{
            SQlite.update(Integer.parseInt(etId.getText().toString().trim()),etName.getText().toString().trim(),etAddress.getText().toString().trim());
            blank();
            finish();
        }
    }
}
