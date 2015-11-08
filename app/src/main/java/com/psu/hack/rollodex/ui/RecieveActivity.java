package com.psu.hack.rollodex.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.psu.hack.rollodex.R;
import com.psu.hack.rollodex.card.Card;
import com.psu.hack.rollodex.card.ContactList;
import com.psu.hack.rollodex.card.UserCard;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class RecieveActivity extends AppCompatActivity {

    private NfcAdapter nfcAdpt;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mIntentFilters;
    private String[][] mNFCTechLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve);

        nfcAdpt = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdpt == null) {
            Toast.makeText(this, "NFC not supported", Toast.LENGTH_LONG).show();
            finish();
        }

        if (!nfcAdpt.isEnabled()) {
            Toast.makeText(this, "Enable NFC.", Toast.LENGTH_LONG).show();
        }

        /*
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter != null) {
            Toast.makeText(this, "Read and NFC tag", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "This phone is not NFC enabled.", Toast.LENGTH_LONG).show();
        }

        // create an intent with tag data and deliver to this activity
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        // set an intent filter for all MIME data
        IntentFilter ndefIntent = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndefIntent.addDataType("text/plain");
            mIntentFilters = new IntentFilter[] { ndefIntent };
        } catch (Exception e) {
            Log.e("TagDispatch", e.toString());
        }

        mNFCTechLists = new String[][] { new String[] { NfcF.class.getName() } };
        */

    }

    @Override
    public void onNewIntent(Intent intent) {
        String action = intent.getAction();
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        String s = action + "\n\n" + tag.toString();

        Parcelable[] data = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        if (data != null) {
            try {
                for (int i = 0; i < data.length; i++) {
                    NdefRecord[] recs = ((NdefMessage)data[i]).getRecords();
                    for (int j = 0; j < recs.length; j++) {
                        if (recs[j].getTnf() == NdefRecord.TNF_WELL_KNOWN &&
                                Arrays.equals(recs[j].getType(), NdefRecord.RTD_TEXT)) {

                            byte[] payload = recs[j].getPayload();
                            try {
                                JSONObject jsonObject = new JSONObject(new String(payload));
                                Card c = new Card(
                                        jsonObject.getString(ViewCardActivity.name),
                                        jsonObject.getString(ViewCardActivity.phone),
                                        jsonObject.getString(ViewCardActivity.email)
                                );
                                ContactList.appendToContacts(this, c);
                                finish();
                                break;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String textEncoding;
                            if ((payload[0] & 0200) == 0) {
                                textEncoding = "UTF-8";
                            } else {
                                textEncoding = "UTF-16";
                            }
                            int langCodeLen = payload[0] & 0077;

                            s += ("\n\nNdefMessage[" + i + "], NdefRecord[" + j + "]:\n\"" +
                                    new String(payload, langCodeLen + 1,
                                            payload.length - langCodeLen - 1, textEncoding) +
                                    "\"");
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("TagDispatch", e.toString());
            }

        }

        Toast.makeText(this, s, Toast.LENGTH_LONG).show();



        //getTag(intent);

    }

    /*
    private void getTag(Intent i) {
        if (i == null)
            return ;

        String type = i.getType();
        String action = i.getAction();

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Log.d("Nfc", "Action NDEF Found");
            Parcelable[] parcs = i.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            int index = 0;
            for (Parcelable p : parcs) {
                // recNumberTxt.setText(String.valueOf(numRec));
                Toast.makeText(this, String.valueOf(p), Toast.LENGTH_LONG).show();
                NdefRecord[] records = msg.getRecords();
                for (NdefRecord record: records) {
                    short tnf = record.getTnf();
                    // Here we handle the payl
                }
                index++;
            }
        }
    }*/

    @Override
    public void onResume() {
        super.onResume();
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    void processIntent(Intent intent) {
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        Toast.makeText(this, new String(msg.getRecords()[0].getPayload()), Toast.LENGTH_LONG).show();
    }

}
