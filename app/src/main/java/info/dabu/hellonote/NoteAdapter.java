package info.dabu.hellonote;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by AlexY on 2016/4/21.
 */
public class NoteAdapter extends BaseAdapter {

    private static final String TAG = "NoteAdapter";
    private Cursor mCursor;

    private Context mContext;


    public NoteAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }


    @Override
    public int getCount() {

        Log.d(TAG, "getCount: "+mCursor.getCount());
        return mCursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return mCursor.getPosition();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        ViewHolder holder;
        if ( convertView == null){

            convertView = layoutInflater.inflate(R.layout.cell,parent,false);
            holder = new ViewHolder();

            holder.contentTV = (TextView) convertView.findViewById(R.id.list_content);
            holder.image = (ImageView) convertView.findViewById(R.id.list_image);
            holder.video = (ImageView) convertView.findViewById(R.id.list_video);

            holder.timeTv = (TextView) convertView.findViewById(R.id.list_time);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        //将游标移动到指定位置
        mCursor.moveToPosition(position);

        //根据字段名获取字段的索引值，然后根据索引值，获得对应的内容
        String content = mCursor.getString(mCursor.getColumnIndex(NotesDB.CONTENT));
        String time = mCursor.getString(mCursor.getColumnIndex(NotesDB.TIME));
        String url = mCursor.getString(mCursor.getColumnIndex(NotesDB.PATH));
        String videoUrl = mCursor.getString(mCursor.getColumnIndex(NotesDB.VIDEO));


        holder.contentTV.setText(content);
        holder.timeTv.setText(time);
        holder.image.setImageBitmap(getImageThumbnail(url,200,200));
        holder.video.setImageBitmap(getVideoThumbnail(videoUrl,200,200,
                MediaStore.Images.Thumbnails.MICRO_KIND));






        return convertView;
    }


//    为了避免图片太大，导致oom，所以我们获取照片的缩略图
    public Bitmap getImageThumbnail(String uri, int width,int height){
        Bitmap bitmap = null;
        BitmapFactory.Options options= new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(uri,options);
        options.inJustDecodeBounds = false;
        int beWidth = options.outWidth/width;
        int beHeight = options.outHeight/height;
        int be = 1;

        if (beWidth< beHeight){
            be  = beWidth;

        }else {
            be = beHeight;
        }

        if (be <= 0){
            be =1 ;
        }

        options.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(uri,options);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap,width,height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

        return bitmap;


    }


//    获取视频的缩略图
    private Bitmap getVideoThumbnail(String uri, int width, int height,int kind){
        Bitmap bitmap = null;
        bitmap = ThumbnailUtils.createVideoThumbnail(uri,kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap,width,height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

        return  bitmap;
    }




    public static class  ViewHolder{
        TextView contentTV;
        TextView timeTv;

        ImageView image;
        ImageView video;

    }




}
