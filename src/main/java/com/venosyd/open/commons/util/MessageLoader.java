package com.venosyd.open.commons.util;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.yamlbeans.YamlReader;

import com.venosyd.open.commons.log.Debuggable;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         Load properly messages agreeing with config file language
 */
public enum MessageLoader implements Debuggable {

    INSTANCE;

    /**
     * mapa de mensagems por linguagem (linguagem: { codigo: mensagem }
     */
    private Map<String, Map<String, String>> _messages;

    /**
     * mensagem padrao definida no arquivo de configuracoes
     */
    private String defaultCode;

    MessageLoader() {
        init(); // inicia o mapa de mensagens
    }

    /**
     * inicia o mapa de mensagens, pega o codigo padrao definido nas configuracoes e
     * carrega as mensagens no arquivo apropriado
     */
    private void init() {
        _messages = new HashMap<>();
        defaultCode = Config.INSTANCE.get("language");
        loadLanguage(defaultCode);
    }

    /**
     * carrega do arquivo seguindo o padrao definido de acordo com o codigo da
     * linguagem
     */
    @SuppressWarnings("unchecked")
    private boolean loadLanguage(String languageCode) {
        try {
            var reader = new YamlReader(new FileReader("assets/i18n/messages_" + languageCode + ".yaml"));
            _messages.put(languageCode, (Map<String, String>) reader.read());
            return true;
        } catch (Exception e) {
            err.exception("MESSAGE LOADER EXCEPTION", e);
            return false;
        }
    }

    /**
     * retorna uma mensagem, na linguagem padrao definida no arquivo de
     * configuracoes
     */
    public String get(String messageCode) {
        return _getMessage(defaultCode, messageCode);
    }

    /**
     * retorna uma mensagem, se existir, em uma linguagem especifica
     */
    public String get(String languageCode, String messageCode) {
        return _getMessage(languageCode, messageCode);
    }

    /**
     * carrega uma mensagem de acordo com a linguagem no mapa em memoria
     */
    private String _getMessage(String languageCode, String messageCode) {
        // procura se a linguagem foi carregada
        if (_messages.containsKey(languageCode)) {
            // se estiver carregada, procura uma mensagem pelo codigo
            if (_messages.get(languageCode).containsKey(messageCode)) {
                // retorna a mensagem
                return _messages.get(languageCode).get(messageCode);
            } else {
                // se a mensagem nao existir retorna um erro
                return "[MESSAGE LOADER] This message in " + languageCode + " is not defined";
            }
        } else {
            // se nao foi, carrega
            if (loadLanguage(languageCode)) {
                return _getMessage(languageCode, messageCode);
            }

            // se nao existir retorna um erro
            else {
                return "[MESSAGE LOADER] This language is not defined";
            }
        }
    }

}
