package com.zjkwdy.thBgm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public static final String DAT_NAME = "com.zjkwdy.demoapp.datName";
    public static final String FMT_NAME = "com.zjkwdy.demoapp.fmtName";

    private Spinner groupChoose;
    private Button goBtn;
    private ImageButton delBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        publicMethods.verifyPermission(getApplicationContext(), this);
        this.groupChoose = findViewById(R.id.groupChoose);
        this.goBtn = findViewById(R.id.go);
        this.delBtn = findViewById(R.id.delGroupBtn);
        getGroups();
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getGroups();
    }
    public void add(View view){
        Intent intent = new Intent(this,addBgmGroup.class);
        startActivityForResult(intent,0);
    }
    public void getGroups() {
        SharedPreferences sp = getSharedPreferences("groups", Context.MODE_PRIVATE);
        Set<String> allEntries = sp.getAll().keySet();
        String[] groups;
        if(allEntries.size()==0){
            this.goBtn.setEnabled(false);
            this.delBtn.setEnabled(false);
            groups = new String[]{"啥也没有"};
        } else {
            this.goBtn.setEnabled(true);
            this.delBtn.setEnabled(true);
            groups = allEntries.toArray(new String[0]);
        }
        ArrayAdapter<CharSequence> adapterNames = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, groups);
        groupChoose.setAdapter(adapterNames);
    }

    public void delGroup(View view){
        String selectGroup = this.groupChoose.getSelectedItem().toString();
        SharedPreferences sp = getSharedPreferences(selectGroup,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
        sp = getSharedPreferences("groups", Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.remove(selectGroup);
        editor.commit();
        getGroups();
    }

    private String[] getGroup(String groupName) {
        SharedPreferences sp = getSharedPreferences(groupName,Context.MODE_PRIVATE);
        String fmt = sp.getString("fmt","");
        String dat = sp.getString("dat","");
        return new String[]{fmt,dat};
    }

    public void processBGM(View view) {
        String selectGroup = this.groupChoose.getSelectedItem().toString();
        String[] group = getGroup(selectGroup);
        Intent intent = new Intent(this, BgmPlayActivity.class);
        String fmt = group[0];
        String dat = group[1];
        intent.putExtra(DAT_NAME, dat);
        intent.putExtra(FMT_NAME, fmt);
        startActivity(intent);
    }
}