package com.kingja.cardpackage.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/25 16:25
 * 修改备注：
 */
public class CheckUtil {
    /**
     * 非空验证
     *
     * @param s
     * @param tip
     * @return
     */
    public static boolean checkEmpty(String s, String tip) {
        if (TextUtils.isEmpty(s)) {
            ToastUtil.showToast(tip);
            return false;
        }
        return true;
    }

    public static boolean checkHeight(String height, int min, int max) {
        if (TextUtils.isEmpty(height)) {
            ToastUtil.showToast("请输入身高");
            return false;
        }
        int heightInt = Integer.valueOf(height);

        if (heightInt < 80 || heightInt > 210) {
            ToastUtil.showToast("输入身高需在" + min + "到" + max + "cm之间");
            return false;
        }

        return true;
    }

    /**
     * 未-1判断(经纬度)
     *
     * @param d
     * @param tip
     * @return
     */

    public static boolean checkZero(double d, String tip) {
        if (d == -1) {
            ToastUtil.showToast(tip);
            return false;
        }
        return true;
    }

    public static boolean checkGender(String gender) {
        ToastUtil.showToast("请输入性别");
        if (TextUtils.isEmpty(gender)) {
            return false;
        }
        if (!("男".equals(gender) || "女".equals(gender))) {
            ToastUtil.showToast("性别格式错误");
            return false;
        }

        return true;

    }

    /**
     * 非-1
     *
     * @param i
     * @param tip
     * @return
     */
    public static boolean checkIntDefalut(int i, String tip) {
        if (i == -1) {
            ToastUtil.showToast(tip);
            return false;
        }
        return true;
    }

    /**
     * 验证是否以0开始
     *
     * @param s
     * @return
     */
    public static boolean checkZero(String s) {
        if ("0".equals(s) || s.startsWith("0") || s == null) {
            ToastUtil.showToast("请输入非零数字");
            return false;
        }
        return true;
    }


    /**
     * 手机号码验证
     *
     * @param phone
     * @return
     */
    public static boolean checkPhoneFormat(String phone) {
        // 判断非空
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showToast("手机号码不能为空");
            return false;
        }

        // 判断手机号格式
        if (!Pattern.matches(
                "^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\\d{8}$", phone)) {
            ToastUtil.showToast("手机号码格式不对");
            return false;
        }
        return true;
    }

    /**
     * 密码格式验证
     *
     * @param password
     * @return
     */
    public static boolean checkPasswordFormat(String password) {
        // 判断非空
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showToast("密码不能为空");
            return false;
        }
        if (!Pattern.matches("[a-zA-Z0-9]{1,12}", password)) {
            ToastUtil.showToast("密码须为1-12位字母或数字组合");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 密码格式验证
     *
     * @param password
     * @return
     */
    public static boolean checkPasswordFormat(String password, int min, int max, String target) {
//        "[a-zA-Z0-9]{1,12}"
        // 判断非空
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showToast(target + "密码不能为空");
            return false;
        }
        String reg = ".{" + min + "," + max + "}";
        if (!Pattern.matches(reg, password)) {
            ToastUtil.showToast(target + "密码须为" + min + "-" + max + "位字母或数字组合");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断字符串是否相同
     *
     * @param a
     * @param b
     * @param tip
     * @return
     */
    public static boolean checkEquil(String a, String b, String tip) {
        if (!a.equals(b)) {
            ToastUtil.showToast(tip);
            return false;
        }
        return true;
    }

    /**
     * 检查字符串最大长度
     *
     * @param input
     * @param lenght
     * @param tip
     * @return
     */
    public static boolean checkLengthMax(String input, int lenght, String tip) {

        if (input.length() > lenght) {
            ToastUtil.showToast(tip);
            return false;
        }
        return true;
    }

    public static boolean checkLengthMin(String input, int lenght, String tip) {

        if (input.length() < lenght) {
            ToastUtil.showToast(tip);
            return false;
        }
        return true;
    }

    public static boolean checkBirthday(String input, String tip) {

        if (input.length() != 11) {
            ToastUtil.showToast(tip);
            return false;
        }
        return true;
    }

    /**
     * 校验18位身份证号码
     *
     * @param value
     * @return
     */
    public static boolean checkIdCard(final String value, String tip) {
        if (value == null || value.length() != 18) {
            ToastUtil.showToast(tip);
            return false;
        }

        if (!value.matches("[\\d]+[X]?")) {
            ToastUtil.showToast(tip);
            return false;
        }
        String code = "10X98765432";
        int weight[] = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
        int nSum = 0;
        for (int i = 0; i < 17; ++i) {
            nSum += (int) (value.charAt(i) - '0') * weight[i];
        }
        int nCheckNum = nSum % 11;
        char chrValue = value.charAt(17);
        char chrCode = code.charAt(nCheckNum);
        if (chrValue == chrCode) {
            return true;
        }

        if (nCheckNum == 2 && (chrValue + ('a' - 'A') == chrCode)) {
            return true;
        }
        ToastUtil.showToast(tip);
        return false;
    }
}
