package com.zjkwdy.thBgm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;


public class BgmPlayActivity extends AppCompatActivity {
    thfmt fmt;
    bgmdat dat;
    Spinner bgmChoose;
    Button playButton;
    Button stopButton;
    thbgm NowPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String fmtName = intent.getStringExtra(MainActivity.FMT_NAME);
        String datName = intent.getStringExtra(MainActivity.DAT_NAME);
        this.bgmChoose = findViewById(R.id.sp);
        this.playButton = findViewById(R.id.play_btn);
        this.stopButton = findViewById(R.id.stop_btn);
        try {
            this.fmt = new thfmt(fmtName);
            this.dat = new bgmdat(datName);
            String[] bgmNames = this.fmt.bgmNameList.toArray(new String[this.fmt.bgmNameList.size()]);
            ArrayAdapter<CharSequence> adapterNames = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, bgmNames);
            bgmChoose.setAdapter(adapterNames);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        stop(this.stopButton);
    }

    public void stop(View view) {
        this.NowPlaying.audioTrack.stop();
        this.NowPlaying.audioTrack.release();
        playButton.setEnabled(true);
        stopButton.setEnabled(false);
    }

    public void start(View view) {
        String choicedBgmName = bgmChoose.getSelectedItem().toString();
        TextView sT = findViewById(R.id.startTime);
        TextView dT = findViewById(R.id.durTime);
        TextView lS = findViewById(R.id.loopStart);
        TextView sP = findViewById(R.id.sample);
        this.NowPlaying = fmt.bgmMap.get(choicedBgmName);
        assert this.NowPlaying != null;
        sT.setText(publicMethods.hex(this.NowPlaying.startTime));
        dT.setText(publicMethods.hex(this.NowPlaying.durTime));
        lS.setText(publicMethods.hex(this.NowPlaying.loopStart));
        sP.setText(publicMethods.str(this.NowPlaying.Sample));
        this.NowPlaying.setPlayThread(dat);
        playButton.setEnabled(false);
        stopButton.setEnabled(true);
    }
}