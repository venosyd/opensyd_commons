package com.venosyd.open.commons.util;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.venosyd.open.commons.log.Debuggable;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public class LoadImage implements Debuggable {

    /**
     * carrega uma imagem
     */
    public static String loadImageBase64(String imagesFolder, String identifier) {
        try {
            // cria o objeto para ler a image
            var imageFile = new File(imagesFolder + File.separator + identifier);

            if (!imageFile.exists())
                return "";

            return org.apache.commons.codec.binary.Base64.encodeBase64String(FileUtils.readFileToByteArray(imageFile));

        } catch (Exception e) {
            e.printStackTrace();
            err.exception("LOAD IMAGE PHOTO", e);
        }

        return null;
    }
}
