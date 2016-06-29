package activity.sostv.com.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.vov.vitamio.utils.Log;

/**
 *
 *
 * @author bxbxbai
 */
public class Utils {

    private static final int MINUTE = 60;
    private static final int HOUR = MINUTE * 60;
    private static final int DAY = HOUR * 24;
    private static final int MONTH = DAY * 30;
    private static final int YEAR = MONTH * 12;

    private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    public static byte[] bmpToByteArray(Bitmap bitmap, boolean paramBoolean){
        Bitmap localBitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);
        int i; int j;
        if (bitmap.getHeight() > bitmap.getWidth()) {
            i = bitmap.getWidth();
            j = bitmap.getWidth();
        }else{
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
        while (true) {
            localCanvas.drawBitmap(bitmap, new Rect(0, 0, i, j), new Rect(0, 0, 80, 80), null);
            if (paramBoolean)
                bitmap.recycle();

            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100, localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try{
                localByteArrayOutputStream.close();
                return arrayOfByte;
            }catch (Exception e){
                Log.e("Utils",e.getMessage());
            }
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
    }

    public static byte[] bmpToByteArray(Bitmap bitmap){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        bitmap.recycle();
        byte[] result = output.toByteArray();
        try {
            output.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String convertPublishTime(String time) {
        try {
            long s = TimeUnit.MILLISECONDS.toSeconds(
                    new Date().getTime() - FORMAT.parse(time).getTime());

            long count = s / YEAR;
            if (count != 0) {
                return count + "年前";
            }

            count = s / MONTH;
            if (count != 0) {
                return count + "月前";
            }

            count = s / DAY;
            if (count != 0) {
                return count + "天前";
            }

            return "今天";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "未知时间";
    }

    public static boolean withinDays(String time, int days) {
        try {
            long s = TimeUnit.MILLISECONDS.toSeconds(
                    new Date().getTime() - FORMAT.parse(time).getTime());
            long count = s / DAY;
            if (0 < count && count <= days) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int compareTime(String a, String b) {
        try {
            long timeA = TimeUnit.MILLISECONDS.toSeconds(
                    new Date().getTime() - FORMAT.parse(a).getTime());
            long timeB = TimeUnit.MILLISECONDS.toSeconds(
                    new Date().getTime() - FORMAT.parse(b).getTime());

            return timeA >= timeB ? 1 : -1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String removeHtmlCode(String source) {
        if (TextUtils.isEmpty(source)) {
            return source;
        }

        StringBuilder dest = new StringBuilder();
        for (int i = 0, flag = 0; i < source.length(); i++) {
            char c  = source.charAt(i);

            if (c == '<') {
                flag = 1;
            }

            if (c == '>') {
                flag = 0;
                continue;
            }

            if (flag == 1) {
                continue;
            }
            dest.append(c);
        }

        return dest.toString();
    }
}
