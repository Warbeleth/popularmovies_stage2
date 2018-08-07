package com.weeturretstudio.warbeleth.android.popularmovies.utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;

public class UrlAsyncLoader extends AsyncTaskLoader<String> {

    private static final String TAG = UrlAsyncLoader.class.getSimpleName();
    private final WeakReference<AppCompatActivity> invokingActivity;
    private URL httpURL;
    private String resultingData;

    public UrlAsyncLoader(@NonNull AppCompatActivity context, URL urlToInvoke) {
        super(context);
        this.invokingActivity = new WeakReference<>(context);
        this.httpURL = urlToInvoke;
    }

    @Override
    protected void onStartLoading() {
        if(resultingData != null)
            deliverResult(resultingData);
        else
            forceLoad();
    }

    @Override
    public void deliverResult(String data) {
        resultingData = data;
        super.deliverResult(data);
    }

    @Override
    public String loadInBackground() {
        if(this.httpURL == null)
            return null;

        try {
            String httpResultString = NetworkUtils.getResponseFromHttpUrl(invokingActivity.get(), this.httpURL);
            Log.v(TAG, "UrlLoader: " + httpURL.toString() + " Result: " + httpResultString);
            return httpResultString;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }
}
