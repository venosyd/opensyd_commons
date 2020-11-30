package com.venosyd.open.commons.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import com.venosyd.open.commons.log.Debuggable;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         le um arquivo que esta na assets/config
 */
public class ReadAssetsFile implements Debuggable {

    /** */
    private String file;

    /** */
    private String content;

    public ReadAssetsFile(String file) {
        this.file = file;
        init();
    }

    /**
     * inicia o mapa em memoria contendo as configuracoes lendo o arquivo de
     * configuracoes definido por padrao em YAML
     */
    private void init() {
        try {
            var path = Paths.get("assets/" + file);

            var lines = Files.lines(path);
            content = lines.collect(Collectors.joining("\n"));

            lines.close();

        } catch (Exception e) {
            err.tag(file.toUpperCase() + " READ EXCEPTION").ln(e);
        }
    }

    public String getContent() {
        return content;
    }

}
