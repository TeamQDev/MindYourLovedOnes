package com.mindyourlovedones.healthcare.DropBox;

import android.os.AsyncTask;
import android.util.Log;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.http.OkHttp3Requestor;
import com.dropbox.core.v2.DbxClientV2;

/**
 * Singleton instance of {@link DbxClientV2} and friends
 */
public class DropboxClientFactory {

    private static DbxClientV2 sDbxClient;

    public static void init(String accessToken) {
        if (sDbxClient == null) {
            DbxRequestConfig requestConfig = DbxRequestConfig.newBuilder("examples-v2-demo")
                .withHttpRequestor(new OkHttp3Requestor(OkHttp3Requestor.defaultOkHttpClient()))
                .build();

            sDbxClient = new DbxClientV2(requestConfig, accessToken);
        }
    }

    public static DbxClientV2 getClient() {
        if (sDbxClient == null) {
            throw new IllegalStateException("Client not initialized.");
        }
        return sDbxClient;
    }

    public static void revokeClient(final CallBack callBack) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    sDbxClient.auth().tokenRevoke();
                } catch (DbxException e) {
                    Log.e("Dropbox", "Access Revoke Exception", e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                sDbxClient = null;
                if (callBack != null)
                    callBack.onRevoke();
            }
        }.execute();

    }

    public interface CallBack{
        void onRevoke();
    }
}
