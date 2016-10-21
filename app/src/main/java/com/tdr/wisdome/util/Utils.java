package com.tdr.wisdome.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 * Created by Linus_Xie on 2016/7/27.
 */
public class Utils {

    /**
     * 自定义Toast
     *
     * @param context
     * @param msg
     */
    public static final void myToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 手机正则表达式
     *
     * @param phone
     * @return boolean
     */
    public static boolean isPhone(String phone) {
        String regExp = "0?(1)[0-9]{10}";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.find();
    }

    /**
     * 校验18位身份证号码
     */
    public static boolean isIDCard18(final String value) {
        if (value == null || value.length() != 18)
            return false;
        if (!value.matches("[\\d]+[X]?"))
            return false;
        String code = "10X98765432";
        int weight[] = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
                8, 4, 2, 1};
        int nSum = 0;
        for (int i = 0; i < 17; ++i) {
            nSum += (int) (value.charAt(i) - '0') * weight[i];
        }
        int nCheckNum = nSum % 11;
        char chrValue = value.charAt(17);
        char chrCode = code.charAt(nCheckNum);
        if (chrValue == chrCode)
            return true;
        if (nCheckNum == 2 && (chrValue + ('a' - 'A') == chrCode))
            return true;
        return false;
    }

    /**
     * 获取版本号(内部识别号)
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取versionName
     *
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    /**
     * dip转为 px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px 转为 dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕的宽度
     */
    public final static int getWindowsWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static final boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * byte转String
     *
     * @param bt
     * @return
     */
    public static String Byte2Str(byte[] bt) {
        return Base64.encode(bt);
    }

    /**
     * Bitmap转换为byte[]
     *
     * @param bm
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * base64字符串转图片
     *
     * @param string
     * @return
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public static Bitmap stringtoBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = android.util.Base64.decode(string, android.util.Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * 初始化数据，避免空指针
     *
     * @param str
     * @return
     */
    public static String initNullStr(String str) {
        if ("null".equals(str) || null == str || "".equals(str)) {
            return "";
        }
        return str;
    }

    /**
     * 压缩图片用ThumbnailUtils
     */
    public static Bitmap thumbnailBitmap(Bitmap bitmap, int reqsW, int reqsH) {
        int old_width = bitmap.getWidth();
        int old_height = bitmap.getHeight();
        // 计算，目标是300*400
        int ratio_width = reqsW;
        int ratio_height = reqsH;
        // 新宽、高
        int new_width = 0;
        int new_height = 0;
        // 缩放参考策略
        if (old_width * ratio_height > old_height * ratio_width) {
            new_width = ratio_width;
            new_height = old_height * ratio_width / old_width;
        } else {
            new_height = ratio_height;
            new_width = old_width * ratio_height / old_height;
        }
        Bitmap new_bitmap = ThumbnailUtils.extractThumbnail(
                bitmap, new_width, new_height);
        return new_bitmap;
    }

    public static Bitmap thumbnailBitmap(Bitmap bitmap) {
        int old_width = bitmap.getWidth();
        int old_height = bitmap.getHeight();
        // 计算，目标是300*400
        int ratio_width = 680;
        int ratio_height = 800;
        // 新宽、高
        int new_width = 0;
        int new_height = 0;
        // 缩放参考策略
        if (old_width * ratio_height > old_height * ratio_width) {
            new_width = ratio_width;
            new_height = old_height * ratio_width / old_width;
        } else {
            new_height = ratio_height;
            new_width = old_width * ratio_height / old_height;
        }
        Bitmap new_bitmap = ThumbnailUtils.extractThumbnail(
                bitmap, new_width, new_height);
        return new_bitmap;
    }

    /**
     * byte——>String
     *
     * @param src
     * @return
     */
    public static final String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 身份证号码，隐藏中间的出身年月日
     */
    public static final String hideID(String id) {
        String a = id.substring(2, 16);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            String b = a.replace(a, "*");
            sb.append(b);
        }
        String newId = id.substring(0, 2) + sb.toString()
                + id.substring(16, id.length());
        return newId;
    }

    public static int[] getLocation(View v) {
        int[] loc = new int[4];
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        loc[0] = location[0];
        loc[1] = location[1];
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(w, h);

        loc[2] = v.getMeasuredWidth();
        loc[3] = v.getMeasuredHeight();

        //base = computeWH();
        return loc;
    }

    /**
     * 保存图片
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static void saveFile(Bitmap bm, String fileName) throws IOException {
        String subForder = Constants.SAVE_PATH;
        File foder = new File(subForder);
        if (!foder.exists()) {
            foder.mkdirs();
        }
        File myCaptureFile = new File(subForder, fileName);
        if (!myCaptureFile.exists()) {
            myCaptureFile.createNewFile();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bos);
        bos.flush();
        bos.close();
    }

    /**
     * 数组截取：从arrData的offset开始获取len个长度的byte生成的len的byte[]
     *
     * @param arrData
     * @param offset
     * @param len
     * @return
     */
    public static final byte[] GetByteArrayByLength(byte[] arrData, int offset, int len) {
        byte[] newData = new byte[len];
        try {
            for (int i = offset; i < offset + len; i++) {
                newData[i - offset] = arrData[i];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newData;
    }

    /**
     * short转换成byte数组
     *
     * @param s
     * @return
     */
    public static final byte[] shortToBytes(short s) {
        byte[] shortBuf = new byte[2];
        for (int i = 0; i < 2; i++) {
            int offset = (shortBuf.length - 1 - i) * 8;
            shortBuf[i] = (byte) ((s >>> offset) & 0xff);
        }
        return shortBuf;
    }

    /**
     * 判断两个byte数组是否相等
     *
     * @param myByte
     * @param otherByte
     * @return
     */
    public static final boolean checkByte(byte[] myByte, byte[] otherByte) {
        boolean b = false;
        int len = myByte.length;
        if (len == otherByte.length) {
            for (int i = 0; i < len; i++) {
                if (myByte[i] != otherByte[i]) {
                    return b;
                }
            }
            b = true;
        }
        return b;
    }

    /**
     * 获取当前系统时间
     *
     * @return
     */
    public static final String getNowTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前系统日期
     *
     * @return
     */
    public static final String getNowDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = simpleDateFormat.format(date) + " " + "00:00:00";
        return nowDate;
    }

    /**
     * String转成byte数组
     */

    public static byte[] hexStringToBytes(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    /**
     * 若都不为空，将arrSrc2添加到arrSrc1的末尾组合成新的byte数组
     *
     * @param arrSrc1
     * @param arrSrc2
     * @return
     */
    public static final byte[] ByteArrayCopy(byte[] arrSrc1, byte[] arrSrc2) {
        byte[] arrDes = null;
        if (arrSrc2 == null) {
            arrDes = arrSrc1;
            return arrDes;
        }

        if (arrSrc1 != null) {
            arrDes = new byte[arrSrc1.length + arrSrc2.length];
            System.arraycopy(arrSrc1, 0, arrDes, 0, arrSrc1.length);
            System.arraycopy(arrSrc2, 0, arrDes, arrSrc1.length, arrSrc2.length);
        } else {
            arrDes = new byte[arrSrc2.length];
            System.arraycopy(arrSrc2, 0, arrDes, 0, arrSrc2.length);
        }

        return arrDes;
    }

    /**
     * 获取两数相除结果
     *
     * @param i 分子
     * @param j 分母
     * @return
     */
    public static String getDivide(int i, int j) {
        NumberFormat f = NumberFormat.getNumberInstance();
        f.setMaximumFractionDigits(1);
        f.setMinimumFractionDigits(1);
        double d = (i * 1.0 / j) * 100;
        return f.format(d);
    }

    /**
     * List to String 中间“，”
     *
     * @param stringList
     * @return
     */
    public static String listToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    /**
     * 奇偶数
     */
    public static String maleOrFemale(String identity) {
        String sex = "";
        int mf = Integer.valueOf(identity.substring(16, 17));
        if (mf % 2 == 0) {
            sex = "女";
        } else {
            sex = "男";
        }
        return sex;
    }

    /**
     * 身份证提取出生年月日
     * 330302 1986 02 01 0305
     */
    public static String identityToBirthday(String identity) {
        String birthday = "";
        String year = identity.substring(6, 10);
        String month = identity.substring(10, 12);
        String day = identity.substring(12, 14);
        birthday = year + "年" + month + "月" + day + "日";
        return birthday;
    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path
     *            图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 读取本地图片
     *
     * @param dst
     * @param width
     * @param height
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Bitmap getBitmapFromFile(File dst, int width, int height) {
        if (null != dst && dst.exists()) {
            BitmapFactory.Options opts = null;
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(dst.getPath(), opts);
                // 计算图片缩放比例
                final int minSideLength = Math.min(width, height);
                opts.inSampleSize = computeSampleSize(opts, minSideLength, width * height);
                opts.inJustDecodeBounds = false;
                opts.inInputShareable = true;
                opts.inPurgeable = true;
            }
            try {
                return BitmapFactory.decodeFile(dst.getPath(), opts);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (true) {
                if (roundedSize >= initialSize)
                    return roundedSize;
                roundedSize <<= 1;
            }
        }

        return 8 * ((initialSize + 7) / 8);
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128
                : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
}
