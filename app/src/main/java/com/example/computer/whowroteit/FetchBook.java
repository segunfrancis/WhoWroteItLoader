package com.example.computer.whowroteit;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FetchBook extends AsyncTask<String, Void, String> {
    /*
     * First parameter - input-parameter type
     * Second parameter - progress-indicator type
     * Third parameter - result type
     */

    private WeakReference<TextView> mTitleText;
    private WeakReference<TextView> mAuthorText;

    public FetchBook(TextView titleText, TextView authorText) {
        this.mTitleText = new WeakReference<>(titleText);
        this.mAuthorText = new WeakReference<>(authorText);
    }

    @Override
    protected String doInBackground(String... strings) {

        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            int i = 0;
            String title = null;
            String authors = null;

            // Look for results in the items array, exiting when both the title
            // and author are found or when all items have been checked.
            while (i < itemsArray.length() && (authors == null && title == null)) {
                // Get the current item information
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Move to the next item
                i++;

                // Because the references to the TextView objects are WeakReference
                // objects, you have to dereference them using the get() method.
                if (title != null && authors != null) {
                    mTitleText.get().setText(title);
                    mAuthorText.get().setText(authors);
                } else {
                    mTitleText.get().setText(R.string.no_results);
                    mAuthorText.get().setText("");
                }
            }
        } catch (Exception e) {
            // if onPostExecute does not receive a proper JSON string,
            // update the UI to show failed results.
            mTitleText.get().setText(R.string.no_results);
            mAuthorText.get().setText("");
            e.printStackTrace();
        }
    }
}
