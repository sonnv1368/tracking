package com.sonnv1368.messagesms.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sonnv1368.messagesms.R;
import com.sonnv1368.messagesms.model.SMSModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sonnv on 8/17/2016.
 */
public class SMSAdapter extends ArrayAdapter<SMSModel> {
    private ArrayList<SMSModel> smsModels;
    private LayoutInflater inflater;
    private Context mContext;

    public SMSAdapter(Context context, int resource, ArrayList<SMSModel> objects) {
        super(context, resource, objects);
        this.smsModels = objects;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_custom_sms, parent, false);

            holder.tvAddress = (TextView)convertView.findViewById(R.id.tvAddress);
            holder.tvAction = (TextView)convertView.findViewById(R.id.tvAction);
            holder.tvBody = (TextView)convertView.findViewById(R.id.tvBody);
            holder.tvDate = (TextView)convertView.findViewById(R.id.tvDate);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        SMSModel smsModel = smsModels.get(position);
        holder.tvDate.setText("Date: " + convertDateToString(smsModel.getDate()));
        holder.tvBody.setText(smsModel.getBody());
        if (smsModel.isSent()){
            holder.tvAction.setText("Sent");
        }else {
            holder.tvAction.setText("Received");
        }
        holder.tvAddress.setText(getContactName(mContext,smsModel.getAddress()));

        return convertView;
    }

    public static class ViewHolder{
        public TextView tvAddress;
        public TextView tvAction;
        public TextView tvDate;
        public TextView tvBody;
    }

    public static String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return phoneNumber;
        }
        String contactName = null;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
            if (contactName.equals("")){
                return phoneNumber;
            }
        }else {
            return phoneNumber;
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return contactName;
    }

    public String convertDateToString(String dateLongString){
        long val = Long.parseLong(dateLongString);
        Date date=new Date(val);
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        String dateText = df2.format(date);
        return dateText;
    }
}
