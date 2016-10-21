package com.kingja.cardpackage.util;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

/**
 * Description：二维码工具类
 * Create Time：2016/9/1 11:08
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class QRCodeUtil {
    private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xffffffff;

    /**
     * 生成二维码
     * @param key
     * @return
     * @throws WriterException
     */
    public static Bitmap createQRCode(String key) throws WriterException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix matrix = new MultiFormatWriter().encode(key,
                BarcodeFormat.QR_CODE, AppUtil.dp2px(200),  AppUtil.dp2px(200));
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = BLACK;
                } else {
                    pixels[y * width + x] = WHITE;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
