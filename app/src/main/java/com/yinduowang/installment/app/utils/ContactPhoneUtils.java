package com.yinduowang.installment.app.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * 获取一条联系人回调
 * */
public class ContactPhoneUtils {


    public static void getContactPhoneInfo11(Context context, Intent data, int code, CallBack callBack) throws Exception {
        Uri contactData = data.getData();
        Cursor cursor;
        cursor = context.getContentResolver().query(contactData, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int phoneColumn = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
            int phoneNum = cursor.getInt(phoneColumn);

            if (phoneNum > 0) {
                // 获得联系人的ID号
                int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                String contactId = cursor.getString(idColumn);
                // 获得联系人电话的cursor
                Cursor phone = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
                if (phone != null && phone.moveToFirst()) {
                    for (; !phone.isAfterLast(); phone.moveToNext()) {
                        int index = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        int typeindex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                        // 取得联系人名字
                        int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
                        if (callBack != null) {
                            callBack.callBack(cursor.getString(nameFieldColumnIndex), StringUtil.toNum(phone.getString(index)));
                        }
                    }
                    if (!phone.isClosed()) {
                        phone.close();
                    }
                }
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
    }


    public interface CallBack {
        void callBack(String name, String phone);
    }

}
