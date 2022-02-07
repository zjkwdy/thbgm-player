package com.zjkwdy.thBgm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String DAT_NAME = "com.zjkwdy.demoapp.datName";
    public static final String FMT_NAME = "com.zjkwdy.demoapp.fmtName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        publicMethods.verifyPermission(getApplicationContext(), this);
    }

    public void processBGM(View view) {
        Intent intent = new Intent(this, BgmPlayActivity.class);
        EditText fmtInput = findViewById(R.id.fmt);
        EditText datInput = findViewById(R.id.dat);
        String fmt = fmtInput.getText().toString();
        String dat = datInput.getText().toString();
        intent.putExtra(DAT_NAME, dat);
        intent.putExtra(FMT_NAME, fmt);
        startActivity(intent);
    }
}