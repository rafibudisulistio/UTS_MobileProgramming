package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    protected Cursor cursor;
    Database database;
    Button btn_update;
    EditText nama, nim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        database = new Database(this);
        nama = findViewById(R.id.inNama);
        nim = findViewById(R.id.inNIM);
        btn_update = findViewById(R.id.btn_update);

        SQLiteDatabase db = database.getReadableDatabase();
        cursor = db.rawQuery("select * from tbl_mhs where nama = '"+getIntent().getStringExtra("nama")+"'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            cursor.moveToPosition(0);
            nama.setText(cursor.getString(0).toString());
            nim.setText(cursor.getString(1).toString());
        }

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = database.getWritableDatabase();
                db.execSQL("update tbl_mhs set nama = '"+nama.getText().toString()+"', nim = '"+nim.getText().toString()+"' where nama = '"+getIntent().getStringExtra("nama")+"'");
                Toast.makeText(UpdateActivity.this, "Data Berhasil Diubah", Toast.LENGTH_SHORT);
                LocalActivity.la.RefreshList();
                finish();
            }
        });
    }
}