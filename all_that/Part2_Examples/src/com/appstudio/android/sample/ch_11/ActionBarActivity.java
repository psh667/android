package com.appstudio.android.sample.ch_11;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;

public class ActionBarActivity extends Activity implements OnQueryTextListener {
	TextView mSearchText;
    int mSortMode = -1;
    private ShareActionProvider mShareActionProvider;
    private static final String SHARED_FILE_NAME = "shared.png";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchText = new TextView(this);
        setContentView(mSearchText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);
        
        getMenuInflater().inflate(R.menu.action_bar_share_action_provider, menu);
        MenuItem actionItem = menu.findItem(R.id.menu_item_share_action_provider_action_bar);
        ShareActionProvider actionProvider = (ShareActionProvider) actionItem.getActionProvider();
        actionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
        actionProvider.setShareIntent(createShareIntent());
        MenuItem overflowItem = menu.findItem(R.id.menu_item_share_action_provider_overflow);
        ShareActionProvider overflowProvider =
            (ShareActionProvider) overflowItem.getActionProvider();
        overflowProvider.setShareHistoryFileName(
            ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
        overflowProvider.setShareIntent(createShareIntent());

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mSortMode != -1) {
            Drawable icon = menu.findItem(mSortMode).getIcon();
            menu.findItem(R.id.action_sort).setIcon(icon);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }

    public void onSort(MenuItem item) {
        mSortMode = item.getItemId();
        invalidateOptionsMenu();
    }

    public boolean onQueryTextChange(String newText) {
        newText = newText.isEmpty() ? "" : "Query so far: " + newText;
        mSearchText.setText(newText);
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();
        return true;
    }

    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        Uri uri = Uri.fromFile(getFileStreamPath("shared.png"));
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        return shareIntent;
    }

    private void copyPrivateRawResuorceToPubliclyAccessibleFile() {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = getResources().openRawResource(R.raw.robot);
            outputStream = openFileOutput(SHARED_FILE_NAME,
                    Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
            byte[] buffer = new byte[1024];
            int length = 0;
            try {
                while ((length = inputStream.read(buffer)) > 0){
                    outputStream.write(buffer, 0, length);
                }
            } catch (IOException ioe) {

            }
        } catch (FileNotFoundException fnfe) {

        } finally {
            try {
                inputStream.close();
            } catch (IOException ioe) {

            }
            try {
                outputStream.close();
            } catch (IOException ioe) {

            }
        }
    }
    
    public static class SettingsActionProvider extends ActionProvider {

        private static final Intent sSettingsIntent = new Intent(Settings.ACTION_SETTINGS);
        private final Context mContext;
        
        public SettingsActionProvider(Context context) {
            super(context);
            mContext = context;
        }

        @Override
        public View onCreateActionView() {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(R.layout.action_bar_settings_action_provider, null);
            ImageButton button = (ImageButton) view.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(sSettingsIntent);
                }
            });
            return view;
        }

        @Override
        public boolean onPerformDefaultAction() {
            mContext.startActivity(sSettingsIntent);
            return true;
        }
    }

}
