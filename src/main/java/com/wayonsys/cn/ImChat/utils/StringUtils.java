package com.wayonsys.cn.ImChat.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by allen on 2016/4/20.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils{

    private static final Logger log = LoggerFactory.getLogger(StringUtils.class);

    public static String createRandom(boolean numberFlag, int length) {
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }

    public static  List<String> asStringList(String str) {

        if(isEmpty(str)) {
            return new ArrayList<String>();
        }
        String[] s = str.split(",");
        return Arrays.asList(s);
    }

    public static List<Long> asList(String str,String regex) {

        if(isEmpty(str)) {
            return new ArrayList<Long>();
        }

        String[] s = str.split(regex);
        Long[] b1 = new Long[s.length];
        for(int i=0;i < b1.length;i++ ) {
            b1[i] =Long.parseLong(s[i]);
        }

        return Arrays.asList(b1);
    }

    public static List<String> asStringList(List<Long> list) {

        List<String> result = new ArrayList<>();
        for(Long o : list) {
            result.add(o.toString());
        }
        return result;
    }

    public static String asString(Long id, int length) {

        String result = "";
        String s = id.toString();
        if(s.length() < length) {
            String format = "%0" + length + "d";
            result = String.format(format, id);
        }else {
            result = s;
        }
        return result;
    }

    public static String getString(Object o) {
        if(o == null) {
            return "";
        }
        return o.toString();
    }

    public static List<String> removeDuplicate(List<String> list) {

        Set<String> set = new HashSet<>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    public static String getString(String value, int max) {

        if (StringUtils.isBlank(value)) {
            return "";
        }
        int size = value.length();
        if (size > max) {
            return value.substring(0, max);
        }else {
            return value;
        }
    }

    public static void main(String[] args) {

        StringUtils.
        log.info(String.format("%05d", 123));
        log.info(String.format("%05d", 123456));
    }

}
