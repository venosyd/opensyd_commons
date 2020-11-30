package com.venosyd.open.commons.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public class HTMLUtil {

    @SuppressWarnings("serial")
    private static final Map<String, String> ENCODING_CHARS = new HashMap<String, String>() {
        {
            put("Á", "&Aacute;");
            put("À", "&Agrave;");
            put("Ã", "&Atilde;");
            put("Â", "&Acirc;");
            put("á", "&aacute;");
            put("à", "&agrave;");
            put("ã", "&atilde;");
            put("â", "&acirc;");

            put("É", "&Eacute;");
            put("È", "&Egrave;");
            put("Ê", "&Ecirc;");
            put("é", "&eacute;");
            put("è", "&egrave;");
            put("ê", "&ecirc;");

            put("Í", "&Iacute;");
            put("Ì", "&Igrave;");
            put("í", "&iacute;");
            put("ì", "&igrave;");

            put("Ó", "&Oacute;");
            put("Ò", "&Ograve;");
            put("Õ", "&Otilde;");
            put("Ô", "&Ocirc;");
            put("ó", "&oacute;");
            put("ò", "&ograve;");
            put("õ", "&otilde;");
            put("ô", "&ocirc;");

            put("Ú", "&Uacute;");
            put("ú", "&uacute;");

            put("Ç", "&Ccedil;");
            put("ç", "&ccedil;");

            put("©", "&copy;");
            put("®", "&reg;");
            put("♥", "&hearts;");
        }
    };

    /**
     * remove acentos e caracteres especiais e os substitui por encodingchars do
     * html
     */
    public static String processTextWithEntityEncoding(String text) {
        for (String character : ENCODING_CHARS.keySet()) {
            text = text.replace(character, ENCODING_CHARS.get(character));
        }

        return text;
    }
}
