package com.psu.hack.rollodex.ui;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import com.psu.hack.rollodex.R;

public class ViewCardActivity extends Activity {

    private TextView n;
    private TextView e;
    private TextView p;
    public static String name = "name";
    public static String email = "email";
    public static String phone = "phone";

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);
        Bundle b = getIntent().getExtras();

        n = (TextView) findViewById(R.id.nameTitle);
        n.setText(b.getString(name));

        e = (TextView) findViewById(R.id.emailTitle);
        e.setText(b.getString(email));

        p = (TextView) findViewById(R.id.phoneTitle);
        p.setText(b.getString(phone));

    }

}
