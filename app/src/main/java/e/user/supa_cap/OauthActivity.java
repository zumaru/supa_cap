package e.user.supa_cap;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.sys1yagi.mastodon4j.MastodonClient;
import com.sys1yagi.mastodon4j.MastodonRequest;
import com.sys1yagi.mastodon4j.api.Scope;
import com.sys1yagi.mastodon4j.api.entity.auth.AccessToken;
import com.sys1yagi.mastodon4j.api.exception.Mastodon4jRequestException;
import com.sys1yagi.mastodon4j.api.method.Apps;
import com.sys1yagi.mastodon4j.api.entity.auth.AppRegistration;
import android.database.sqlite.SQLiteDatabase;
import okhttp3.OkHttpClient;

public class OauthActivity extends AppCompatActivity {
    static String insta;
    static String cliid;
    static String clisec;
    static String acct;
    static String OaUrl;
    static String authCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseTask task = new databaseTask(this);
        SQLiteDatabase db = task.getWritableDatabase();

        //InstDB task2 =new InstDB(this);
        //SQLiteDatabase db = task2.getWritableDatabase();

    }
    public void onClick(View v) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.account_manu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.account_action_settings) {


            //EditText、AlertDialogという文字入力とダイアログのクラスのインスタンスを作成
            final EditText InstanceName = new EditText(this);
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);


            //ダイアログにレイアウトを設定、タイトルの追加
            dialog.setView(InstanceName);
            dialog.setTitle("鯖の名前");

            //認証側ボタンの設定
            dialog.setPositiveButton("認証しにいく", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton){
                    //認証押したときの処理
                    //edittextの内容を代入
                    String inst = InstanceName.getText().toString();

                    //アプリ登録

                    appReg appregi = new appReg();
                    //一つのタスクに一つのインスタンスが必要
                    appregi.execute(inst);


                    }
            });
            //キャンセル側ボタンの設定
            dialog.setNegativeButton("やめとく", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            dialog.show();

        }

        return super.onOptionsItemSelected(item);
    }


private class  getAcct extends AsyncTask<String, String, String> {

    @Override
    protected void onPreExecute() {
        final AlertDialog.Builder Start1 = new AlertDialog.Builder(OauthActivity.this);
        Start1.setMessage("しばらくWait");
        Start1.show();
    }

    @Override
    protected String doInBackground(String... authcode) {

        MastodonClient client = new MastodonClient.Builder(OauthActivity.insta, new OkHttpClient.Builder(), new Gson()).build();
        Apps apps = new Apps(client);

        try {

            AccessToken t = apps.getAccessToken(OauthActivity.cliid, OauthActivity.clisec, authcode[0]).execute();
            System.out.println(t.getAccessToken());
            OauthActivity.acct = t.getAccessToken();


        } catch (Mastodon4jRequestException e){

            int statausCode = e.getResponse().code();
            System.out.println(statausCode);

        }


        return authcode[0];
    }

    @Override
    protected void onPostExecute(String result) {
    }


}
private class  appReg extends AsyncTask<String, String, String> {

    @Override
    protected void onPreExecute() {
        //バックグラウンド処理開始前にUIスレッド
        final AlertDialog.Builder Start1 = new AlertDialog.Builder(OauthActivity.this);
        Start1.setMessage("しばらくWait");
        Start1.show();
    }

    @Override
    protected String doInBackground(String... instanse) {

        MastodonClient client = new MastodonClient.Builder(instanse[0], new OkHttpClient.Builder(), new Gson()).build();
        Apps apps = new Apps(client);

        try {
            AppRegistration registration = apps.createApp("supa_cap", "urn:ietf:wg:oauth:2.0:oob",
                    new Scope(Scope.Name.ALL), "https://github.com/zumaru/supa_cap").execute();

            OauthActivity.insta = registration.getInstanceName();
            OauthActivity.cliid = registration.getClientId();
            OauthActivity.clisec = registration.getClientSecret();

            String rurl = "urn:ietf:wg:oauth:2.0:oob";
            String Ourl = apps.getOAuthUrl(registration.getClientId(), new Scope(Scope.Name.ALL), rurl);
            OauthActivity.OaUrl = Ourl;
            Intent intent = new  Intent(Intent.ACTION_VIEW, Uri.parse(Ourl));
            startActivity(intent);
            //String t = apps.getAccessToken(registration.getClientId(), registration.getClientSecret(), rurl, Ourl);
            //OauthActivity.acct = t;

        } catch (Mastodon4jRequestException e) {

            int statausCode = e.getResponse().code();
            System.out.println(statausCode);

        }


        return OauthActivity.OaUrl;
    }

    @Override
    protected void onPostExecute(String result) {

        //authコード入力ダイアログ
        final EditText authCodeE = new EditText(getApplicationContext());
        final AlertDialog.Builder end1 = new AlertDialog.Builder(OauthActivity.this);
        end1.setMessage("ここにアクセス先のコードをペースト").setView(authCodeE);

        end1.setPositiveButton("入力した", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton){
                OauthActivity.authCode = authCodeE.getText().toString();

                //getAcctを呼び出してアクセストークン取得
                getAcct geta = new getAcct();
                geta.execute(OauthActivity.authCode);
            }
        });
        end1.setNegativeButton("やめとく", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        end1.show();


    }
/*private static class DBadd {

    private  void dbopen(){

        databaseTask task = new databaseTask(this);
        SQLiteDatabase db = task.getWritableDatabase();

        DBadder(db, insta, cliid, clisec);
    }

    private void DBadder(SQLiteDatabase db,String insta, String cliid, String clisec){
        // 挿入するデータはContentValuesに格納
        ContentValues val = new ContentValues();
        val.put("Instance", insta);
        val.put("Client_ID", cliid);
        val.put("Client_Secret", clisec);

        // “bura”に1件追加
        db.insert("Mytable", null, val);
    }
}*/

 }
}
