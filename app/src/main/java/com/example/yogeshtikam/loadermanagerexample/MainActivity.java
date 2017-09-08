package com.example.yogeshtikam.loadermanagerexample;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int CONTACT_LOADER_ID = 1;
    private ListView mListVContacts;
    private SimpleCursorAdapter mSimpleCursorAdapter;
    private CursorLoader mCursorLoader;

    private RecyclerView mRecyclerContacts;

    private ContactsObject mContacts;

    private ArrayList<ContactsObject> mContactList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mListVContacts = (ListView) findViewById(R.id.lv_contact_list);

//        setUpCursorAdapter();

        mRecyclerContacts = (RecyclerView) findViewById(R.id.rv_contact_list);
        getSupportLoaderManager().initLoader(CONTACT_LOADER_ID, new Bundle(), this);
//        mListVContacts.setAdapter(mSimpleCursorAdapter);
        setUpList();

    }

    private void setUpCursorAdapter() {

        String[] uiBindFrom = {ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI/*,
                ContactsContract.CommonDataKinds.Phone.NUMBER*/};

        int[] uiBindTo = {R.id.contact_name, R.id.contact_img/*,R.id.contact_no*/};

        mSimpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.single_contact_item,
                null, uiBindFrom, uiBindTo, 0);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projectionFields = new String[]{ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI
               /* ContactsContract.CommonDataKinds.Phone.NUMBER*/};

        mCursorLoader = new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI,
                projectionFields, null, null, null);

        return mCursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        mSimpleCursorAdapter.swapCursor(data);
        if (data.getCount() > 0) {
            while (data.moveToNext()) {

                String id = data.getString(data.getColumnIndex(ContactsContract.Contacts._ID));
                String photoUri = data.getString(data.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
                String name = data.getString(data.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

//                Log.v("id : ", id);
//                Log.v("name : ", name);

                mContacts = new ContactsObject();

                mContacts.setmContactName(name);
                mContacts.setmContactPhoto(photoUri);


                Cursor phoneCursor = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{id},
                        null);
                if (phoneCursor != null) {
                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(
                                phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        Log.v("phone : ", phoneNumber);
                        mContacts.setmContactNumber(phoneNumber);
                    }
                    phoneCursor.close();
                }
                mContactList.add(mContacts);
            }
        }

        for (int i = 0; i < mContactList.size(); i++) {

            Log.i("Name", mContactList.get(i).getmContactName());
            Log.i("Number", mContactList.get(i).getmContactNumber());
//            Log.i("Photo",mContactList.get(i).getmContactPhoto());
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
//        mSimpleCursorAdapter.swapCursor(null);
    }

    private void setUpList() {
        ContactListAdapter contactAdapter = new ContactListAdapter(mContactList, getApplicationContext());
        mRecyclerContacts.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerContacts.setAdapter(contactAdapter);
    }
}
