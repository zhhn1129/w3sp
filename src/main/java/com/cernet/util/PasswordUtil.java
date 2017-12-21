package com.cernet.util;
import java.util.Random;
import java.security.MessageDigest;

/**
 * Created by IntelliJ IDEA.
 * User: zhanghx
 * Date: 2007-8-29
 * Time: 11:32:04
 * To change this template use File | Settings | File Templates.
 */
public class PasswordUtil {
     private static Random randomInstance; //唯一的随机数产生器

     /**
     * 加密函数
     * @param passwd 原始密码，密码不能为null
     * @param key 密钥，不能为null
     * @return 加密后的字符串，如果passwd==null || key==null 则返回空字符串""
     * //@see com.cernet.service.util.Encrypt#encrypt(String,String)
     */
    public static String encrypt(String passwd, String key) {
        if (passwd == null || key == null)
            return "";
        String t = key + passwd + key;
        return doEncrypt(t);
    }
    
    public static void main(String[] args) {
//    	System.out.println(encrypt("1234","1234"));
    	System.out.println(encrypt("w3spadmin", "tXPamnojBeOwyAWU"));
    }

    /**
     * 核心加密函数，仅在内部调用
     * @param base 初始值，应保证不为null,此方法中不检验
     * @return 加密后的字符串
     */
    private static  String doEncrypt(String base) {
        char[] digit =
            {
                'U',
                'w',
                'S',
                'h',
                'Q',
                'p',
                'N',
                'm',
                'T',
                'z',
                'R',
                'x',
                'B',
                'a',
                'C',
                'k'};
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.reset();
            byte[] b = md.digest(base.getBytes()); //加密完成
            //将byte[]转为字符串，字符串由digit数组中的字符组成
            StringBuffer sb = new StringBuffer(b.length * 2);
            for (int i = 0; i < b.length; i++) {
                sb.append(digit[(b[i] >>> 4) & 0x0f]);
                sb.append(digit[b[i] & 0X0F]);
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException ep) {
            System.out.println(ep.toString()); //此例外应不可能抛出
            return null;
        }
    }

    /**
     * 随机生成字符串的函数
     * @param len 生成字符串(String) 的长度
     * @return 长度为len 的随机字符串,字符的范围为a-z | A-Z
     * //@see com.cernet.service.util.Encrypt#getKey(int)
     */
    public static String getKey(int len) {
        if (len <= 0)
            return "";
        StringBuffer sb = new StringBuffer(len);
        java.util.Random r = getRandomInstance();
        for (int i = 0; i < len; i++) {
            int z = Math.abs(r.nextInt());
            z %= 52;
            z += 65;
            if (z > 90)
                z += 6;
            sb.append((char)z);
        }
        return sb.toString();
    }

    /** 得到同一个随机数生成器 */
    private static Random getRandomInstance() {
        if (randomInstance == null) {
            randomInstance = new java.util.Random();
        }
        return randomInstance;
    }
}
