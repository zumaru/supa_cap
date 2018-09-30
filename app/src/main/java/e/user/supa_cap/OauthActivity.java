package e.user.supa_cap;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.sys1yagi.mastodon4j.MastodonClient;
import com.sys1yagi.mastodon4j.MastodonRequest;
import com.sys1yagi.mastodon4j.api.Scope;
import com.sys1yagi.mastodon4j.api.entity.auth.AccessToken;
import com.sys1yagi.mastodon4j.api.exception.Mastodon4jRequestException;
import com.sys1yagi.mastodon4j.api.method.Accounts;
import com.sys1yagi.mastodon4j.api.method.Apps;
import com.sys1yagi.mastodon4j.api.entity.auth.AppRegistration;



import okhttp3.OkHttpClient;

public class OauthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
            final EditText InstanceName = new EditText(OauthActivity.this);
            final AlertDialog.Builder dialog = new AlertDialog.Builder(OauthActivity.this);


            //setTitleメソッドでタイトル付け、setViewメソッドでEditTextをダイアログに
            dialog.setTitle("お前の鯖はどこだ？");
            dialog.setView(InstanceName);


            //認証側ボタンの設定
            dialog.setPositiveButton("認証しにいく", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton){
                    //認証押したときの処理
                    //edittextの内容を代入
                    String inst = InstanceName.getText().toString();//ここまで問題なし

                    //アプリ登録
                    api appregi = new api();
                    appregi.finst = inst;
                   // appregi.doInBackground();
                    final AlertDialog.Builder result = new AlertDialog.Builder(OauthActivity.this);
                    result.setMessage(appregi.finst);
                    result.show();
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

}
