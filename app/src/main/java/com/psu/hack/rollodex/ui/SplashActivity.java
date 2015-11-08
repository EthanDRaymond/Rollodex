package com.psu.hack.rollodex.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.psu.hack.rollodex.card.ContactList;
import com.psu.hack.rollodex.card.UserCard;
import com.psu.hack.rollodex.fileio.FileOperator;

import com.psu.hack.rollodex.R;
import com.psu.hack.rollodex.fileio.FileOperator;
import com.psu.hack.rollodex.fileio.Files;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(!isUserNew()) {
                    try {
                        readContacts();
                        readUserCard();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(getBaseContext(), MainPageActivity.class));
                }
                else {
                    startActivity(new Intent(getBaseContext(), UserContactActivity.class));
                }
                finish();
            }
        });
    }

    public boolean  isUserNew()
    {

        try {
            String s = FileOperator.readFromFile(this, Files.USER_CARD);
            if(s.equals(""))
                return true;
            else
                return false;
        } catch (IOException e) {
            return true;
        }
    }

    public void readContacts() throws IOException {
        ContactList.readContactsFromFile(this);
    }

    public void readUserCard() throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject(
                FileOperator.readFromFile(
                        this,
                        Files.USER_CARD
                )
        );
        new UserCard(
                jsonObject.getString(ViewCardActivity.name),
                jsonObject.getString(ViewCardActivity.phone),
                jsonObject.getString(ViewCardActivity.email)
        );
    }

}
