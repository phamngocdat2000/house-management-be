package com.student.searchroom.util;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.text.Normalizer;

@Log4j2
public class Utils {

    public static void copyPropertiesNotNull(Object source, Object target) {
        try {
            BeanUtilsBean notNull=new NullAwareBeanUtilsBean();
            notNull.copyProperties(target, source);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static String toEn(String input) {
        if (input == null) return null;
        return Normalizer
                .normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replace("Đ", "D")
                .replace("đ", "d");
    }

    public static String getAlphaNumericString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
