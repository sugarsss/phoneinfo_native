package com.niu.user.tools;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.ContactsContract;

import com.niu.user.APP;
import com.niu.user.entrty.MessageBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by niu on 2017/7/1.
 */

public class MessageUtil {


    /**
     * 获取短信数据
     *
     * @param lastPostTime 最后一次提交到数据库的时间
     * @return
     */
    public static List<MessageBean> getSmsInPhone(long lastPostTime) {
        //TODO:是否加权限判断
        List<MessageBean> messageList = new ArrayList<>();

        final String SMS_URI_ALL = "content://sms/";
        final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_SEND = "content://sms/sent";
        final String SMS_URI_DRAFT = "content://sms/draft";

        StringBuilder smsBuilder = new StringBuilder();

        try {
            ContentResolver cr = APP.getContext().getContentResolver();
            String[] projection = new String[]{"_id", "address", "person",
                    "body", "date", "type"};
            if (lastPostTime == 0) {
                lastPostTime = System.currentTimeMillis() - 90 * 24 * 60 * 60 * 1000l;
            }

            String where = " date >  "
                    + lastPostTime;
            Uri uri = Uri.parse(SMS_URI_ALL);
            Cursor cur = cr.query(uri, projection, where, null, "date desc");

            if (cur != null && cur.moveToFirst()) {
                String name;
                String phoneNumber;
                String smsbody;
                String date;
                String type;

                int phoneNumberColumn = cur.getColumnIndex("address");
                int smsbodyColumn = cur.getColumnIndex("body");
                int dateColumn = cur.getColumnIndex("date");
                int typeColumn = cur.getColumnIndex("type");

                do {
                    phoneNumber = cur.getString(phoneNumberColumn);
                    smsbody = cur.getString(smsbodyColumn);

                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(Long.parseLong(cur.getString(dateColumn)));
                    date = dateFormat.format(d);

                    int typeId = cur.getInt(typeColumn);
                    if (typeId == 1) {
                        type = "1";
                    } else {
                        type = "2";
                    }

                    name = getPeopleNameFromPerson(phoneNumber);

                    MessageBean message = new MessageBean();
                    message.setName(name);
                    message.setType(type);
                    message.setPhoneNumber(phoneNumber);
                    message.setSmsBody(smsbody);
                    message.setDate(date);
                    messageList.add(message);
                    if (smsbody == null) smsbody = "";
                } while (cur.moveToNext());
            } else {
            }

        } catch (SQLiteException ex) {
        }
        return messageList;
    }


    // 通过address手机号关联Contacts联系人的显示名字
    private static String getPeopleNameFromPerson(String address) {
        if (address == null || address == "") {
            return "( no address )\n";
        }

        String strPerson = "null";
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};

        Uri uri_Person = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, address);  // address 手机号过滤
        Cursor cursor = APP.getContext().getContentResolver().query(uri_Person, projection, null, null, null);

        if (cursor.moveToFirst()) {
            int index_PeopleName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            String strPeopleName = cursor.getString(index_PeopleName);
            strPerson = strPeopleName;
        }
        cursor.close();

        return strPerson;
    }
}
