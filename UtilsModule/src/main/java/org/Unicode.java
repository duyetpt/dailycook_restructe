package org;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class Unicode {

//    private static final String VIETNAMESE_DIACRITIC_CHARACTERS
//            = "ẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴ";
    public static String toAscii(String value) {
        value = value.replace('đ', 'd');
        String str = Normalizer.normalize(value.toLowerCase().trim(), Normalizer.Form.NFD);
        String regex = "\\p{InCombiningDiacriticalMarks}+";

        Pattern pattern = Pattern.compile(regex);
        String result = pattern.matcher(str).replaceAll("");

        return result.trim();
    }

    public static void main(String[] args) throws java.lang.Exception {
//         for (char c: VIETNAMESE_DIACRITIC_CHARACTERS.toCharArray()) {
//         System.out.println(c + ": " + Character.getName(c));
//         }
        System.out.println(toAscii("xin chào Việt Nam. Món đậu phụ rất ngon"));
    }
}
