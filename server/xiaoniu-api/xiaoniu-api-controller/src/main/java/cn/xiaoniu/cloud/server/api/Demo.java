package cn.xiaoniu.cloud.server.api;

import cn.xiaoniu.cloud.server.util.constant.CommonConstant;

import java.nio.charset.Charset;

/**
 * @author 孔明
 * @date 2020/4/24 21:50
 * @description cn.xiaoniu.cloud.server.api.Demo
 */
public class Demo {

    public static void main(String[] args) {
//        String abc = "kongming";
//
//
//        byte[] abcd = abc.getBytes(CommonConstant.UTF_8_C);
//
//        byte key = abcd[3];
//
//        for (int i = 0; i <= abcd.length - 1; i++) {
//            abcd[i] = (byte) (((abcd[i] & 0xff) + key) % 256);
//        }
//
//        System.out.println(new String(abcd));
//
//        for (int i = 0; i <= abcd.length - 1; i++) {
//            abcd[i] = (byte) (((abcd[i] & 0xff) + 256 - key) % 256);
//        }
//
//        System.out.println(new String(abcd));

        String a = "adbghede94bf0eb6436f240a4fd71282";
        char[] b = a.toCharArray();
        char[] c = new char[4];
        long e = 0;
        for (int i = 0; i < 8; i++) {
            System.arraycopy(b, i * c.length, c, 0, c.length);
            long d = 0L;
            for (int i1 = 0; i1 < c.length; i1++) {
                long f = ((long) c[i1]) << (16 * (3 - i1));
                d = (d | f);
            }
            e += d;
        }
        System.out.println(e);
    }
}
