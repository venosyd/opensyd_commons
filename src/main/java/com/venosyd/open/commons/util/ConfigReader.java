package com.venosyd.open.commons.util;

import java.io.FileReader;
import java.util.Map;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.google.common.collect.ImmutableMap;
import com.venosyd.open.commons.log.Debuggable;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         YAML Configuration object reader
 */
public abstract class ConfigReader implements Debuggable {

    /** */
    private String file;

    /**
     * mapa de configuracoes
     */
    private ImmutableMap<String, Object> _yaml;

    /** */
    public ConfigReader(String file) {
        this.file = file;
        init();
    }

    /**
     * inicia o mapa em memoria contendo as configuracoes lendo o arquivo de
     * configuracoes definido por padrao em YAML
     */
    @SuppressWarnings("unchecked")
    private void init() {
        try {
            var reader = new YamlReader(new FileReader("assets/config/" + file + ".yaml"));
            _yaml = ImmutableMap.copyOf((Map<String, Object>) reader.read());
        } catch (Exception e) {
            err.tag(file.toUpperCase() + " YAML EXCEPTION").ln(e);
        }
    }

    /**
     * retorna uma propriedade de acordo com o tipo especificado
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String property) {
        return (T) _yaml.get(property);
    }

    /**
     * imprime o mapa em memoria que representa o arquivo lido
     */
    public void outPrintln() {
        out.tag(file.toUpperCase() + " YAML FILE").map_(_yaml);
    }

}
