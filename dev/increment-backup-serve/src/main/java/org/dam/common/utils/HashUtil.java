package org.dam.common.utils;

/**
 * @Author dam
 * @create 2024/2/21 14:48
 */
public class HashUtil {

    public static void main(String[] args) {
        System.out.println(hash("abc"));
        System.out.println(hash("def"));
    }

    public static int hash(String str){
        char[] charArray = str.toCharArray();
        int res = 1;
        for (char c : charArray) {
            res = 31 * res + c;
        }
        return res;
    }

}
