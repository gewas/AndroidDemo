package com.icer.contacts.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.icer.contacts.R;
import com.icer.contacts.adapter.ContactsAdapter;
import com.icer.contacts.callback.OnFinishCallback;
import com.icer.contacts.manager.ContactsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getCanonicalName();

    private ListView mListView;

    private ContactsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initAdapter();
        loadData();
    }

    private void initData() {
        mAdapter = new ContactsAdapter(this, new ArrayList<Map<String, String>>());
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.contactListView);
    }

    private void initAdapter() {
        mListView.setAdapter(mAdapter);
    }

    private void loadData() {
        ContactsManager.getInstance().loadContacts(this, new OnFinishCallback<List<Map<String, String>>>() {
            @Override
            public void onFinish(List<Map<String, String>> maps) {
                if (maps != null && !maps.isEmpty()) {
                    mAdapter.addData(ContactsManager.getInstance().addContactListHeader(maps));
                }
            }
        });
    }
}
