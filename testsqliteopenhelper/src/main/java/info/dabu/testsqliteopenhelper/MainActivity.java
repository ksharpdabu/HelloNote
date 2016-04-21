package info.dabu.testsqliteopenhelper;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    private static   int count = 2000;



    private static final int   INIT_DB_VERSION = 1;
    private static final int   UPGRADE_DB_VERSION = 2;


    private MyDBHelper mMyDBHelper;


    private SQLiteDatabase mSQLiteDatabase;


    private final static  String  SQL = "INSERT INTO tb_hello(name) values('ser')";




    private Button mBtnCreateDB;
    private Button mBtnUpgradeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mBtnCreateDB = (Button) findViewById(R.id.btnCreateDB);

        mBtnCreateDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyDBHelper = new MyDBHelper(MainActivity.this,INIT_DB_VERSION);

//                创建数据库
                mSQLiteDatabase = mMyDBHelper.getWritableDatabase();



            }
        });



        mBtnUpgradeDB = (Button) findViewById(R.id.btnUpgradeDB);

        mBtnUpgradeDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMyDBHelper = new MyDBHelper(getBaseContext(),INIT_DB_VERSION);

                mSQLiteDatabase = mMyDBHelper.getWritableDatabase();
                long  begain = System.currentTimeMillis();

                Log.e(TAG, "onClick: "+ begain );


                while (count > 0){
                    count--;
                    mSQLiteDatabase.execSQL(SQL);

                }

                long useTime = System.currentTimeMillis() - begain;
                 Log.e(TAG, "used: " + useTime);


            }
        });




    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

//        关闭数据库
        mMyDBHelper.close();
    }
}
