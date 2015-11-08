package com.psu.hack.rollodex.ui;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.psu.hack.rollodex.R;

public class MainPageActivity extends Activity {

    private LinearLayout cardList;
    private View item1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        this.cardList = (LinearLayout) findViewById(R.id.main_page_card_list);
        this.item1 = findViewById(R.id.main_page_item1);
        this.item1.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ViewCardActivity.class);
                intent.putExtra(ViewCardActivity.name, "Ethan Raymond");
                intent.putExtra(ViewCardActivity.phone, "555-555-5555");
                intent.putExtra(ViewCardActivity.email, "email@website.com");
                startActivity(intent);
            }
        });
    }

}
