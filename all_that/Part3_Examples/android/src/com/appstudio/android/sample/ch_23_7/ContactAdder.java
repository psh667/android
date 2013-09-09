/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appstudio.android.sample.ch_23_7;

import java.util.ArrayList;
import java.util.Iterator;

import com.appstudio.android.sample.R;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public final class ContactAdder extends Activity 
                         implements OnAccountsUpdateListener  {
    public static final String TAG = "ContactsAdder";
    private ArrayList<AccountData> mAccounts;
    private AccountAdapter mAccountAdapter;
    private Spinner mAccountSpinner;
    private EditText mContactEmailEditText;
    private ArrayList<Integer> mContactEmailTypes;
    private Spinner mContactEmailTypeSpinner;
    private EditText mContactNameEditText;
    private EditText mContactPhoneEditText;
    private ArrayList<Integer> mContactPhoneTypes;
    private Spinner mContactPhoneTypeSpinner;
    private Button mContactSaveButton;
    private AccountData mSelectedAccount;

    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_adder);
        mAccountSpinner = 
            (Spinner) findViewById(R.id.accountSpinner);
        mContactNameEditText = 
            (EditText) findViewById(R.id.contactNameEditText);
        mContactPhoneEditText = 
            (EditText) findViewById(R.id.contactPhoneEditText);
        mContactEmailEditText = 
            (EditText) findViewById(R.id.contactEmailEditText);
        mContactPhoneTypeSpinner = 
           (Spinner)findViewById(R.id.contactPhoneTypeSpinner);
        mContactEmailTypeSpinner = 
           (Spinner)findViewById(R.id.contactEmailTypeSpinner);
        mContactSaveButton = 
            (Button) findViewById(R.id.contactSaveButton);

        // 전화 타입을 위한 스피너 정보 준비
        mContactPhoneTypes = new ArrayList<Integer>();
        mContactPhoneTypes.add(
           ContactsContract.CommonDataKinds.Phone.TYPE_HOME);
        mContactPhoneTypes.add(
           ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
        mContactPhoneTypes.add(
           ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        mContactPhoneTypes.add(
           ContactsContract.CommonDataKinds.Phone.TYPE_OTHER);
        // 메일타입을 위한 스피너 정보 준비
        mContactEmailTypes = new ArrayList<Integer>();
        mContactEmailTypes.add(
           ContactsContract.CommonDataKinds.Email.TYPE_HOME);
        mContactEmailTypes.add(
           ContactsContract.CommonDataKinds.Email.TYPE_WORK);
        mContactEmailTypes.add(
           ContactsContract.CommonDataKinds.Email.TYPE_MOBILE);
        mContactEmailTypes.add(
           ContactsContract.CommonDataKinds.Email.TYPE_OTHER);

        // 계정을 위한 스피너 준비
        mAccounts = new ArrayList<AccountData>();
        mAccountAdapter = new AccountAdapter(this, mAccounts);
        mAccountSpinner.setAdapter(mAccountAdapter);

        // 전화 종류를 위한 스피너 준비
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        Iterator<Integer> iter;
        iter = mContactPhoneTypes.iterator();
        while (iter.hasNext()) {
            adapter.add(ContactsContract.CommonDataKinds.Phone
                .getTypeLabel(
                    this.getResources(),
                    iter.next(),
                    getString(R.string.undefinedTypeLabel))
                .toString());
        }
        mContactPhoneTypeSpinner.setAdapter(adapter);
        mContactPhoneTypeSpinner.setPrompt(
                getString(R.string.selectLabel));

        // 이메일 종류를 위한 스피너 준비
        adapter = new ArrayAdapter<String>(this, 
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        iter = mContactEmailTypes.iterator();
        while (iter.hasNext()) {
            adapter.add(ContactsContract.CommonDataKinds.Email
                .getTypeLabel(
                    this.getResources(),
                    iter.next(),
                    getString(R.string.undefinedTypeLabel))
                .toString());
        }
        mContactEmailTypeSpinner.setAdapter(adapter);
        mContactEmailTypeSpinner.setPrompt(
                getString(R.string.selectLabel));

        // 계정정보를 조회한다.
        // updateImmediately를 true로 했기에 즉시 한번은 수행된다.
        AccountManager.get(this).
                addOnAccountsUpdatedListener(this, null, true);

        // 계정 선택 시 콜백 호출
        mAccountSpinner.setOnItemSelectedListener(
                                 new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, 
                                       View view, 
                                       int position, 
                                       long i) {
                updateAccountSelection();
            }

            public void onNothingSelected(
                                       AdapterView<?> parent) {
            }
        });
        mContactSaveButton.setOnClickListener(
                                   new View.OnClickListener() {
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
    }

    private void onSaveButtonClicked() {
        createContactEntry();
        finish();
    }

    protected void createContactEntry() {
        String name = 
            mContactNameEditText.getText().toString();
        String phone = 
            mContactPhoneEditText.getText().toString();
        String email = 
            mContactEmailEditText.getText().toString();
        int phoneType = mContactPhoneTypes.get(
           mContactPhoneTypeSpinner.getSelectedItemPosition());
        int emailType = mContactEmailTypes.get(
           mContactEmailTypeSpinner.getSelectedItemPosition());

        ArrayList<ContentProviderOperation> ops = 
            new ArrayList<ContentProviderOperation>();
        // 계정을 추가하기 위해 ContentProviderOperation의 목록
        // 을 만든 후 applyBatch()실행
        ops.add(ContentProviderOperation.
           newInsert(ContactsContract.RawContacts.CONTENT_URI)
           .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, 
                     mSelectedAccount.getType())
           .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, 
                     mSelectedAccount.getName()).build());
        
        ops.add(ContentProviderOperation.
           newInsert(ContactsContract.Data.CONTENT_URI)
           .withValueBackReference(
                   ContactsContract.Data.RAW_CONTACT_ID, 0)
           .withValue(ContactsContract.Data.MIMETYPE,
                      ContactsContract.CommonDataKinds
                         .StructuredName.CONTENT_ITEM_TYPE)
           .withValue(ContactsContract.CommonDataKinds
                         .StructuredName.DISPLAY_NAME, 
                      name).build()
        );
        
        ops.add(ContentProviderOperation.
           newInsert(ContactsContract.Data.CONTENT_URI)
           .withValueBackReference(ContactsContract.Data
                                  .RAW_CONTACT_ID, 0)
           .withValue(ContactsContract.Data.MIMETYPE,
                      ContactsContract.CommonDataKinds.Phone
                         .CONTENT_ITEM_TYPE)
           .withValue(ContactsContract.CommonDataKinds.Phone
                         .NUMBER, 
                      phone)
           .withValue(ContactsContract.CommonDataKinds
                          .Phone.TYPE, 
                      phoneType)
           .build()
        );
        
        ops.add(ContentProviderOperation.newInsert(
                             ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data
                                        .RAW_CONTACT_ID, 0)
            .withValue(ContactsContract.Data.MIMETYPE,
                       ContactsContract.CommonDataKinds.Email
                           .CONTENT_ITEM_TYPE)
            .withValue(ContactsContract.CommonDataKinds.Email
                           .DATA, email)
            .withValue(ContactsContract.CommonDataKinds.Email
                           .TYPE, emailType).build()
        );
        try {
            getContentResolver().applyBatch(
                    ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            // Display warning
            Context ctx = getApplicationContext();
            CharSequence txt = 
                getString(R.string.contactCreationFailure);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(ctx, txt, duration);
            toast.show();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroy() {
        AccountManager.get(
                this).removeOnAccountsUpdatedListener(this);
        super.onDestroy();
    }

    // 계정 변경 시 호출, 초기에도 한번은 호출된다.
    // 이를 통해 계정 값을 가져온다. 
    @Override
    public void onAccountsUpdated(Account[] a) {
        mAccounts.clear();
        AuthenticatorDescription[] accountTypes = 
            AccountManager.get(this).getAuthenticatorTypes();

        for (int i = 0; i < a.length; i++) {
            String systemAccountType = a[i].type;
            AuthenticatorDescription ad = 
                getAuthenticatorDescription(systemAccountType,
                                            accountTypes);
            AccountData data = new AccountData(a[i].name, ad);
            mAccounts.add(data);
        }

        // 계정목록이 변경되었음을 알려준다.
        mAccountAdapter.notifyDataSetChanged();
    }

    // 어떤 계정 타입인지를 반환한다. 예를 들면 구글인지등을 반환
    private static AuthenticatorDescription 
         getAuthenticatorDescription(String type,
                       AuthenticatorDescription[] dictionary) {
        for (int i = 0; i < dictionary.length; i++) {
            if (dictionary[i].type.equals(type)) {
                return dictionary[i];
            }
        }
        throw new RuntimeException("매칭되는 계정 타입이 없음.");
    }


    private void updateAccountSelection() {
        mSelectedAccount = 
            (AccountData) mAccountSpinner.getSelectedItem();
    }
    
    private class AccountData {
        private String mName;
        // 계정 타입
        private String mType;
        // 계정 타입 라벨
        private CharSequence mTypeLabel;
        // 계정 타입 아이콘
        private Drawable mIcon;

        public AccountData(String name, 
                AuthenticatorDescription description) {
            mName = name;
            if (description != null) {
                mType = description.type;
                String packageName = description.packageName;
                PackageManager pm = getPackageManager();
                if (description.labelId != 0) {
                    mTypeLabel = pm.getText(packageName, 
                            description.labelId, null);
                    if (mTypeLabel == null) {
                        throw new IllegalArgumentException(
                                "LabelID provided");
                    }
                } else {
                    mTypeLabel = "";
                }

                if (description.iconId != 0) {
                    mIcon = pm.getDrawable(packageName, 
                            description.iconId, null);
                    if (mIcon == null) {
                        throw new IllegalArgumentException(
                            "IconID provided, but drawable not"
                            + " found");
                    }
                } else {
                    mIcon = getResources().getDrawable(
                          android.R.drawable.sym_def_app_icon);
                }
            }
        }
        public String getName() {
            return mName;
        }
        public String getType() {
            return mType;
        }
        public CharSequence getTypeLabel() {
            return mTypeLabel;
        }
        public Drawable getIcon() {
            return mIcon;
        }
        public String toString() {
            return mName;
        }
    }
    /**
     * 계정을 위한 커스텀 어댑터 
     */
    private class AccountAdapter extends 
                                    ArrayAdapter<AccountData> {
        public AccountAdapter(Context context, 
                          ArrayList<AccountData> accountData) {
            super(context,android.R.layout.simple_spinner_item,
                    accountData);
            setDropDownViewResource(R.layout.account_entry);
        }

        @Override
        public View getDropDownView(int position, 
                          View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater layoutInflater = 
                    getLayoutInflater();
                convertView = layoutInflater.inflate(
                        R.layout.account_entry, parent, false);
            }
            TextView firstAccountLine = (TextView) convertView
                          .findViewById(R.id.firstAccountLine);
            TextView secondAccountLine = (TextView) convertView
                         .findViewById(R.id.secondAccountLine);
            ImageView accountIcon = (ImageView) convertView.
                                findViewById(R.id.accountIcon);
            AccountData data = getItem(position);
            firstAccountLine.setText(data.getName());
            secondAccountLine.setText(data.getTypeLabel());
            Drawable icon = data.getIcon();
            if (icon == null) {
                icon = getResources().getDrawable(
                            android.R.drawable.ic_menu_search);
            }
            accountIcon.setImageDrawable(icon);
            return convertView;
        }
    }
}
