package com.venosyd.open.commons.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         Utilidades para imagens, carregamento e afins
 */
public class ImageUtil {

    private static List<String> supportedImageTypes = Arrays.asList("png", "jpg", "gif", "bmp");

    /**
     * converte imagens (buffered, rendered) para um array de bytes. muito util para
     * salvar na base de dados, ou operar pela rede
     */
    public static byte[] convertImageToByteArray(String path) throws Exception {
        // se nao tiver o tipo especificado
        if (!path.contains(".")) {
            throw new Exception("[IMAGE UTIL] tipo nao especificado de imagem");
        }

        // se a extensao nao for suportada
        var extension = path.substring(path.lastIndexOf(".") + 1);
        if (!supportedImageTypes.contains(extension)) {
            throw new Exception("[IMAGE UTIL] tipo nao suportado de imagem");
        }

        var originalImage = ImageIO.read(new File(path));
        var baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, extension, baos);
        baos.flush();

        byte[] imageInByte = baos.toByteArray();
        baos.close();

        return imageInByte;
    }

}
