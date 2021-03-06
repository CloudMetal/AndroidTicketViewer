package org.ligi.ticketviewer;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import com.google.analytics.tracking.android.EasyTracker;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * User: ligi
 * Date: 2/7/13
 * Time: 2:27 AM
 */
class ImportAsyncTask extends AsyncTask<Void, Void, InputStream> {

    private Uri intent_uri;
    protected Activity ticketImportActivity;

    public ImportAsyncTask(Activity ticketImportActivity, Uri intent_uri) {
        this.ticketImportActivity = ticketImportActivity;
        this.intent_uri = intent_uri;
    }

    @Override
    protected InputStream doInBackground(Void... params) {

        long start_time = System.currentTimeMillis();

        if (intent_uri.toString().startsWith("content://")) {
            try {
                return ticketImportActivity.getContentResolver().openInputStream(intent_uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else
            try {
                return new BufferedInputStream(new URL("" + intent_uri).openStream(), 4096);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        EasyTracker.getTracker().trackTiming("load_time", System.currentTimeMillis() - start_time, "import", "" + intent_uri);
        return null;
    }


}
