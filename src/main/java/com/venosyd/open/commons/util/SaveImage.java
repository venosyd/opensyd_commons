package com.venosyd.open.commons.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.venosyd.open.commons.log.Debuggable;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public class SaveImage implements Debuggable {

    /**
     * salva imagem
     */
    public static String saveImage(String imagesFolder, String identifier, InputStream photoStream) {
        try {
            // cria um arquivo com o nome sendo igual a ID do customer
            var image = new File(imagesFolder + File.separator + identifier);

            // se ja tiver uma imagem deleta
            if (image.exists())
                image.delete();

            // cria a nova imagem
            if (image.createNewFile()) {

                // para cada chunk do stream vai montando a imagem ate terminar
                try (var bo = new BufferedOutputStream(new FileOutputStream(image))) {
                    int chunk;
                    while ((chunk = photoStream.read()) != -1) {
                        bo.write(chunk);
                    }

                    photoStream.close();
                } catch (Exception e) {
                    throw new Exception("Cant create image file");
                }

                // retorna OK se der tudo certo
                return "SUCCESS";

            } else {
                throw new Exception("Cant create image file");
            }
        } catch (Exception e) {
            err.exception("SAVE PHOTO", e);
        }

        return null;
    }

}
