package com.psu.hack.rollodex.fileio;

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

    /**
     * This writes the given data to a file.
     * @param context   used to find where the file is stored
     * @param filename  the name of the file
     * @param data      the data to write to the file
     * @throws IOException      thrown when there is a problem writing to the file
     */
    public static void writeToFile(Context context, String filename, String data) throws IOException {
        writeToFile(context, filename, data.getBytes());
    }

    /**
     * This writes the given data to a file.
     * @param context   used to find where the file is stored
     * @param filename  the name of the file
     * @param data      the data to write to the file
     * @throws IOException      thrown when there is a problem writing to the file
     */
    public static void writeToFile(Context context, String filename, byte[] data) throws IOException {
        File file = new File(context.getFilesDir(), filename);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(data);
        fileOutputStream.close();
    }

    /**
     * This reads the given data from a file.
     * @param context   used to find where the file is stored
     * @param filename  the name of the file
     * @param data      the data to read from the file
     * @throws IOException      thrown when there is a problem reading from the file
     * @return      the read data
     */
    public static String readFromFile(Context context, String filename, String data) {
        return readFromFile(context, filename, data);
    }

    /**
     * This reads the given data from a file.
     * @param context   used to find where the file is stored
     * @param filename  the name of the file
     * @param data      the data to read from the file
     * @throws IOException      thrown when there is a problem reading from the file
     * @return      the read data
     */
    public static String readFromFile(Context context, String filename, byte[] data) throws IOException {
        File file = new File(context.getFilesDir(), filename);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = null;
        fileInputStream.read(buffer);
        fileInputStream.close();
        return new String(buffer);
    }

}
