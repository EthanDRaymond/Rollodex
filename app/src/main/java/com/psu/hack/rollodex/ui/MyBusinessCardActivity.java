package com.psu.hack.rollodex.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.psu.hack.rollodex.R;
import com.psu.hack.rollodex.card.UserCard;

import org.w3c.dom.Text;

public class MyBusinessCardActivity extends AppCompatActivity {
    private TextView name;
    private TextView email;
    private TextView phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_business_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        name = (TextView) findViewById(R.id.lblName);
        name.setText(UserCard.getN());

        email = (TextView) findViewById(R.id.lblEmail);
        email.setText(UserCard.getE());

        phone = (TextView) findViewById(R.id.lblPhone);
        phone.setText(UserCard.getN());


        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),ShareActivity.class));
            }
        });
    }

}
