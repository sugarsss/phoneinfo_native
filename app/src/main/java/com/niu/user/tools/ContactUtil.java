package com.niu.user.tools;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.niu.user.entrty.ContactBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by niu on 2017/7/1.
 */

public class ContactUtil {


    /**
     * 获取通讯录中所有联系人的简单信息
     *
     * @throws Throwable
     */
    public static List<ContactBean> getAllContact(Context context) throws Throwable {

        List<ContactBean> list = new ArrayList<>();
        Map<String, String> contactIdMap = new HashMap<String, String>();
        //获取联系人信息的Uri
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //获取ContentResolver
        ContentResolver contentResolver = context.getContentResolver();
        //查询数据，返回Cursor
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            return list;
        }
        while (cursor.moveToNext()) {
            StringBuilder sb = new StringBuilder();
            //获取联系人的ID
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            if (contactIdMap.containsKey(contactId)) {
                continue;
            }
            contactIdMap.put(contactId, contactId);
            ContactBean contactBean = new ContactBean();
            //获取联系人的姓名
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            contactBean.setName(name);
            //构造联系人信息
            sb.append("contactId=").append(contactId).append(",Name=").append(name);
            //查询电话类型的数据操作
            Cursor phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null, null);
            contactBean.setPhoneNumber(null);
            while (phones.moveToNext()) {
                String phoneNumber = phones.getString(phones.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                //添加Phone的信息
                contactBean.setPhoneNumber(phoneNumber);
            }
            phones.close();

            //查询Email类型的数据操作
            Cursor emails = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId,
                    null, null);
            contactBean.setEmail(null);
            while (emails.moveToNext()) {
                String emailAddress = emails.getString(emails.getColumnIndex(
                        ContactsContract.CommonDataKinds.Email.DATA));
                //添加Email的信息
                contactBean.setEmail(emailAddress);
            }
            emails.close();
            list.add(contactBean);
        }
        cursor.close();
        return list;
    }


}
