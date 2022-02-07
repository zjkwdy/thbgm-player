package com.zjkwdy.thBgm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class bgmdat {
    RandomAccessFile dat;
    String fileName;


    public bgmdat(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        RandomAccessFile file = new RandomAccessFile(fileName, "r");
        this.dat = file;
    }

    public void seek(int pos) throws IOException {
        this.dat.seek(pos);
    }

    public byte[] read(int size) throws IOException {
        byte[] tmp = new byte[size];
        this.dat.read(tmp, 0, size);
        return tmp;
    }
}
