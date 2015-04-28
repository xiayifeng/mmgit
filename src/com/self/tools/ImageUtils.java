package com.self.tools;

import android.graphics.*;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sh-xiayf on 15-4-27.
 */
public class ImageUtils {

    private ImageUtils(){}

    private static ImageUtils instances;

    public static ImageUtils getInstances(){
        if(instances == null){
            instances = new ImageUtils();
        }
        return instances;
    }

    public void startView(String url,ImageView imageView,boolean isCir){
        LoadImageTask loadImageTask = new LoadImageTask(imageView,isCir);
        loadImageTask.execute(url);
    }


    class LoadImageTask extends AsyncTask<String,Integer,Bitmap>{

        private ImageView myView;
        private boolean isCircle;

        public LoadImageTask(ImageView v) {
            this.myView = v;
            this.isCircle = false;
        }

        public LoadImageTask(ImageView v,boolean isCir) {
            this.myView = v;
            this.isCircle = isCir;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            URL m;
            InputStream i = null;
            try {
                m = new URL(params[0]);
                i = (InputStream) m.getContent();
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//	         Drawable d = Drawable.createFromStream(i, "src");

            Bitmap d = BitmapFactory.decodeStream(i);

            if(isCircle && d != null){
                d = toRoundBitmap(d);
            }

            return d;
        }

        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);
        }


        public Bitmap toRoundBitmap(Bitmap bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float roundPx;
            float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
            if (width <= height) {
                roundPx = width / 2;
                left = 0;
                top = 0;
                right = width;
                bottom = width;
                height = width;
                dst_left = 0;
                dst_top = 0;
                dst_right = width;
                dst_bottom = width;
            } else {
                roundPx = height / 2;
                float clip = (width - height) / 2;
                left = clip;
                right = width - clip;
                top = 0;
                bottom = height;
                width = height;
                dst_left = 0;
                dst_top = 0;
                dst_right = height;
                dst_bottom = height;
            }

            Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
            final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
            final RectF rectF = new RectF(dst);

            paint.setAntiAlias(true);// 设置画笔无锯齿

            canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas

            paint.setColor(color);

            // 以下有两种方法画圆,drawRounRect和drawCircle
            // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
            canvas.drawCircle(roundPx, roundPx, roundPx - 10, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
            canvas.drawBitmap(bitmap, src, dst, paint); //以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

            Bitmap output2 = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(output2);
            final Paint pp = new Paint();
            pp.setAntiAlias(true);
            canvas2.drawARGB(0, 0, 0, 0); // 填充整个Canvas

            pp.setColor(Color.parseColor("#dddddd"));
            canvas2.drawCircle(roundPx, roundPx, roundPx, pp);


            int bgwith = output2.getWidth();
            int bgheight = output2.getHeight();

            Bitmap newbmp = Bitmap.createBitmap(bgwith, bgheight, Bitmap.Config.ARGB_8888);
            Canvas cv = new Canvas(newbmp);
            cv.drawBitmap(output2, 0, 0, null);
            cv.drawBitmap(output, 0, 0, null);
            cv.save(Canvas.ALL_SAVE_FLAG);
            cv.restore();

            return newbmp;
        }
    }

}
