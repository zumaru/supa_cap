package e.user.supa_cap;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.sys1yagi.mastodon4j.MastodonClient;
import com.sys1yagi.mastodon4j.api.Scope;
import com.sys1yagi.mastodon4j.api.entity.auth.AppRegistration;
import com.sys1yagi.mastodon4j.api.method.Apps;
import com.sys1yagi.mastodon4j.api.exception.Mastodon4jRequestException;
import okhttp3.OkHttpClient;



public class api extends AsyncTask<Uri.Builder, Void, String> {
    String finst;

    @Override
    public String doInBackground(Uri.Builder... builder) {


        MastodonClient client = new MastodonClient.Builder(finst, new OkHttpClient.Builder(), new Gson()).build();
        Apps apps = new Apps(client);

        AppRegistration registration = null;
        try {
            registration = apps.createApp("supa_cap", "urn:ietf:wg:oauth:2.0:oob",
                    new Scope(Scope.Name.ALL), "https://github.com/zumaru/supa_cap").execute();
        } catch (Mastodon4jRequestException e) {
            e.printStackTrace();
        }

        String insta = registration.getInstanceName();
        String cliid = registration.getClientId();
        String secre = registration.getClientSecret();
        return doInBackground();
    }
    @Override
    protected void onPostExecute(String result){
    }


}
