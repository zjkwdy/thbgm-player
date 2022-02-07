package com.zjkwdy.thBgm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addBgmGroup extends AppCompatActivity {

    private Button add;
    private EditText name;
    private EditText fmt;
    private EditText dat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bgm_group);
        this.add = findViewById(R.id.add);
        this.name = findViewById(R.id.groupname);
        this.fmt = findViewById(R.id.fmtPath);
        this.dat = findViewById(R.id.datPath);
    }
    public void add(View view){
        String nameStr,fmtStr,datStr;
        nameStr = this.name.getText().toString();
        fmtStr = this.fmt.getText().toString();
        datStr = this.dat.getText().toString();
        addNewGroup(nameStr,datStr,fmtStr);
    }
    private void commitEditor(SharedPreferences.Editor editor){
        editor.apply();
        editor.commit();
    }

    public void addNewGroup(String groupName,String datPath,String fmtPath){
        if(publicMethods.fileIsExists(datPath) || publicMethods.fileIsExists(fmtPath)) {
            SharedPreferences groups = getSharedPreferences("groups", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = groups.edit();
            editor.putString(groupName, groupName);
            commitEditor(editor);

            SharedPreferences bgm = getSharedPreferences(groupName, Context.MODE_PRIVATE);
            editor = bgm.edit();
            editor.putString("fmt", fmtPath);
            editor.putString("dat", datPath);
            commitEditor(editor);
            Toast.makeText(this,groupName+"Add Success!",Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this,"Input File NotFound",Toast.LENGTH_LONG).show();
        }
    }
}