package com.sky.app.library.utils;

import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sky on 2017/2/13.
 * 数据校验
 * 1、判断手机号
 * 2、判断身份证
 * 3、判断邮箱
 * 4、判断中文
 * 5、随机数
 * 6、判断银行卡
 */

public class StringUtil {

    /**
     * 判断是否是手机号码
     * @param phonenumber
     * @return
     */
    public static boolean isPhone(String phonenumber) {
        if (TextUtils.isEmpty(phonenumber)) return false;
        Pattern p = Pattern.compile("^[1]\\d{10}$");
        Matcher m = p.matcher(phonenumber);
        return m.matches();
    }

    /**
     * 判断身份证
     * @param idnumber
     * @return
     */
    public static boolean isIDNumber(String idnumber) {
        if (idnumber == null) return false;
        Pattern p = Pattern.compile("(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|x|X))");
        Matcher m = p.matcher(idnumber);
        return m.matches();
    }

    /**
     * 检查是否是邮箱
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        Pattern pattern = Pattern
                .compile("^\\w+([-.]\\w+)*@\\w+([-]\\w+)*\\.(\\w+([-]\\w+)*\\.)*[a-z]{2,3}$");
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 验证是否是中文
     *
     * @param str
     * @return
     */
    public static boolean isgbk(String str) {
        if (!str.matches("[\u4e00-\u9fa5]{2,20}")) {
            System.out.println("只能输入2到4个汉字");
            return false;
        }
        return true;
    }

    /**
     * 判断6-20位字母或数字
     *
     * @param idnumber
     * @return
     */
    public static boolean ispwd(String idnumber) {
        if (idnumber == null) {
            return false;
        }
        Pattern p = Pattern.compile("^[\\w.]{6,12}$");
        Matcher m = p.matcher(idnumber);
        return m.matches();
    }

    /**
     * 随机数
     * @param count
     * @return
     */
    public static String randStr(int count){
        char[] db = {'0','1','2','3','4','5','6','7','8','9'};
        char[] rel = new char[count];
        for(int i = 0; i < count; i++){
            int random = (int)(Math.random()*db.length);
            rel[i] = db[random];
        }
        return String.valueOf(rel);
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeCardId
     * @return
     */
    private static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }
}
