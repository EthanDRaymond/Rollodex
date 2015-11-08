package com.psu.hack.rollodex.card;

import android.content.Context;

import com.psu.hack.rollodex.fileio.FileOperator;
import com.psu.hack.rollodex.fileio.Files;
import com.psu.hack.rollodex.ui.ViewCardActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ethanraymond on 11/7/15.
 */
public class ContactList {

    private static ArrayList<Card> contacts = null;

    public static ArrayList<Card> getContacts(Context context) {
        if (contacts == null) {
            contacts = new ArrayList<>();
            try {
                readContactsFromFile(context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return contacts;
    }

    public static void appendToContacts(Context context, Card card) {
        contacts.add(card);
        int i = 0;
        do {
            try {
                writeContactsToFile(context);
                break;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        } while (i < 5);
    }

    public static void removeContact(Context context, Card card) {
        contacts.remove(card);
        int i = 0;
        do {
            try {
                writeContactsToFile(context);
                break;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        } while (i < 5);
    }

    public static void writeContactsToFile(Context context) throws JSONException, IOException {
        FileOperator.writeToFile(context, Files.ADDRESS_BOOK, makeJSON().toString());
    }

    public static void readContactsFromFile(Context context) throws IOException {
        contacts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Card card = new Card("Ethan Raymond", "555-555-5555", "email@website.com");
            contacts.add(card);
        }
        //FileOperator.readFromFile(context, Files.ADDRESS_BOOK);
    }

    public static JSONArray makeJSON() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < contacts.size(); i++) {
            jsonArray.put(contacts.get(i).toJSON());
        }
        return jsonArray;
    }

}
