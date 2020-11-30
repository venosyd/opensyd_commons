package com.venosyd.open.commons.util;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         Hexadecimal convert functions
 */
public class HexUtil {

    public static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    /**
     * based in code founded at
     * http://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
     * 
     * thanks to maybeWeCouldStealAVan
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
