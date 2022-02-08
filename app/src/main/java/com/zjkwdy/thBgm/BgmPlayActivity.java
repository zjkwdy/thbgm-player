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
    thbgm NowPlaying;
    Button stop_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bgm_play_activity);

        Intent intent = getIntent();
        String fmtName = intent.getStringExtra(MainActivity.FMT_NAME);
        String datName = intent.getStringExtra(MainActivity.DAT_NAME);
        this.bgmChoose = findViewById(R.id.sp);
        this.playButton = findViewById(R.id.play_btn);
        this.stop_btn = findViewById(R.id.stop_btn);
        this.stop_btn.setEnabled(false);
        try {
            this.fmt = new thfmt(fmtName);
            this.dat = new bgmdat(datName);
            String[] bgmNames = this.fmt.bgmNameList.toArray(new String[0]);
            ArrayAdapter<CharSequence> adapterNames = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, bgmNames);
            bgmChoose.setAdapter(adapterNames);
            playButton.setOnClickListener(new playOnclick());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void start() {
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
        this.stop_btn.setEnabled(true);
        this.NowPlaying.setPlayThread(dat);
    }

    public void stop(View view) {
        this.NowPlaying.stop();
        stop_btn.setEnabled(false);
    }
    public void pause() {
        this.NowPlaying.pause();
    }

    class playOnclick implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Button btn = (Button) view;
            if (btn.getText() == getString(R.string.button_play)) {
                start();
                btn.setText(R.string.button_pause);
            } else {
                pause();
                btn.setText(R.string.button_play);
            }
        }
    }
}