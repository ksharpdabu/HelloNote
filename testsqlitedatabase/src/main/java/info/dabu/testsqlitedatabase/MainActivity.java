package info.dabu.testsqlitedatabase;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String DB_NAME = "helloDB";

    private Button mBtnCreateDB;
    private Button mBtnCloseDB;
    private Button mBtnDeleteDB;


    private SQLiteDatabase mSQLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mBtnCreateDB = (Button) findViewById(R.id.btnCreateDB);

        mBtnCreateDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSQLiteDatabase = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

                if (mSQLiteDatabase == null) {
                    Toast.makeText(MainActivity.this, "创建数据库" + DB_NAME + "失败", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "创建数据库" + DB_NAME + "成功", Toast.LENGTH_LONG).show();

                }


            }
        });


        mBtnCloseDB = (Button) findViewById(R.id.btnCloseDB);
        mBtnCloseDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mSQLiteDatabase != null) {

                    mSQLiteDatabase.close();
                    Toast.makeText(MainActivity.this, "数据库" + DB_NAME + "关闭成功", Toast.LENGTH_LONG).show();

                }


            }
        });


        mBtnDeleteDB = (Button) findViewById(R.id.btnDeleteDB);
        mBtnDeleteDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mSQLiteDatabase == null) {

                    boolean isDel = deleteDatabase(DB_NAME) ;

                    if (isDel) {
                        Toast.makeText(MainActivity.this, "数据库" + DB_NAME + "删除成功", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(MainActivity.this, "数据库" + DB_NAME + "删除失败", Toast.LENGTH_LONG).show();

                    }


                }
            }
        });


    }
}


