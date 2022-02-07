package com.zjkwdy.thBgm;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class thfmt {

    FileInputStream fmt;
    Map<String, thbgm> bgmMap;
    List<String> bgmNameList;

    public thfmt(String fileName) throws IOException {
        File file = new File(fileName);
        FileInputStream stream = new FileInputStream(file);
        List<String> bgmNameListtmp = new ArrayList<String>();
        Map<String, thbgm> map = new HashMap<>();
        this.fmt = stream;
        byte[] buffer = new byte[52];
        while (stream.read(buffer, 0, buffer.length) == 52) {
            //buffer为读出来的二进制数据，长度1024，最后一段数据小于1024
            byte[] bgmName = new byte[16];
            System.arraycopy(buffer, 0, bgmName, 0, 16);
            String bgmNameString = publicMethods.byteToStr(bgmName);
            Log.d("BGMNAME", bgmNameString);
            byte[] startTime = new byte[4];
            byte[] durTime = new byte[4];
            byte[] loopStart = new byte[4];
            byte[] sampleRate = new byte[4];
//            byte byteRate[] = new byte[4];
            byte[] channles = new byte[4];
            byte[] bits = new byte[4];
            System.arraycopy(buffer, 16, startTime, 0, 4);
            System.arraycopy(buffer, 28, durTime, 0, 4);
            System.arraycopy(buffer, 24, loopStart, 0, 4);
            System.arraycopy(buffer, 36, sampleRate, 0, 4);
//            System.arraycopy(buffer, 40, byteRate, 0, 4);
            System.arraycopy(buffer, 34, channles, 0, 2);
            System.arraycopy(buffer, 46, bits, 0, 2);
            int startTimeInt = publicMethods.bytesToIntLittle(startTime, 0);
            int durTimeInt = publicMethods.bytesToIntLittle(durTime, 0);
            int loopStartInt = publicMethods.bytesToIntLittle(loopStart, 0);
            int sampleRateInt = publicMethods.bytesToIntLittle(sampleRate, 0);
//            int byteRateInt = publicMethods.bytesToIntLittle(byteRate, 0);
            int channelsInt = publicMethods.bytesToIntLittle(channles, 0);
            int bitsInt = publicMethods.bytesToIntLittle(bits, 0);
            thbgm bgm = new thbgm(bgmNameString, startTimeInt, durTimeInt, loopStartInt, sampleRateInt, channelsInt, bitsInt);
            bgmNameListtmp.add(bgmNameString);
            map.put(bgmNameString, bgm);
        }
        this.bgmNameList = bgmNameListtmp;
        this.bgmMap = map;
    }
}
