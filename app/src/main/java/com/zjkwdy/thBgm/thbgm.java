package com.zjkwdy.thBgm;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import java.io.IOException;

public class thbgm {
    public String name;
    public int startTime;
    public int durTime;
    public int loopStart;
    public int Sample;
    public int channels;
    public int bits;

    public Thread playThread;
    public volatile AudioTrack audioTrack;

    public thbgm(String name, int startTime, int durTime, int loopStart, int sample, int channels, int bits/*,int byteRate*/) {
        this.name = name;
        this.startTime = startTime;
        this.durTime = durTime;
        this.loopStart = loopStart;
        this.Sample = sample;
        this.channels = channels;
        this.bits = bits;
    }

    public void stop() {
        assert this.playThread != null;
        this.playThread = null;
        this.audioTrack.stop();
        this.audioTrack.release();
    }

    public void pause() {
        assert this.audioTrack != null;
        this.audioTrack.pause();
    }

    public void setPlayThread(bgmdat dat) {
        this.playThread = new Thread(() -> {
            try {
                if(audioTrack==null || audioTrack.getState()!=AudioTrack.STATE_INITIALIZED) {
                    audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, Sample, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT, durTime, AudioTrack.MODE_STATIC);
                    dat.seek(startTime);
                    byte[] musicBytes = dat.read(durTime);
                    audioTrack.write(musicBytes, 0, durTime);
                    int frameSize = durTime / audioTrack.getBufferSizeInFrames();
                    audioTrack.setLoopPoints(loopStart / frameSize, audioTrack.getBufferSizeInFrames(), -1);
                }
                audioTrack.play();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.playThread.start();
    }
}
