package com.msi.manning.chapter5.providerexplorer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.Contacts;
import android.provider.Contacts.PeopleColumns;
import android.provider.Contacts.PhonesColumns;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProviderExplorer extends Activity {

    private EditText addName;
    private EditText addPhoneNumber;
    private EditText editName;
    private EditText editPhoneNumber;
    private Button addContact;
    private Button editContact;

    private long contactId;

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        this.setContentView(R.layout.provider_explorer);

        this.addName = (EditText) findViewById(R.id.add_name);
        this.addPhoneNumber = (EditText) findViewById(R.id.add_phone_number);
        this.editName = (EditText) findViewById(R.id.edit_name);
        this.editPhoneNumber = (EditText) findViewById(R.id.edit_phone_number);
        this.addContact = (Button) findViewById(R.id.add_contact_button);
        this.addContact.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                addContact();
            }
        });
        this.editContact = (Button) findViewById(R.id.edit_contact_button);
        this.editContact.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                editContact();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        List<Contact> contacts = getContacts();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200,
            android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        if (contacts != null) {
            LinearLayout editLayout = (LinearLayout) findViewById(R.id.edit_buttons_layout);
            LinearLayout deleteLayout = (LinearLayout) findViewById(R.id.delete_buttons_layout);
            params.setMargins(10, 0, 0, 0);
            for (Contact c : contacts) {
                ContactButton contactEditButton = new ContactButton(this, c);
                contactEditButton.setText(c.toString());
                editLayout.addView(contactEditButton, params);
                contactEditButton.setOnClickListener(new OnClickListener() {

                    public void onClick(final View v) {
                        ContactButton view = (ContactButton) v;
                        editName.setText(view.contact.name);
                        editPhoneNumber.setText(view.contact.phoneNumber);
                        contactId = view.contact.id;
                    }
                });

                ContactButton contactDeleteButton = new ContactButton(this, c);
                contactDeleteButton.setText("Delete " + c.name);
                deleteLayout.addView(contactDeleteButton, params);
                contactDeleteButton.setOnClickListener(new OnClickListener() {

                    public void onClick(final View v) {
                        ContactButton view = (ContactButton) v;
                        contactId = view.contact.id;
                        deleteContact();
                    }
                });
            }
        } else {
            LinearLayout layout = (LinearLayout) findViewById(R.id.edit_buttons_layout);
            TextView empty = new TextView(this);
            empty.setText("No current contacts");
            layout.addView(empty, params);
        }
    }

    //
    // begin resolver methods
    //

    private List<Contact> getContacts() {
        List<Contact> results = null;
        long id = 0L;
        String name = null;
        String phoneNumber = null;
        String[] projection = new String[] { BaseColumns._ID, PeopleColumns.NAME, PhonesColumns.NUMBER };
        ContentResolver resolver = getContentResolver();
        Cursor cur = resolver.query(Contacts.People.CONTENT_URI, projection, null, null,
            Contacts.People.DEFAULT_SORT_ORDER);
        while (cur.moveToNext()) {
            if (results == null) {
                results = new ArrayList<Contact>();
            }
            id = cur.getLong(cur.getColumnIndex(BaseColumns._ID));
            name = cur.getString(cur.getColumnIndex(PeopleColumns.NAME));
            phoneNumber = cur.getString(cur.getColumnIndex(PhonesColumns.NUMBER));
            results.add(new Contact(id, name, phoneNumber));
        }
        return results;
    }

    private void addContact() {
        ContentResolver resolver = getContentResolver();
        ContentValues values = new ContentValues();

        // create Contacts.People record first, using helper method to get person in "My Contacts"
        // group
        values.put(PeopleColumns.NAME, this.addName.getText().toString());
        Uri personUri = Contacts.People.createPersonInMyContactsGroup(resolver, values);
        Log.v("ProviderExplorer", "ADD personUri - " + personUri.toString());

        // append other contact data, like phone number
        values.clear();
        Uri phoneUri = Uri.withAppendedPath(personUri, Contacts.People.Phones.CONTENT_DIRECTORY);
        Log.v("ProviderExplorer", "ADD phoneUri - " + phoneUri.toString());
        values.put(PhonesColumns.TYPE, PhonesColumns.TYPE_MOBILE);
        values.put(PhonesColumns.NUMBER, this.addPhoneNumber.getText().toString());

        // insert manually (this time no helper method)
        resolver.insert(phoneUri, values);

        startActivity(new Intent(this, ProviderExplorer.class));
    }

    private void deleteContact() {
        Uri personUri = Contacts.People.CONTENT_URI;
        personUri = personUri.buildUpon().appendPath(Long.toString(this.contactId)).build();
        Log.v("ProviderExplorer", "DELETE personUri - " + personUri.toString());
        getContentResolver().delete(personUri, null, null);
        startActivity(new Intent(this, ProviderExplorer.class));
    }

    private void editContact() {
        ContentResolver resolver = getContentResolver();
        ContentValues values = new ContentValues();

        // another way to append to a Uri, use buildUpon
        Uri personUri = Contacts.People.CONTENT_URI.buildUpon().appendPath(Long.toString(this.contactId)).build();
        Log.v("ProviderExplorer", "EDIT personUri - " + personUri.toString());

        // once we have the person Uri we can edit person values, like name
        values.put(PeopleColumns.NAME, this.editName.getText().toString());
        resolver.update(personUri, values, null, null);

        // separate step to update phone values
        values.clear();
        // just edit the first phone, with id 1
        // (in real life we would need to parse more phone data and edit the correct phone out of a
        // possible many)
        Uri phoneUri = Uri.withAppendedPath(personUri, Contacts.People.Phones.CONTENT_DIRECTORY + "/1");
        values.put(PhonesColumns.NUMBER, this.editPhoneNumber.getText().toString());
        resolver.update(phoneUri, values, null, null);

        startActivity(new Intent(this, ProviderExplorer.class));
    }

    //
    // end resolver methods
    //

    //
    // addtl classes
    //

    private class Contact {

        public long id;
        public String name;
        public String phoneNumber;

        public Contact(final long id, final String name, final String phoneNumber) {
            this.id = id;
            this.name = name;
            this.phoneNumber = phoneNumber;
        }

        @Override
        public String toString() {
            return this.name + "\n" + this.phoneNumber;
        }
    }

    private class ContactButton extends Button {

        public Contact contact;

        public ContactButton(final Context ctx, final Contact contact) {
            super(ctx);
            this.contact = contact;
        }
    }

}
