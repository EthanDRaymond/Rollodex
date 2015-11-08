package com.psu.hack.rollodex.ui;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
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
    private Button exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_contact);

        FName = (EditText) findViewById(R.id.fName);
        LName = (EditText) findViewById(R.id.lName);
        PHone = (EditText) findViewById(R.id.iphone);
        EMail = (EditText) findViewById(R.id.iemail);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String f = new String();
                String l = new String();
                String e = new String();
                String p = new String();

                if(FName.getText()!=null)
                    f = FName.getText().toString();
                else
                    f = "";
                if(LName.getText()!=null)
                    l = LName.getText().toString();
                else
                    l = "";
                if(EMail.getText()!=null)
                    e = EMail.getText().toString();
                else
                    e = "";
                if(PHone.getText()!=null)
                    p = PHone.getText().toString();
                else
                    p = "";
                
                c = new UserCard( f + l, p, e);
                finish();
            }
        });
    }

}