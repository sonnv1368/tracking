package com.sonnv1368.messagesms;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.commonsware.cwac.merge.MergeAdapter;
import com.sonnv1368.messagesms.adapter.SMSAdapter;
import com.sonnv1368.messagesms.model.SMSModel;

import java.util.ArrayList;

/**
 * Created by sonnv on 8/17/2016.
 */
public class TrackingSMSActivity extends Activity {
    private ListView lvSMS;
    private ArrayList<SMSModel> smsModels;
    private SMSAdapter smsAdapter;
    private Cursor mCursorSent, mCursorReceived;


    public static final String INBOX = "content://sms/inbox";
    public static final String SENT = "content://sms/sent";
    public static final String DRAFT = "content://sms/draft";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_sms);

        lvSMS = (ListView) findViewById(R.id.lvSMS);
        smsModels = new ArrayList<>();
        smsAdapter = new SMSAdapter(this, R.layout.item_custom_sms, smsModels);
        lvSMS.setAdapter(smsAdapter);

        readSMS(this);
    }

    private void readSMS(Context context) {

        mCursorSent = getContentResolver().query(Uri.parse(SENT), null, null, null, null);
        mCursorReceived = getContentResolver().query(Uri.parse(INBOX), null, null, null, null);

        if (mCursorSent.moveToFirst()) { // must check the result to prevent exception
            do {
                SMSModel smsModel = new SMSModel(
                        true,
                        mCursorSent.getString(mCursorSent.getColumnIndex("address")),
                        mCursorSent.getString(mCursorSent.getColumnIndex("date")),
                        mCursorSent.getString(mCursorSent.getColumnIndex("body")));
                smsModels.add(smsModel);
            } while (mCursorSent.moveToNext());
        }

        if (mCursorReceived.moveToFirst()) { // must check the result to prevent exception
            do {
                SMSModel smsModel = new SMSModel(
                        false,
                        mCursorReceived.getString(mCursorReceived.getColumnIndex("address")),
                        mCursorReceived.getString(mCursorReceived.getColumnIndex("date")),
                        mCursorReceived.getString(mCursorReceived.getColumnIndex("body")));
                smsModels.add(smsModel);
            } while (mCursorReceived.moveToNext());
        }


        smsAdapter.notifyDataSetChanged();
    }

}
