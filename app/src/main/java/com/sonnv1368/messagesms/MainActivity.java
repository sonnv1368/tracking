package com.sonnv1368.messagesms;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.commonsware.cwac.merge.MergeAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView lvSMS;
    private SimpleCursorAdapter adapterSent, adapterReceived;
    private MergeAdapter mergeAdapter;
    private Cursor mCursorSent, mCursorReceived;


    public static final String INBOX = "content://sms/inbox";
    public static final String SENT = "content://sms/sent";
    public static final String DRAFT = "content://sms/draft";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvSMS = (ListView) findViewById(R.id.lvSMS);

        readSMS();
    }

    private void readSMS() {

        mCursorSent = getContentResolver().query(Uri.parse(SENT), null, null, null, null);
        mCursorReceived = getContentResolver().query(Uri.parse(INBOX), null, null, null, null);

        if (mCursorSent != null) {
            adapterSent = new SimpleCursorAdapter(this, R.layout.item_sms, mCursorSent,
                    new String[]{"date", "address", "body"},
                    new int[]{R.id.tvDate, R.id.tvAddress, R.id.tvbody}, 0);
        }

        if (mCursorReceived != null) {
            adapterReceived = new SimpleCursorAdapter(this, R.layout.item_sms, mCursorReceived,
                    new String[]{"date", "address", "body"},
                    new int[]{R.id.tvDate, R.id.tvAddress, R.id.tvbody}, 0);
        }

        mergeAdapter = new MergeAdapter();
        View sentLayout = getLayoutInflater().inflate(R.layout.item_sent, null);
        View receivedLayout = getLayoutInflater().inflate(R.layout.item_received, null);

        mergeAdapter.addView(sentLayout);
        mergeAdapter.addAdapter(adapterSent);

        mergeAdapter.addView(receivedLayout);
        mergeAdapter.addAdapter(adapterReceived);

        lvSMS.setAdapter(mergeAdapter);

    }

    public static String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return contactName;
    }
}
