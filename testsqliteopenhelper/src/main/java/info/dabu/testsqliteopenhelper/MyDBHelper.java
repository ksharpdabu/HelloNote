package info.dabu.testsqliteopenhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by AlexY on 2016/4/17.
 */
public class MyDBHelper extends SQLiteOpenHelper {

    private static final String  DB_NAME = "hellDB";

    private static final String TAG = "MyDBHelper";


    public MyDBHelper(Context context,  int version) {
        super(context, DB_NAME, null, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        // 输出日志
        Log.d(TAG, "Begin execute onCreate, cretating database table......");


        try {
            db.beginTransaction();

            db.execSQL("CREATE table  tb_hello(_id INTEGER AUTOINCREMENT PRIMARY KEY, name TEXT NOT NULL)");

            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

        Log.d(TAG, "Execute onCreate completed. Database created success.");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // 输出日志
        Log.d(TAG, "Begin execute onUpgrade, alert the database schema......");

        try{
            // 启用事务
            db.beginTransaction();

            // 修改表的的SQL语句
            String upgradeSql = "ALTER TABLE TBL_USER ADD COLUMN created_dt timestamp NOT NULL DEFAULT '2010-10-01' COLLATE NOCASE;";

            // 执行SQL语句
            db.execSQL(upgradeSql);

            // 设置事务成功标志
            db.setTransactionSuccessful();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            // 结束事务
            db.endTransaction();
        }
        Log.d(TAG, "Execute onUpgrade completed. Database schema updated success.");


    }


    public void insertRecord(){

    }

}
