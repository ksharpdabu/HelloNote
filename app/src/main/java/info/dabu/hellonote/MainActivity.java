package info.dabu.hellonote;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {






    private Button mBtnText;
    private Button mBtnImage;
    private Button mBtnVideo;
    private ListView mLv;

    private NoteAdapter mAdapter;

    private NotesDB mNotesDB;
    private SQLiteDatabase mSQLiteDatabase;



    private Intent mIntent;
    private Cursor mCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        initView();



    }

    private void initView() {
        mBtnText = (Button) findViewById(R.id.btnText);
        mBtnText.setOnClickListener(this);
        mBtnImage = (Button) findViewById(R.id.btnImage);
        mBtnImage.setOnClickListener(this);
        mBtnVideo = (Button) findViewById(R.id.btnVideo);
        mBtnVideo.setOnClickListener(this);
        mLv = (ListView) findViewById(R.id.Lv);

        mNotesDB = new NotesDB(this);
        mSQLiteDatabase = mNotesDB.getWritableDatabase();

        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCursor.moveToPosition(position);
                Intent i = new Intent(MainActivity.this,DetailActivity.class);
                i.putExtra(NotesDB.ID,mCursor.getInt(mCursor.getColumnIndex(NotesDB.ID)));
                i.putExtra(NotesDB.CONTENT, mCursor.getString(mCursor.getColumnIndex(NotesDB.CONTENT)));

                i.putExtra(NotesDB.PATH, mCursor.getString(mCursor.getColumnIndex(NotesDB.PATH)));
                i.putExtra(NotesDB.VIDEO, mCursor.getString(mCursor.getColumnIndex(NotesDB.VIDEO)));
                i.putExtra(NotesDB.TIME, mCursor.getString(mCursor.getColumnIndex(NotesDB.TIME)));

                startActivity(i);


            }
        });





    }


    public void queryDB(){
        mCursor = mSQLiteDatabase.query(NotesDB.TABLE_NAME,null,null,null,null,null,null);

        mAdapter = new NoteAdapter(this, mCursor);
        mLv.setAdapter(mAdapter);
    }




    @Override
    public void onClick(View v) {

        mIntent = new Intent(this,AddContentActivity.class);
        switch (v.getId()){
            case R.id.btnText:
                mIntent.putExtra("flag","1");
                startActivity(mIntent);

                break;

            case R.id.btnImage:
                mIntent.putExtra("flag","2");
                startActivity(mIntent);
                break;
            case R.id.btnVideo:
                mIntent.putExtra("flag","3");
                startActivity(mIntent);

                break;

        }

    }


    @Override
    protected void onResume() {
        super.onResume();


        queryDB();
    }
}
