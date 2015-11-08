package com.psu.hack.rollodex.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.psu.hack.rollodex.R;
import com.psu.hack.rollodex.card.Card;
import com.psu.hack.rollodex.card.ContactList;

import java.util.ArrayList;

public class MainPageActivity extends Activity {

    private LinearLayout cardList;
    private View item1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        this.cardList = (LinearLayout) findViewById(R.id.main_page_card_list);
        buildCardList();
    }

    public void buildCardList() {
        ArrayList<Card> contacts = ContactList.getContacts(this);
        cardList.removeAllViews();
        if (contacts.size() == 0) {
            cardList.addView(buildEmptyListItem());
        } else {
            for (int i = 0; i < contacts.size(); i++) {
                cardList.addView(
                        buildCardView(
                                contacts.get(i)
                        )
                );
            }
        }
    }

    public TextView buildEmptyListItem() {
        TextView textView = new TextView(this);
        textView.setText("No Contacts");
        textView.setTextSize(20);
        textView.setTextColor(Color.DKGRAY);
        textView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(20, 20, 20, 20);
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    public RelativeLayout buildCardView(final Card card) {
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                )
        );
        relativeLayout.addView(buildCardText(card.getName()));
        relativeLayout.addView(buildSpacer());
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ViewCardActivity.class);
                i.putExtra(ViewCardActivity.name, card.getName());
                i.putExtra(ViewCardActivity.phone, card.getPhoneNumber());
                i.putExtra(ViewCardActivity.email, card.getEmailAddress());
                startActivity(i);
            }
        });
        return relativeLayout;
    }

    public TextView buildCardText(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(12, 12, 12, 12);
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    public View buildSpacer() {
        View spacer = new View(this);
        spacer.setBackgroundResource(R.color.color_spacer);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                2
        );
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        return spacer;
    }

}
