package org;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class Unicode {

    public static String toAscii(String value) {
        value = value.toLowerCase().replace('đ', 'd');
        String str = Normalizer.normalize(value.trim(), Normalizer.Form.NFD);
        String regex = "\\p{InCombiningDiacriticalMarks}+";

        Pattern pattern = Pattern.compile(regex);
        String result = pattern.matcher(str).replaceAll("");

        return result.trim();
    }
}
