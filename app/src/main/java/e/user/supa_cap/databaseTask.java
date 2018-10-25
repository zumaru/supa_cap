package e.user.supa_cap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databaseTask extends SQLiteOpenHelper {
    //DBの名前
    private final static  String DB_Name = "pantu";
    //DBのバージョン
    private final static int DB_ver = 1;

    //コンストラクタ
    public databaseTask(Context context) {
        super(context, DB_Name, null, DB_ver);
    }
    //DB作成された時に呼ばれ、テーブルの作成をする
    @Override
    public  void  onCreate(SQLiteDatabase db){
        String sql = "";
        sql += "create table Mytable (";
        sql += "id integer primary key";
        sql += ",Instance text not null";
        sql += ",Client_ID text not null";
        sql += ",Client_Secret text not null";
       /* sql += ",accessToken text not null";
        sql += ",Name text not null";
        sql += ",UID text not null";
        sql += ",alive text not null";*/
        sql += ")";
        db.execSQL(sql);
    }
    //DBのアップデート時に呼ばれる。レコードを退避し、テーブルの再作成後にもどす
    @Override
    public void  onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
