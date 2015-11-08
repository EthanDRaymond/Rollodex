package com.psu.hack.rollodex.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.psu.hack.rollodex.R;
import com.psu.hack.rollodex.card.Card;
import com.psu.hack.rollodex.card.ContactList;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainPageActivity extends Activity {

    private RelativeLayout mainBox;
    private LinearLayout cardList;
    private ArrayList<RelativeLayout> cardViews;

    private boolean deleteMode;
    private ArrayList<Integer> deleteList;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        this.mainBox = (RelativeLayout) findViewById(R.id.main_page_mainBox);
        this.cardList = (LinearLayout) findViewById(R.id.main_page_card_list);
        this.cardViews = new ArrayList<>();
        this.deleteList = new ArrayList<>();
        buildCardList();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (deleteMode) {
            leaveDeleteMode(false);
        } else {
            try {
                ContactList.writeContactsToFile(this);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finish();
        }
    }

    public void buildCardList() {
        ArrayList<Card> contacts = ContactList.getContacts(this);
        cardList.removeAllViews();
        cardViews.clear();
        if (contacts.size() == 0) {
            cardList.addView(buildEmptyListItem());
        } else {
            for (int i = 0; i < contacts.size(); i++) {
                RelativeLayout cardView = buildCardView(contacts.get(i));
                cardList.addView(cardView);
                cardViews.add(cardView);
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
        relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                enterDeleteMode();
                return false;
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

    public CheckBox buildCheckBox() {
        CheckBox checkBox = new CheckBox(this);
        checkBox.setChecked(false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        checkBox.setLayoutParams(params);
        return checkBox;
    }

    public Button buildDeleteButton() {
        Button button = new Button(this);
        button.setText("Delete Selected");
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(10, 10, 10, 10);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        button.setLayoutParams(params);
        return button;
    }

    public void addToDeleteList(int index) {
        deleteList.add(index);
    }

    public void enterDeleteMode() {
        this.deleteMode = true;
        for (int i = 0; i < cardViews.size(); i++) {
            CheckBox checkBox = buildCheckBox();
            final int index = i;
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        addToDeleteList(index);
                    }
                }
            });
            cardViews.get(i).addView(checkBox);
        }
        deleteButton = buildDeleteButton();
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveDeleteMode(true);
            }
        });
        this.mainBox.addView(deleteButton);
    }

    public void leaveDeleteMode(boolean doErase) {
        if (doErase) {
            for (int i = 0; i < deleteList.size(); i++) {
                int deleteIndex = deleteList.get(i);
                ContactList.removeContact(this, ContactList.getContacts(this).get(deleteIndex));
            }
        }
        this.deleteMode = false;
        this.mainBox.removeView(deleteButton);
        deleteButton = null;
        buildCardList();
    }

}
