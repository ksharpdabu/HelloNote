package info.dabu.hellonote;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DetailActivity";

    private ImageView mImage;
    private VideoView mVideo;
    private TextView mText;
    private Button mBtnDel;
    private Button mBtnBack;



    private int mId;
    private String mContent;
    private String mPathStr;
    private String mVideoStr;

    private NotesDB mNotesDB;
    private SQLiteDatabase mSQLiteDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        mNotesDB = new NotesDB(this);
        mSQLiteDatabase = mNotesDB.getWritableDatabase();


        Intent i = getIntent();
        if ( null !=  i){

            mId = i.getIntExtra(NotesDB.ID,0);
            mPathStr = i.getStringExtra(NotesDB.PATH);
            mVideoStr = i.getStringExtra(NotesDB.VIDEO);
            mContent = i.getStringExtra(NotesDB.CONTENT);

            Log.d(TAG, "onCreate: "+ mId);
        }




        initView();
    }

    private void initView() {
        mImage = (ImageView) findViewById(R.id.image);
        mVideo = (VideoView) findViewById(R.id.video);
        mText = (TextView) findViewById(R.id.text);
        mBtnDel = (Button) findViewById(R.id.btnDel);
        mBtnDel.setOnClickListener(this);
        mBtnBack = (Button) findViewById(R.id.btnBack);
        mBtnBack.setOnClickListener(this);

        if ( !TextUtils.isEmpty(mPathStr)){
            mImage.setImageBitmap(BitmapFactory.decodeFile(mPathStr));
            mImage.setVisibility(View.VISIBLE);
        }else {
            mImage.setVisibility(View.GONE);

        }

        if ( !TextUtils.isEmpty(mVideoStr)){
            mVideo.setVisibility(View.VISIBLE);

            mVideo.setVideoPath(mVideoStr);
            mVideo.start();

        }else {
            mVideo.setVisibility(View.GONE);

        }

        if ( !TextUtils.isEmpty(mContent)){
            mText.setText(mContent);

        }





    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnDel:

                deleteItem();
                finish();

                break;
            case R.id.btnBack:
                finish();

                break;
        }

    }

    private void deleteItem() {


//        mSQLiteDatabase.execSQL("DELETE FROM  "+NotesDB.TABLE_NAME+ " WHERE "+NotesDB.ID
//        +" = "+

        mSQLiteDatabase.delete(NotesDB.TABLE_NAME,"_id="+ mId,null);




    }
}
