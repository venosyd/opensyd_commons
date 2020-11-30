package com.venosyd.open.commons.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import com.google.common.hash.Hashing;
import com.venosyd.open.commons.log.Debuggable;
import com.venosyd.open.commons.util.HexUtil;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         Utilidades para hasheamento
 */
public class HashUtil implements Debuggable {

    /**
     * 
     */
    public static final String DEFAULT_HASH_ALGORITHM = "SHA-256";

    /**
     * From the source, to the hashes!
     */
    public static String generate(byte[] source) {
        try {
            var m = MessageDigest.getInstance(DEFAULT_HASH_ALGORITHM);
            m.update(source, 0, source.length);

            return HexUtil.bytesToHex(m.digest());
        } catch (Exception e) {
            err.tag("HASH UTIL DEFAULT").ln("Problems with hashing: " + e);
            return "";
        }
    }

    /**
     * From the source, to the hashes!
     */
    public static String sha256(String source) {
        try {
            return Hashing.sha256().hashString(source, StandardCharsets.UTF_8).toString();
        } catch (Exception e) {
            err.tag("HASH UTIL SHA-256").ln("Problems with hashing: " + e);
            return "";
        }
    }

    /**
     * From the source, to the hashes!
     */
    public static String sha512(String source) {
        try {
            return Hashing.sha512().hashString(source, StandardCharsets.UTF_8).toString();
        } catch (Exception e) {
            err.tag("HASH UTIL SHA-512").ln("Problems with hashing: " + e);
            return "";
        }
    }

}
