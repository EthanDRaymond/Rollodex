package com.psu.hack.rollodex.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.psu.hack.rollodex.R;
import com.psu.hack.rollodex.card.UserCard;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.Locale;

public class ShareActivity extends AppCompatActivity {

    private NfcAdapter mNfcAdapter;
    private NdefMessage mNdefMessage;

    private PendingIntent nfcPendingIntent;
    private IntentFilter[] intentFiltersArray;
    private NfcAdapter nfcAdpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ViewCardActivity.name, UserCard.name);
            jsonObject.put(ViewCardActivity.phone, UserCard.phoneNumber);
            jsonObject.put(ViewCardActivity.email, UserCard.emailAddress);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mNdefMessage = new NdefMessage(createTextRecord(jsonObject.toString(), Locale.ENGLISH, true));

        mNfcAdapter.setNdefPushMessage(mNdefMessage, this);

        /*
        Intent nfcIntent =  new Intent(this, getClass());

        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        nfcPendingIntent = PendingIntent.getActivity(this, 0, nfcIntent, 0);

        IntentFilter tagIntentFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);

        try {
            tagIntentFilter.addDataType("text/plain");
            intentFiltersArray = new IntentFilter[]{tagIntentFilter};
        } catch (Throwable t) {
            t.printStackTrace();
        }*/

        /*
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter != null) {
            Toast.makeText(this, "Tap to beam to another NFC device.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "This phone is not NFC enabled.", Toast.LENGTH_LONG).show();
        }

        mNdefMessage = new NdefMessage(
                createNewTextRecord("This is a test message!!!", Locale.ENGLISH, true)
        );

        mNfcAdapter.setNdefPushMessage(mNdefMessage, this);
        */

    }

    @Override
    protected void onResume() {
        super.onResume();

        /*
        if (mNfcAdapter != null) {
            mNfcAdapter.setNdefPushMessage(mNdefMessage, this);
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();

        nfcAdpt.disableForegroundDispatch(this);

    }

    public static NdefRecord createNewTextRecord(String text, Locale locale, boolean encodeInUtf8) {
        byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));

        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        byte[] textBytes = text.getBytes(utfEncoding);

        int utfBit = encodeInUtf8 ? 0 : (1 << 7);
        char status = (char)(utfBit + langBytes.length);

        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte)status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);

        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
    }

    public NdefRecord createTextRecord(String payload, Locale locale, boolean encodeInUtf8) {
        byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));
        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        byte[] textBytes = payload.getBytes(utfEncoding);
        int utfBit = encodeInUtf8 ? 0 : (1 << 7);
        char status = (char) (utfBit + langBytes.length);
        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);
        NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
                NdefRecord.RTD_TEXT, new byte[0], data);
        return record;
    }

}
