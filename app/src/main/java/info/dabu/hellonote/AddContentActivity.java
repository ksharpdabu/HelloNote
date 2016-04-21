package info.dabu.hellonote;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddContentActivity extends AppCompatActivity implements View.OnClickListener {



    private String flag ;


    private ImageView mImage;
    private VideoView mVideo;
    private EditText mEdit;

    private Button mBtnSave;
    private Button mBtnCancel;


    private NotesDB mNotesDB;
    private SQLiteDatabase mSQLiteDatabase;


    private File imageFile;
    private File videoFile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);


        mNotesDB = new NotesDB(this);
        mSQLiteDatabase = mNotesDB.getWritableDatabase();

        Intent intent = getIntent();

        if (intent != null ){

            if ( !TextUtils.isEmpty(intent.getStringExtra("flag"))){

                flag = intent.getStringExtra("flag");
            }


        }

        mImage = (ImageView) findViewById(R.id.image);
        mVideo = (VideoView) findViewById(R.id.video);
        mEdit = (EditText) findViewById(R.id.edit);
        mBtnSave = (Button) findViewById(R.id.btnSave);
        mBtnSave.setOnClickListener(this);
        mBtnCancel = (Button) findViewById(R.id.btnCancel);
        mBtnCancel.setOnClickListener(this);

        initView();

    }

    private void initView() {

        if ( !TextUtils.isEmpty(flag)){

            if ( flag.equals("1")){
                mImage.setVisibility(View.GONE);
                mVideo.setVisibility(View.GONE);

                mEdit.setVisibility(View.VISIBLE);



            }else if (flag.equals("2")){
                mImage.setVisibility(View.VISIBLE);
                mVideo.setVisibility(View.GONE);

                mEdit.setVisibility(View.VISIBLE);

                Intent intentImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+
                    "/"+ System.currentTimeMillis()+".jpg");

                intentImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));

                startActivityForResult(intentImage,1);




            }else if (flag.equals("3")) {
                mImage.setVisibility(View.GONE);
                mVideo.setVisibility(View.VISIBLE);

                mEdit.setVisibility(View.VISIBLE);
                videoFile = new File(Environment.getExternalStorageDirectory()+"/"+ System.currentTimeMillis()+".mp4");
                Intent intentVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intentVideo.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(videoFile));

                startActivityForResult(intentVideo,3);


            }



        }




    }







    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnSave:

                if ( !TextUtils.isEmpty( mEdit.getText().toString())){
                    addDB(mEdit.getText().toString());
                }


                finish();
                break;
            case R.id.btnCancel:
                finish();

                break;


        }

    }



//    添加一条记录
    public void addDB(String content){
        ContentValues cv = new ContentValues();
        cv.put(NotesDB.CONTENT, content);
        cv.put(NotesDB.TIME,getTime());
        if ( null != imageFile){
            cv.put(NotesDB.PATH, imageFile.getAbsolutePath());

        }else {
            cv.put(NotesDB.PATH, " ");

        }

        if ( null != videoFile){
            cv.put(NotesDB.VIDEO,videoFile.getAbsolutePath());

        }else {
            cv.put(NotesDB.VIDEO, " ");

        }


        mSQLiteDatabase.insert(NotesDB.TABLE_NAME,null,cv);



    }

    //    获取日期时间
    private String  getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月DD日 HH:mm:ss");
        Date curDate = new Date();
        String str = format.format(curDate);

        return  str;




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取新拍的照片的路径
        if (requestCode == 1){
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            mImage.setImageBitmap(bitmap);
        }

        if (requestCode == 3){
            Bitmap bitmap = BitmapFactory.decodeFile(videoFile.getAbsolutePath());
            mVideo.setVideoURI(Uri.fromFile(videoFile));

//            自动播放视频
            mVideo.start();
        }

    }
}
