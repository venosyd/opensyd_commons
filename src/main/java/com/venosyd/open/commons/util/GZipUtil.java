package com.venosyd.open.commons.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         achado em https://myadventuresincoding.wordpress.com/2016/01/02/
 *         java-simple-gzip-utility-to-compress-and-decompress-a-string/
 */
public class GZipUtil {

    /** */
    public static byte[] zip(final String str) {
        if ((str == null) || (str.length() == 0)) {
            throw new IllegalArgumentException("Cannot zip null or empty string");
        }

        try (var byteArrayOutputStream = new ByteArrayOutputStream()) {
            try (var gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
                gzipOutputStream.write(str.getBytes(StandardCharsets.UTF_8));
            }

            return byteArrayOutputStream.toByteArray();
        }
        //
        catch (Exception e) {
            throw new RuntimeException("Failed to zip content", e);
        }
    }

    /** */
    public static String unzip(final byte[] compressed) {
        if ((compressed == null) || (compressed.length == 0)) {
            throw new IllegalArgumentException("Cannot unzip null or empty bytes");
        }

        if (!isZipped(compressed)) {
            return new String(compressed, StandardCharsets.UTF_8);
        }

        try (var byteArrayInputStream = new ByteArrayInputStream(compressed)) {
            try (var gzipInputStream = new GZIPInputStream(byteArrayInputStream)) {
                try (var inputStreamReader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8)) {
                    try (var bufferedReader = new BufferedReader(inputStreamReader)) {
                        var output = new StringBuilder();

                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            output.append(line);
                        }

                        return output.toString();
                    }
                }
            }
        }
        //
        catch (Exception e) {
            throw new RuntimeException("Failed to unzip content", e);
        }
    }

    /** */
    public static boolean isZipped(final byte[] compressed) {
        return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC))
                && (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
    }
}