package com.psu.hack.rollodex.ui.fileio;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ethanraymond on 11/7/15.
 */
public class FileOperator {

    public static void writeToFile(Context context, String filename, String data) throws IOException {
        writeToFile(context, filename, data.getBytes());
    }

    public static void writeToFile(Context context, String filename, byte[] data) throws IOException {
        File file = new File(context.getFilesDir(), filename);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(data);
        fileOutputStream.close();
    }

    public static String readFromFile(Context context, String filename, String data) {
        return readFromFile(context, filename, data);
    }

    public static String readFromFile(Context context, String filename, byte[] data) throws IOException {
        File file = new File(context.getFilesDir(), filename);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = null;
        fileInputStream.read(buffer);
        fileInputStream.close();
        return new String(buffer);
    }

}
