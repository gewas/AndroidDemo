package com.icer.contacts.manager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;

import com.icer.contacts.callback.OnFinishCallback;
import com.icer.contacts.util.StringUtil;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.icer.contacts.adapter.ContactsAdapter.KEY_AVATAR;
import static com.icer.contacts.adapter.ContactsAdapter.KEY_HEADER;
import static com.icer.contacts.adapter.ContactsAdapter.KEY_NAME;
import static com.icer.contacts.adapter.ContactsAdapter.KEY_PHONE;
import static com.icer.contacts.adapter.ContactsAdapter.KEY_TYPE;
import static com.icer.contacts.adapter.ContactsAdapter.VIEW_TYPE_CONTENT;
import static com.icer.contacts.adapter.ContactsAdapter.VIEW_TYPE_HEADER;

/**
 * Created by icer on 2015/10/27.
 */
public class ContactsManager {

    private static final String[] CONTACTS_DATA = new String[]
            {Phone.DISPLAY_NAME, Phone.NUMBER, Phone.PHOTO_ID, Phone.CONTACT_ID};

    private static final ContactsManager appContactsManager = new ContactsManager();

    private ContactsManager() {

    }

    public static ContactsManager getInstance() {
        return appContactsManager;
    }

    public void loadContacts(final Context context, final OnFinishCallback<List<Map<String, String>>> callback) {
        new AsyncTask<Void, List<Map<String, String>>, List<Map<String, String>>>() {

            @Override
            protected List<Map<String, String>> doInBackground(Void... params) {
                ContentResolver resolver = context.getContentResolver();
                Cursor cursor = resolver.query(Phone.CONTENT_URI, CONTACTS_DATA, null, null, ContactsContract.Contacts.SORT_KEY_PRIMARY);
                List<Map<String, String>> maps = null;
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        if (maps == null)
                            maps = new ArrayList<>();

                        Map<String, String> map = new HashMap<>();

                        map.put(KEY_TYPE, VIEW_TYPE_CONTENT + "");

                        String name = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
                        if (!StringUtil.isEmpty(name))
                            map.put(KEY_NAME, name);

                        String phone = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                        if (!StringUtil.isEmpty(phone))
                            map.put(KEY_PHONE, phone);

                        long photoId = cursor.getLong(cursor.getColumnIndex(Phone.PHOTO_ID));
                        if (photoId > 0) {
                            long contactId = cursor.getLong(cursor.getColumnIndex(Phone.CONTACT_ID));
                            String uriString = ContentUris.withAppendedId(
                                    ContactsContract.Contacts.CONTENT_URI, contactId).toString();
                            map.put(KEY_AVATAR, uriString);
                        }
                        maps.add(map);
                    }
                    cursor.close();
                }
                return maps;
            }

            @Override
            protected void onPostExecute(List<Map<String, String>> maps) {
                super.onPostExecute(maps);
                callback.onFinish(maps);
            }
        }.execute();
    }

    /**
     * @param maps A-Z# sorted contact list
     * @return A with header A-Z# sorted contact list
     */
    public List<Map<String, String>> addContactListHeader(List<Map<String, String>> maps) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);

        try {
            char indexLetter = 'A';
            for (int i = 0; i < maps.size(); i++) {
                Map<String, String> map = maps.get(i);
                String name = map.get(KEY_NAME);
                if (name.matches("^[a-zA-Z\\u4e00-\\u9fa5].*")) {
                    char firstLetter = 0;
                    if (name.matches("^[\\u4e00-\\u9fa5].*")) {

                        String[] pinYinArr = PinyinHelper.toHanyuPinyinStringArray(name.charAt(0), format);
                        if (pinYinArr == null || pinYinArr.length == 0) {
                            Log.e("PinYinParseError", name);
                            continue;
                        } else {
                            firstLetter = pinYinArr[0].charAt(0);
                        }
                    } else if (name.matches("^[a-zA-Z].*")) {
                        firstLetter = name.charAt(0);
                    }
                    if (firstLetter != 0) {
                        if (indexLetter != firstLetter) {
                            indexLetter = firstLetter;
                            Map<String, String> header = new HashMap<>();
                            header.put(KEY_TYPE, VIEW_TYPE_HEADER + "");
                            header.put(KEY_HEADER, indexLetter + "");
                            maps.add(i, header);
                            i++;
                        }
                    }
                } else {
                    indexLetter = '#';
                    Map<String, String> header = new HashMap<>();
                    header.put(KEY_TYPE, VIEW_TYPE_HEADER + "");
                    header.put(KEY_HEADER, indexLetter + "");
                    maps.add(i, header);
                    break;
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        return maps;
    }

}
