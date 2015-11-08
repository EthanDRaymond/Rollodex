package com.psu.hack.rollodex.ui;

import android.os.Bundle;
import android.app.Activity;
import android.widget.EditText;

import com.psu.hack.rollodex.R;
import com.psu.hack.rollodex.card.UserCard;

import org.json.JSONObject;

public class UserContact extends Activity {
    private EditText FName;
    private EditText LName;
    private EditText EMail;
    private EditText PHone;
    private UserCard c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_contact);
        FName = (EditText) findViewById(R.id.fName);
        LName = (EditText) findViewById(R.id.lName);
        PHone = (EditText) findViewById(R.id.iphone);
        EMail = (EditText) findViewById(R.id.iemail);
        c = new UserCard(FName.getText().toString() + LName.getText().toString(), PHone.getText().toString(), EMail.getText().toString());
    }

}
