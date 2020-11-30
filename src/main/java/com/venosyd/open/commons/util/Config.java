package com.venosyd.open.commons.util;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         assets/config/config.yaml reader
 */
public class Config extends ConfigReader {

    /** */
    public static final Config INSTANCE = new Config();

    /** */
    private Config() {
        super("config");
    }

}
